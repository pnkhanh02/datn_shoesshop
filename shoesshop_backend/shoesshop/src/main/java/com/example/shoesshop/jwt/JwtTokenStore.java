package com.example.shoesshop.jwt;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenStore {


    private static JwtTokenStore instance;
    // Private constructor to prevent instantiation from outside the class
    public JwtTokenStore() {
    }
    // Method to get the singleton instance
    public static JwtTokenStore getInstance() {
        if (instance == null) {
            synchronized (JwtTokenStore.class) {
                if (instance == null) {
                    instance = new JwtTokenStore();
                }
            }
        }
        return instance;
    }
    private static final Map<String, String> tokenStore = new HashMap<>();

    public void storeToken(String email, String token) {
        tokenStore.put(email, token);
    }

    public void removeToken(String email) {
        tokenStore.remove(email);
    }

    public boolean isTokenPresent(String email, String token) {
        String storedToken = tokenStore.get(email);
        return storedToken != null && storedToken.equals(token);
    }
}
