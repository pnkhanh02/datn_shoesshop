package com.example.shoesshop.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MonthlyRevenueDTO {
    private int month;
    private BigDecimal totalAmount;

    public MonthlyRevenueDTO() {
    }

    public MonthlyRevenueDTO(int month, BigDecimal totalAmount) {
        this.month = month;
        this.totalAmount = totalAmount;
    }
    public String formatTotalAmount() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##"); // Định dạng 2 chữ số thập phân
        return decimalFormat.format(totalAmount);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
