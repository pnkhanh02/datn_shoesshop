package com.example.shoesshop.request;

import com.example.shoesshop.entity.Product;

public class ProductRequest {
    private String name;
    private String description;
    private String image_url;
    private float price;
    private int type_id;
    private Product.GenderType genderType;
    private int sale_id;
    public ProductRequest() {
    }

    public ProductRequest(String name, String description, String image_url, float price, int type_id, Product.GenderType genderType, int sale_id) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
        this.type_id = type_id;
        this.genderType = genderType;
        this.sale_id = sale_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public Product.GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(Product.GenderType genderType) {
        this.genderType = genderType;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }
}
