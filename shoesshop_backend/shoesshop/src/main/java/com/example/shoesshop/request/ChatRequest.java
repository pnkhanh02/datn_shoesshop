package com.example.shoesshop.request;

import java.util.List;
import java.util.Map;

public class ChatRequest {
    // messages must be a list of {role: "user/assistant", content: "message"}
    private String messages;

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }
}