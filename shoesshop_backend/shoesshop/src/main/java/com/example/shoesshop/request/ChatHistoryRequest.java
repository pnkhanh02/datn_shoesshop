package com.example.shoesshop.request;

import java.time.LocalDate;

public class ChatHistoryRequest {
    private String message;
    private String response;
    private LocalDate createDate;
    private int customerId;

    public ChatHistoryRequest() {
    }

    public ChatHistoryRequest(String message, String response, LocalDate createDate, int customerId) {
        this.message = message;
        this.response = response;
        this.createDate = createDate;
        this.customerId = customerId;
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
