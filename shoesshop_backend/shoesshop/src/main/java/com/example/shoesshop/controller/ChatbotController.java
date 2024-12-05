package com.example.shoesshop.controller;

import com.example.shoesshop.request.ChatRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/chatbot")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatbotController {

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    @Value("${openai.api.key}")
    private String openAiApiKey;

    @PostMapping("/chat")
    public ResponseEntity<?> chatWithBot(@RequestBody ChatRequest chatRequest) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Build the OpenAI API URL
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(openAiApiUrl);

            // Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + openAiApiKey);

            // Prepare request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-3.5-turbo"); // Sử dụng GPT-3.5
            requestBody.put("messages", chatRequest.getMessages());
            requestBody.put("temperature", 0.5); // Tùy chỉnh độ sáng tạo
            requestBody.put("max_tokens", 50);

            // Create the HTTP Entity
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

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
            errorResponse.put("error", "Failed to communicate with OpenAI API");
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
