package com.example.shoesshop.dto;

import com.example.shoesshop.entity.Account;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class AccountDTO {
    private int id;
    private String username;
    private  String email;
    private Account.Gender gender;
    private String lastName;
    private  String firstName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate;
    private Account.Role role;

    public AccountDTO() {
    }

    public AccountDTO(String username, String lastName, LocalDate createdDate, Account.Role role) {
        this.username = username;
        this.lastName = lastName;
        this.createdDate = createdDate;
        this.role = role;
    }

    public AccountDTO(String username, String lastName,String firstName, LocalDate createdDate, Account.Role role, String email, int id, Account.Gender gender) {
        this.username=username;
        this.lastName=lastName;
        this.firstName=firstName;
        this.createdDate=createdDate;
        this.role=role;
        this.email=email;
        this.id=id;
        this.gender=gender;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Account.Role getRole() {
        return role;
    }

    public void setRole(Account.Role role) {
        this.role = role;
    }
}
