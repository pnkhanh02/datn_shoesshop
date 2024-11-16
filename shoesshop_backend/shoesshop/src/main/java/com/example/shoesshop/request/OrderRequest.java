package com.example.shoesshop.request;

import com.example.shoesshop.entity.Order;

import java.time.LocalDate;

public class OrderRequest {
    private float total_amount;

    private LocalDate oder_date;

    private Order.OderStatus oderStatus;

    private int customer_id;

    private int employee_id;

    private String address;

    private String phone;

    private int payment_method_id;

    public OrderRequest() {
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public LocalDate getOder_date() {
        return oder_date;
    }

    public void setOder_date(LocalDate oder_date) {
        this.oder_date = oder_date;
    }

    public Order.OderStatus getOderStatus() {
        return oderStatus;
    }

    public void setOderStatus(Order.OderStatus oderStatus) {
        this.oderStatus = oderStatus;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
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

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }
}
