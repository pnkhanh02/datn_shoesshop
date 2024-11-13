package com.example.shoesshop.request;

public class PaymentMethodRequest {
    private String name;
    private String description_payment;

    public PaymentMethodRequest() {
    }

    public PaymentMethodRequest(String name, String description_payment) {
        this.name = name;
        this.description_payment = description_payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription_payment() {
        return description_payment;
    }

    public void setDescription_payment(String description_payment) {
        this.description_payment = description_payment;
    }
}
