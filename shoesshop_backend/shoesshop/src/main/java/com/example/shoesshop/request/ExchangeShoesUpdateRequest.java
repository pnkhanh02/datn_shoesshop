package com.example.shoesshop.request;

import com.example.shoesshop.entity.ExchangeShoes;

import java.time.LocalDate;

public class ExchangeShoesUpdateRequest {
    private int exchangeShoesId;
    private String exchangeShoesName;
    private String exchangeShoesType;
    private LocalDate purchaseDate;
    private float price;
    private String description;
    private String img_url;
    private ExchangeShoes.STATUS status;
    private boolean used;
    private int customerId;
    private float exchangeShoesSales;

    public ExchangeShoesUpdateRequest() {
    }

    public ExchangeShoesUpdateRequest(int exchangeShoesId, ExchangeShoes.STATUS status, float exchangeShoesSales, boolean used) {
        this.exchangeShoesId = exchangeShoesId;
        this.status = status;
        this.exchangeShoesSales = exchangeShoesSales;
        this.used = used;
    }

    public int getExchangeShoesId() {
        return exchangeShoesId;
    }

    public void setExchangeShoesId(int exchangeShoesId) {
        this.exchangeShoesId = exchangeShoesId;
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

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public ExchangeShoes.STATUS getStatus() {
        return status;
    }

    public void setStatus(ExchangeShoes.STATUS status) {
        this.status = status;
    }

    public float getExchangeShoesSales() {
        return exchangeShoesSales;
    }

    public void setExchangeShoesSales(float exchangeShoesSales) {
        this.exchangeShoesSales = exchangeShoesSales;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
