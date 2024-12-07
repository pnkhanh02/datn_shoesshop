package com.example.shoesshop.dto;

public class OrderItemDTO {
    private int id;
    private float sell_price;
    private float subtotal;
    private int quantity;
    private int order_id;
    private int product_detail_id;
    private String product_detail_name;
    private String size;
    private String color;
    private String url_img;
    private boolean isFeedbackReceived;

    public OrderItemDTO() {
    }

    public OrderItemDTO(int id, float sell_price, float subtotal, int quantity, int order_id, int product_detail_id, String product_detail_name, String size, String color, String url_img, boolean isFeedbackReceived) {
        this.id = id;
        this.sell_price = sell_price;
        this.subtotal = subtotal;
        this.quantity = quantity;
        this.order_id = order_id;
        this.product_detail_id = product_detail_id;
        this.product_detail_name = product_detail_name;
        this.size = size;
        this.color = color;
        this.url_img = url_img;
        this.isFeedbackReceived = isFeedbackReceived;
    }

    public OrderItemDTO(int id, float sell_price, float subtotal, int quantity, int order_id, int product_detail_id) {
        this.id = id;
        this.sell_price = sell_price;
        this.subtotal = subtotal;
        this.quantity = quantity;
        this.order_id = order_id;
        this.product_detail_id = product_detail_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getSell_price() {
        return sell_price;
    }

    public void setSell_price(float sell_price) {
        this.sell_price = sell_price;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_detail_id() {
        return product_detail_id;
    }

    public void setProduct_detail_id(int product_detail_id) {
        this.product_detail_id = product_detail_id;
    }

    public String getProduct_detail_name() {
        return product_detail_name;
    }

    public void setProduct_detail_name(String product_detail_name) {
        this.product_detail_name = product_detail_name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUrl_img() {
        return url_img;
    }

    public void setUrl_img(String url_img) {
        this.url_img = url_img;
    }

    public boolean isFeedbackReceived() {
        return isFeedbackReceived;
    }

    public void setFeedbackReceived(boolean isFeedbackReceived) {
        this.isFeedbackReceived = isFeedbackReceived;
    }
}
