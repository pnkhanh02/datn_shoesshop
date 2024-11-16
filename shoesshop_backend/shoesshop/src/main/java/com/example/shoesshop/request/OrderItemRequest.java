package com.example.shoesshop.request;

public class OrderItemRequest {
    private int quantity;
    private int customer_id;
    private int product_detail_id;

    public OrderItemRequest() {
    }

    public OrderItemRequest(int quantity, int customer_id, int product_detail_id) {
        this.quantity = quantity;
        this.customer_id = customer_id;
        this.product_detail_id = product_detail_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getProduct_detail_id() {
        return product_detail_id;
    }

    public void setProduct_detail_id(int product_detail_id) {
        this.product_detail_id = product_detail_id;
    }
}
