package com.example.shoesshop.dto;

public class TopRatingProductDTO {
    private int productId;
    private String productName;
    private String imageUrl;
    private float productPrice;
    private float avgRating;

    public TopRatingProductDTO() {
    }

    public TopRatingProductDTO(int productId, String productName, String imageUrl, float productPrice, float avgRating) {
        this.productId = productId;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.productPrice = productPrice;
        this.avgRating = avgRating;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }
}
