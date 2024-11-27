package com.example.shoesshop.request;

import com.example.shoesshop.entity.Order;

import java.util.Date;

public class OrderUpdateRequest {

    private Date order_date;

    private Order.OrderStatus orderStatus;

    private int employee_id;

    private int payment_method_id;

    public OrderUpdateRequest() {
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOder_date(Date order_date) {
        this.order_date = order_date;
    }

    public Order.OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOderStatus(Order.OrderStatus oderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public int getPayment_method_id() {
        return payment_method_id;
    }

    public void setPayment_method_id(int payment_method_id) {
        this.payment_method_id = payment_method_id;
    }
}
