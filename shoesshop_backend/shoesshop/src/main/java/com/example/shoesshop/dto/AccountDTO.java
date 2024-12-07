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
    private String phone;
    private String address;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthday;
    private String createdDate;
    private Account.Role role;

    public AccountDTO() {
    }

    public AccountDTO(String username, String lastName, String createdDate, Account.Role role) {
        this.username = username;
        this.lastName = lastName;
        this.createdDate = createdDate;
        this.role = role;
    }

    public AccountDTO(String username, String lastName,String firstName, String createdDate, Account.Role role, String email, int id, Account.Gender gender) {
        this.username=username;
        this.lastName=lastName;
        this.firstName=firstName;
        this.createdDate=createdDate;
        this.role=role;
        this.email=email;
        this.id=id;
        this.gender=gender;
    }

    public AccountDTO(int id, String username, String email, Account.Gender gender, String lastName, String firstName, String phone, String address, LocalDate birthday, String createdDate, Account.Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.gender = gender;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phone = phone;
        this.address = address;
        this.birthday = birthday;
        this.createdDate = createdDate;
        this.role = role;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Account.Role getRole() {
        return role;
    }

    public void setRole(Account.Role role) {
        this.role = role;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
