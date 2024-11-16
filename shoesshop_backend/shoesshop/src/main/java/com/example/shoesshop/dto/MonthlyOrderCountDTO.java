package com.example.shoesshop.dto;

public class MonthlyOrderCountDTO {
    private String month;
    private String order_Count;

    public MonthlyOrderCountDTO() {
    }

    public MonthlyOrderCountDTO(String month, String order_Count) {
        this.month = month;
        this.order_Count = order_Count;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getOrder_Count() {
        return order_Count;
    }

    public void setOrder_Count(String order_Count) {
        this.order_Count = order_Count;
    }
}
