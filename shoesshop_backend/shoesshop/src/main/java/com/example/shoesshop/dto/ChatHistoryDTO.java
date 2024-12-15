package com.example.shoesshop.dto;

public class ChatHistoryDTO {
    private int chatId;
    private String message;
    private String response;
    private int customerId;

    public ChatHistoryDTO() {
    }

    public ChatHistoryDTO(int chatId, String message, String response, int customerId) {
        this.chatId = chatId;
        this.message = message;
        this.response = response;
        this.customerId = customerId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
