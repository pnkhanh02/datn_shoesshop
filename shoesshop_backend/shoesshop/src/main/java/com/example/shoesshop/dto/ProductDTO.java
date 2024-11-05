package com.example.shoesshop.dto;

public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private String image_url;
    private float price;
    private String type_name;
    private int type_id;
    private float sale_percent;
    private int sale_id;
    private String gender_for;

    public ProductDTO() {
    }

    public ProductDTO(int id, String name, String description, String image_url, float price, String type_name, String gender_for) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
        this.type_name = type_name;
        this.gender_for = gender_for;
    }

    public ProductDTO(int id, String name, String description, String image_url, float price, String type_name,int type_id, float sale_percent, int sale_id, String gender_for) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
        this.type_name = type_name;
        this.type_id = type_id;
        this.sale_percent = sale_percent;
        this.sale_id = sale_id;
        this.gender_for = gender_for;
    }

    public ProductDTO(int id, String name, String description, String image_url, float price, String type_name, int type_id, String gender_for) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
        this.type_name = type_name;
        this.type_id = type_id;
        this.gender_for = gender_for;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public float getSale_percent() {
        return sale_percent;
    }

    public void setSale_percent(float sale_percent) {
        this.sale_percent = sale_percent;
    }

    public int getSale_id() {
        return sale_id;
    }

    public void setSale_id(int sale_id) {
        this.sale_id = sale_id;
    }

    public String getGender_for() {
        return gender_for;
    }

    public void setGender_for(String gender_for) {
        this.gender_for = gender_for;
    }
}
