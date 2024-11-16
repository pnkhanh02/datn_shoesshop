package com.example.shoesshop.dto;

import com.example.shoesshop.entity.Order;

import java.time.LocalDate;

public class OrderDTO {
    private int id;
    private float total_amount;
    private LocalDate oder_date;
    private Order.OderStatus oderStatus;
    private int customer_id;
    private String customer_name;
    private String employee_name;
    private int employee_id;
    private String address;
    private String phone;
    private String payment_method;
    private int payment_method_id;

    public OrderDTO() {
    }

    public OrderDTO(int id, float total_amount, LocalDate oder_date, Order.OderStatus oderStatus, String customer_name, String address, String phone, String payment_method) {
        this.id = id;
        this.total_amount = total_amount;
        this.oder_date = oder_date;
        this.oderStatus = oderStatus;
        this.customer_name = customer_name;
        this.address = address;
        this.phone = phone;
        this.payment_method = payment_method;
    }

    public OrderDTO(int id, float total_amount, LocalDate oder_date, Order.OderStatus oderStatus, String customer_name, String employee_name, String address, String phone, String payment_method) {
        this.id = id;
        this.total_amount = total_amount;
        this.oder_date = oder_date;
        this.oderStatus = oderStatus;
        this.customer_name = customer_name;
        this.employee_name = employee_name;
        this.address = address;
        this.phone = phone;
        this.payment_method = payment_method;
    }

    public OrderDTO(int id, float total_amount, LocalDate oder_date, Order.OderStatus oderStatus, int customer_id, int employee_id, int payment_method_id) {
        this.id =id;
        this.total_amount = total_amount;
        this.oder_date = oder_date;
        this.oderStatus = oderStatus;
        this.customer_id = customer_id;
        this.employee_id = employee_id;
        this.payment_method_id = payment_method_id;
    }

    public OrderDTO(int id, LocalDate oder_date, Order.OderStatus oderStatus, int customer_id) {
        this.id = id;

        this.oder_date = oder_date;
        this.oderStatus = oderStatus;
        this.customer_id = customer_id;
    }

    public OrderDTO(int id, float total_amount, LocalDate oder_date, Order.OderStatus oderStatus, int customer_id, int payment_method_id) {
        this.id = id;
        this.total_amount = total_amount;
        this.oder_date = oder_date;
        this.oderStatus = oderStatus;
        this.customer_id = customer_id;
        this.payment_method_id = payment_method_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
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

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }
}
