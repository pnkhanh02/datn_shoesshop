package com.example.shoesshop.request;

import com.example.shoesshop.entity.Feedback;

public class FeedbackRequest {
    private String comment;
    private Feedback.RATING rating;
    private int customer_id;
    private int product_id;
    private int order_id;

    public FeedbackRequest() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Feedback.RATING getRating() {
        return rating;
    }

    public void setRating(Feedback.RATING rating) {
        this.rating = rating;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
