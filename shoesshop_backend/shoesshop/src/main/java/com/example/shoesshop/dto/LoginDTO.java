package com.example.shoesshop.dto;

import com.example.shoesshop.entity.Account;

public class LoginDTO {
    private int id;
    private String username;
    private Account.Role role;
    private String token;

    public LoginDTO() {
    }

    public LoginDTO(int id, String username, Account.Role role, String token) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Account.Role getRole() {
        return role;
    }

    public void setRole(Account.Role role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
