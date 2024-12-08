package com.example.shoesshop.controller;

import com.example.shoesshop.entity.Product;
import com.example.shoesshop.request.ChatRequest;
import com.example.shoesshop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.util.*;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/chatbot")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatbotController {

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Autowired
    private ProductService productService;

    @PostMapping("/chat")
    public ResponseEntity<?> chatWithBot(@RequestBody ChatRequest chatRequest) {
        try {

            // get data product
            List<Product> listProduct = productService.getFullProducts();

            String userMessage = chatRequest.getMessages().toLowerCase();
            String chatMessage = "Hãy trả lời câu hỏi với ";

            if (userMessage.contains("giày") || userMessage.contains("shoe")) {
                StringBuilder productInfo = new StringBuilder("Danh sách sản phẩm giày hiện có trong cửa hàng:\n");

                // Tạo danh sách các sản phẩm
                for (Product product : listProduct) {
                    productInfo.append("- Tên: ").append(product.getName())
                            .append(", Giá: ").append(product.getPrice()).append(" VNĐ\n");
                }

                if(userMessage.contains("mô tả")){
                    for (Product product : listProduct) {
                        productInfo.append("- Mô tả: ").append(product.getDescription());
                    }
                }

                chatMessage = productInfo.toString();
            }

//            ObjectMapper objectMapper = new ObjectMapper();
//            Product shoeData = objectMapper.readValue(new File("E:\\product.json"), Product.class);


            chatMessage += userMessage;

            RestTemplate restTemplate = new RestTemplate();

            // Build the OpenAI API URL
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(geminiApiUrl)
                    .queryParam("key", geminiApiKey);

//            RequestBodyGeminiDTO body = new RequestBodyGeminiDTO();
//            RequestBodyGeminiDTO.Part part = new RequestBodyGeminiDTO.Part();
//            part.setText(chatRequest.getMessages());
//
//            RequestBodyGeminiDTO.Content content = new RequestBodyGeminiDTO.Content();
//            content.setParts(List.of(part));
//
//            body.setContents(List.of(content));

            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
//            headers.set("Authorization", "Bearer " + openAiApiKey);
            System.out.println(chatMessage);
            // Prepare request body
            Map<String, Object> requestBody = new HashMap<>();
            List<Map<String, Object>> contents = new ArrayList<>();
            Map<String, Object> parts = new HashMap<>();
            parts.put("text", chatMessage);
            Map<String, Object> singleContent = new HashMap<>();
            singleContent.put("parts", Collections.singletonList(parts));
            contents.add(singleContent);
            requestBody.put("contents", contents);
//            Map<String, Object> requestBody = new HashMap<>();
//            requestBody.put("contents", chatRequest.getMessages());

//            Map<String, Object> requestBody = new HashMap<>();
//            requestBody.put("model", "gpt-3.5-turbo"); // Sử dụng GPT-3.5
//            requestBody.put("messages", chatRequest.getMessages());
//            requestBody.put("temperature", 0.5); // Tùy chỉnh độ sáng tạo
//            requestBody.put("max_tokens", 50);

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

            // Return response from OpenAI
            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            // Handle error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to communicate with Gemini API");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}