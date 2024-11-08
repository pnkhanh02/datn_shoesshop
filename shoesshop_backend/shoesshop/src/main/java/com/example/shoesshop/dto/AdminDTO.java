package com.example.shoesshop.dto;

import com.example.shoesshop.entity.Account;

import java.time.LocalDate;

public class AdminDTO {
    private int id;
    private String username;
    private String address;
    private LocalDate birthday;
    private String email;
    private LocalDate createdDate;
    private Account.Gender gender;

    public AdminDTO() {
    }

    public AdminDTO(int id, String username, String address, LocalDate birthday, String email, LocalDate createdDate, Account.Gender gender) {
        this.id=id;
        this.username = username;
        this.address = address;
        this.birthday = birthday;
        this.email = email;
        this.createdDate = createdDate;
        this.gender = gender;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Account.Gender getGender() {
        return gender;
    }

    public void setGender(Account.Gender gender) {
        this.gender = gender;
    }
}
