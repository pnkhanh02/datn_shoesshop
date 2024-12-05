package com.example.shoesshop.request;

import com.example.shoesshop.entity.ExchangeShoes;

import java.time.LocalDate;

public class ExchangeShoesUpdateRequest {
    private ExchangeShoes.STATUS status;
    private float exchangeShoesSales;

    public ExchangeShoesUpdateRequest() {
    }

    public ExchangeShoesUpdateRequest(ExchangeShoes.STATUS status, float exchangeShoesSales) {
        this.status = status;
        this.exchangeShoesSales = exchangeShoesSales;
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
}
