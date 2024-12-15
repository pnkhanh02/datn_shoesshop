package com.example.shoesshop.controller;

import com.example.shoesshop.dto.ChatHistoryDTO;
import com.example.shoesshop.dto.LoginDTO;
import com.example.shoesshop.entity.ChatHistory;
import com.example.shoesshop.entity.Product;
import com.example.shoesshop.exception.CustomException;
import com.example.shoesshop.exception.ErrorResponseEnum;
import com.example.shoesshop.request.ChatHistoryRequest;
import com.example.shoesshop.request.ChatRequest;
import com.example.shoesshop.service.ChatbotService;
import com.example.shoesshop.service.ProductService;
import com.example.shoesshop.utils.JWTTokenUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/chatbot")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatbotController {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${client.url}")
    private String clientUrl;

    @Autowired
    private ChatbotService chatbotService;

    @Autowired
    private ProductService productService;

    @Autowired
    JWTTokenUtils jwtTokenUtils;

    @PostMapping("/chat")
    public ResponseEntity<?> chatWithBot(@RequestBody ChatRequest chatRequest,
                                         @RequestHeader("Authorization") String authorizationHeader) {
        try {

            // Lấy token từ Authorization header
            String token = authorizationHeader.replace("Bearer ", "");

            // Giải mã token để lấy thông tin user
            LoginDTO loginDto = jwtTokenUtils.parseAccessToken(token);
            if (loginDto == null || loginDto.getUsername() == null) {
                throw new CustomException(ErrorResponseEnum.USERNAME_NOT_FOUND);
            }

            int customerId = loginDto.getId();
            if (customerId == -1) {
                throw new CustomException(ErrorResponseEnum.USERNAME_NOT_FOUND);
            }

            // get data product
            List<Product> listProduct = productService.getFullProducts();

            String userMessage = chatRequest.getMessages().toLowerCase();
            String chatMessage = "Hãy trả lời câu hỏi với ";

            if (userMessage.contains("giày") || userMessage.contains("shoe")) {
                StringBuilder productInfo = new StringBuilder("Danh sách sản phẩm giày hiện có trong cửa hàng:\n");

                // Tạo danh sách các sản phẩm
                for (Product product : listProduct) {
                    // link sản phẩm
                    int productId = product.getId();
                    String productLink = clientUrl + "/client/product/" + productId;
                    productInfo.append("- Tên: ").append(product.getName())
                            .append(", Giá: ").append(product.getPrice()).append(" VNĐ\n")
                            .append(", link sp: ").append(productLink).append("\n");
                }

//                if(userMessage.contains("mô tả")){
//                    for (Product product : listProduct) {
//                        productInfo.append("- Mô tả: ").append(product.getDescription());
//                    }
//                }

                chatMessage = productInfo.toString();
            }

//            ObjectMapper objectMapper = new ObjectMapper();
//            Product shoeData = objectMapper.readValue(new File("E:\\product.json"), Product.class);


            chatMessage += userMessage;
            chatMessage += "\nLink sản phẩm sẽ đính kèm vào tên sản phẩm";
//            chatMessage += "\nTrả về danh sách sản phẩm, chuyển hướng đến link sản phẩm khi người dùng click vào tên sản phẩm";

            RestTemplate restTemplate = new RestTemplate();

            // Build the OpenAI API URL
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(geminiApiUrl)
                    .queryParam("key", geminiApiKey);

            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            //System.out.println(chatMessage);

            // Prepare request body
            Map<String, Object> requestBody = new HashMap<>();
            List<Map<String, Object>> contents = new ArrayList<>();
            Map<String, Object> parts = new HashMap<>();
            parts.put("text", chatMessage);
            Map<String, Object> singleContent = new HashMap<>();
            singleContent.put("parts", Collections.singletonList(parts));
            contents.add(singleContent);
            requestBody.put("contents", contents);

            // Create the HTTP Entity
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//            HttpEntity<RequestBodyGeminiDTO> entity = new HttpEntity<>(body, headers);

            // Send POST request
            ResponseEntity<Map> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            System.out.println(response.getBody());

            // Extract chatbot response
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> responseBody = response.getBody();

            //lưu tin nhắn vào database
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
            if (!candidates.isEmpty()) {
                Map<String, Object> firstCandidate = candidates.get(0);
                Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
                List<Map<String, Object>> partsHistory = (List<Map<String, Object>>) content.get("parts");
                if (!partsHistory.isEmpty()) {
                    String chatbotResponse = (String) partsHistory.get(0).get("text");

                    // Lưu vào database
                    ChatHistoryRequest chatHistoryRequest = new ChatHistoryRequest();
                    chatHistoryRequest.setMessage(userMessage);
                    chatHistoryRequest.setResponse(chatbotResponse);
                    chatHistoryRequest.setCreateDate(LocalDate.now()); // Hoặc ngày từ request
                    chatHistoryRequest.setCustomerId(customerId);

                    chatbotService.createChatHistory(chatHistoryRequest);

                }
            }

            // Return response from Gemini
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            // Handle error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to communicate with Gemini API");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/getAllByCustomerId/{customerId}")
    public ResponseEntity<?> findAllByCustomerId(@PathVariable(name = "customerId") int customerId) {
        List<ChatHistory> chatHistories = chatbotService.findAllByCustomerId(customerId);
        List<ChatHistoryDTO> chatHistoryDTOList = chatHistories.stream()
                .map(chatHistory -> new ChatHistoryDTO(
                                chatHistory.getChatId(),
                                chatHistory.getMessage(),
                                chatHistory.getResponse(),
                                chatHistory.getCustomer().getId()
                        )
                ).collect(Collectors.toList());
        return new ResponseEntity<>(chatHistoryDTOList, HttpStatus.OK);
    }
}