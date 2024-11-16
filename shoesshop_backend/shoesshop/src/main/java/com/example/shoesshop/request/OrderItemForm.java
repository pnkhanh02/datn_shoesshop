package com.example.shoesshop.request;

public class OrderItemForm {
    private int id;
    private int quantity_item;

    public OrderItemForm() {
    }

    public OrderItemForm(int id, int quantity_item) {
        this.id = id;
        this.quantity_item = quantity_item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity_item() {
        return quantity_item;
    }

    public void setQuantity_item(int quantity_item) {
        this.quantity_item = quantity_item;
    }
}
