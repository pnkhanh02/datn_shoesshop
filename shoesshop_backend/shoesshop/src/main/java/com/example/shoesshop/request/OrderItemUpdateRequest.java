package com.example.shoesshop.request;

public class OrderItemUpdateRequest {
    private int quantity;

    public OrderItemUpdateRequest() {
    }

    public OrderItemUpdateRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
