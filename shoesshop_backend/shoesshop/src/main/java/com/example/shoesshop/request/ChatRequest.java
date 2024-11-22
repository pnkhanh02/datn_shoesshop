package com.example.shoesshop.request;

import java.util.List;
import java.util.Map;

public class ChatRequest {
    // messages must be a list of {role: "user/assistant", content: "message"}
    private List<Map<String, String>> messages;

    public List<Map<String, String>> getMessages() {
        return messages;
    }

    public void setMessages(List<Map<String, String>> messages) {
        this.messages = messages;
    }
}
