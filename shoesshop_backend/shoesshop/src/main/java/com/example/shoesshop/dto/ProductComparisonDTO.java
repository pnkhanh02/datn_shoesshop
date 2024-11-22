package com.example.shoesshop.dto;

public class ProductComparisonDTO {
    private int productId;

    private String name;

    private float price;

    private int uniqueSizes; // Count of unique sizes available

    private int uniqueColors; // Count of unique colors (if applicable)

    private float averageRating; // Average feedback rating

    private String imageUrl;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getUniqueSizes() {
        return uniqueSizes;
    }

    public void setUniqueSizes(int uniqueSizes) {
        this.uniqueSizes = uniqueSizes;
    }

    public int getUniqueColors() {
        return uniqueColors;
    }

    public void setUniqueColors(int uniqueColors) {
        this.uniqueColors = uniqueColors;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
