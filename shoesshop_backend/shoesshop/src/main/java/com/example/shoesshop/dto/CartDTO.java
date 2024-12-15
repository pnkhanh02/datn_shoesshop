package com.example.shoesshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartDTO {
    private int orderItem_id;
    private String imgSrc;
    private int quantity;
    private float subtotal;
    private float price;
    private String size;
    private String name;
    private String brand;
    private String color;
//    private int saleId;
//    private float sale_percent;

    public CartDTO() {
    }

    public CartDTO(int orderItem_id, String imgSrc, int quantity, float subtotal, float price, String size, String name, String brand, String color) {
        this.orderItem_id = orderItem_id;
        this.imgSrc = imgSrc;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.price = price;
        this.size = size;
        this.name = name;
        this.brand = brand;
        this.color = color;
    }



    public int getOrderItem_id() {
        return orderItem_id;
    }

    public void setOrderItem_id(int orderItem_id) {
        this.orderItem_id = orderItem_id;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

//    public int getSaleId() {
//        return saleId;
//    }
//
//    public void setSaleId(int saleId) {
//        this.saleId = saleId;
//    }
//
//    public float getSale_percent() {
//        return sale_percent;
//    }
//
//    public void setSale_percent(float sale_percent) {
//        this.sale_percent = sale_percent;
//    }

    @JsonProperty("imgSrc")
    public String getimgSrc() {
        return imgSrc;
    }
    @JsonProperty("imgSrc")
    public void setimgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
