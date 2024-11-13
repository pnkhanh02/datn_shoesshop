package com.example.shoesshop.dto;

public class PaymentMethodDTO {
    private int id;
    private String name;
    private String description_payment;

    public PaymentMethodDTO() {
    }

    public PaymentMethodDTO(int id, String name, String description_payment) {
        this.id =id;
        this.name = name;
        this.description_payment = description_payment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
