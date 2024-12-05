package com.example.shoesshop.request;

import com.example.shoesshop.entity.ExchangeShoes;

import java.time.LocalDate;

public class ExchangeShoesRequest {
    private String exchangeShoesName;
    private String exchangeShoesType;
    private String purchaseDate;
    private float price;
    private String description;
    private String img_url;
    private ExchangeShoes.STATUS status;
    private boolean used;
    private int customerId;

    public ExchangeShoesRequest() {
    }

    public ExchangeShoesRequest(String exchangeShoesName, String exchangeShoesType, String purchaseDate, float price, String description, String img_url, ExchangeShoes.STATUS status, boolean used, int customerId) {
        this.exchangeShoesName = exchangeShoesName;
        this.exchangeShoesType = exchangeShoesType;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.description = description;
        this.img_url = img_url;
        this.status = status;
        this.used = used;
        this.customerId = customerId;
    }

    public String getExchangeShoesName() {
        return exchangeShoesName;
    }

    public void setExchangeShoesName(String exchangeShoesName) {
        this.exchangeShoesName = exchangeShoesName;
    }

    public String getExchangeShoesType() {
        return exchangeShoesType;
    }

    public void setExchangeShoesType(String exchangeShoesType) {
        this.exchangeShoesType = exchangeShoesType;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public ExchangeShoes.STATUS getStatus() {
        return status;
    }

    public void setStatus(ExchangeShoes.STATUS status) {
        this.status = status;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
