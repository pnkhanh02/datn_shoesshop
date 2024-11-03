package com.example.shoesshop.dto;

public class SaleDTO {
    private int id;
    private String sale_info;

    private float percent_sale;

    private String start_date;

    private String end_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSale_info() {
        return sale_info;
    }

    public void setSale_info(String sale_info) {
        this.sale_info = sale_info;
    }

    public float getPercent_sale() {
        return percent_sale;
    }

    public void setPercent_sale(float percent_sale) {
        this.percent_sale = percent_sale;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
