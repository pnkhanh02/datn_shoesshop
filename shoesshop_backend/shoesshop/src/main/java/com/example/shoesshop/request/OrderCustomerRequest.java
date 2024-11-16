package com.example.shoesshop.request;

import java.util.List;

public class OrderCustomerRequest {
    private int customer_id;
    private String address;
    private String phone;
    private List<OrderItemForm> orderItemForms;
    private int payment_method_id;

    public OrderCustomerRequest() {
    }

    public OrderCustomerRequest(int customer_id, String address, String phone, List<OrderItemForm> orderItemForms) {
        this.customer_id = customer_id;
        this.address = address;
        this.phone = phone;
        this.orderItemForms = orderItemForms;
    }

    public OrderCustomerRequest(int customer_id, String address, String phone, List<OrderItemForm> orderItemForms, int payment_method_id) {
        this.customer_id = customer_id;
        this.address = address;
        this.phone = phone;
        this.orderItemForms = orderItemForms;
        this.payment_method_id = payment_method_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<OrderItemForm> getOrderItemForms() {
        return orderItemForms;
    }

    public void setOrderItemForms(List<OrderItemForm> orderItemForms) {
        this.orderItemForms = orderItemForms;
    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }
}
