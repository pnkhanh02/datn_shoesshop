package com.example.shoesshop.request;

public class AccountUpdateRequest {
    private String firstName;
    private String lastName;
    private String address;
    private String birthday;

    public AccountUpdateRequest() {
    }

    public AccountUpdateRequest(String firstName, String lastName, String address, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthday = birthday;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
