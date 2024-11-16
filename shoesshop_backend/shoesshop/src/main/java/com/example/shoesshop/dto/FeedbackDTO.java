package com.example.shoesshop.dto;

import com.example.shoesshop.entity.Feedback;

import java.time.LocalDate;

public class FeedbackDTO {
    private int id;
    private String comment;
    private LocalDate feedback_date;
    private Feedback.RATING rating;
    private int customer_id;
    private String customer_name;
    private int product_id;

    public FeedbackDTO() {
    }

    public FeedbackDTO(int id, String comment, LocalDate feedback_date, Feedback.RATING rating, int customer_id, String customer_name, int product_id) {
        this.id = id;
        this.comment = comment;
        this.feedback_date = feedback_date;
        this.rating = rating;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.product_id = product_id;
    }

    public FeedbackDTO(int id, String comment, LocalDate feedback_date, Feedback.RATING rating, int customer_id, int product_id) {
        this.id =id;
        this.comment = comment;
        this.feedback_date = feedback_date;
        this.rating = rating;
        this.customer_id = customer_id;
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getFeedback_date() {
        return feedback_date;
    }

    public void setFeedback_date(LocalDate feedback_date) {
        this.feedback_date = feedback_date;
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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
