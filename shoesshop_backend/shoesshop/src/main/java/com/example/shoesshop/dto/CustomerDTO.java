package com.example.shoesshop.dto;

import com.example.shoesshop.entity.Account;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class CustomerDTO {
    private int id;
    private String username;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private String firstName;
    private String lastName;
    private String email;
    private Account.Gender gender;
    private LocalDate createdDate;

    public CustomerDTO() {
    }

    public CustomerDTO(int id, String username, String address, LocalDate birthday, String email, Account.Gender gender, LocalDate createdDate) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.birthday = birthday;
        this.email = email;
        this.gender = gender;
        this.createdDate = createdDate;
    }

    public CustomerDTO(int id, String username, String address, LocalDate birthday, String firstName, String lastName, String email, Account.Gender gender, LocalDate createdDate) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.birthday = birthday;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.createdDate = createdDate;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Account.Gender getGender() {
        return gender;
    }

    public void setGender(Account.Gender gender) {
        this.gender = gender;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
