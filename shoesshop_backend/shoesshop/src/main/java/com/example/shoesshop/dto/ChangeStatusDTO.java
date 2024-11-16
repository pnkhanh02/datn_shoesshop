package com.example.shoesshop.dto;

import com.example.shoesshop.entity.Order;

public class ChangeStatusDTO {
    private int employee_id;

    private Order.OderStatus oderStatus;

    public ChangeStatusDTO() {
    }

    public ChangeStatusDTO(int employee_id, Order.OderStatus oderStatus) {
        this.employee_id = employee_id;
        this.oderStatus = oderStatus;
    }

    public int getCustomer_id() {
        return employee_id;
    }

    public void setCustomer_id(int customer_id) {
        this.employee_id = customer_id;
    }

    public Order.OderStatus getOderStatus() {
        return oderStatus;
    }

    public void setOderStatus(Order.OderStatus oderStatus) {
        this.oderStatus = oderStatus;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }
}
