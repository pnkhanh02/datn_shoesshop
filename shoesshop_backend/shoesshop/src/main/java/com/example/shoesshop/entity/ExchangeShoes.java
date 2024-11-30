package com.example.shoesshop.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "ExchangeShoes")
public class ExchangeShoes implements Serializable  {
    @Column(name = "exchangeShoesId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "exchangeShoesName", length = 255, nullable = false)
    private String exchangeShoesName;

    @Column(name = "exchangeShoesType", length = 255, nullable = true)
    private String exchangeShoesType;

    @Column(name = "purchaseDate", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDate purchaseDate;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "`description`", length = 255, nullable = false)
    private String description;

    @Column(name = "img_url", length = 255, nullable = false)
    private String img_url;

    @Column(name = "`status`", nullable = false)
    @Enumerated(EnumType.STRING)
    private STATUS status;

    @Column(name = "exchangeShoesSales", nullable = true)
    private float exchangeShoesSales;

    @Column(name = "used", nullable = false)
    private boolean used;

    public enum STATUS {
        PENDING,
        APPROVE,
        REJECT;
    }

    @ManyToOne()
    @JoinColumn(name = "customerId", nullable = true)
    private Account customer;

    public ExchangeShoes() {
    }

    public ExchangeShoes(int id, String exchangeShoesName, String exchangeShoesType, LocalDate purchaseDate, float price, String description, String img_url, STATUS status, float exchangeShoesSales, boolean used, Account customer) {
        this.id = id;
        this.exchangeShoesName = exchangeShoesName;
        this.exchangeShoesType = exchangeShoesType;
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.description = description;
        this.img_url = img_url;
        this.status = status;
        this.exchangeShoesSales = exchangeShoesSales;
        this.used = used;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
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

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }
}
