package com.example.shoesshop.dto;

import com.example.shoesshop.entity.Order;

public class ChangeStatusDTO {
    private int employee_id;

    private Order.OrderStatus orderStatus;

    public ChangeStatusDTO() {
    }

    public ChangeStatusDTO(int employee_id, Order.OrderStatus orderStatus) {
        this.employee_id = employee_id;
        this.orderStatus = orderStatus;
    }

    public int getCustomer_id() {
        return employee_id;
    }

    public void setCustomer_id(int customer_id) {
        this.employee_id = customer_id;
    }

    public Order.OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Order.OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
