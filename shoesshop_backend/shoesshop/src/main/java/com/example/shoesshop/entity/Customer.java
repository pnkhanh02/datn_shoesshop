package com.example.shoesshop.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends Account{
    @Getter
    @OneToMany(mappedBy = "customer")
    private List<Order> orders_buy;

    @OneToMany(mappedBy = "customer")
    private List<Feedback> feedbacks;

    public Customer(String username, String phone, String password, String firstName, String lastName, String address, LocalDate birthday, String email, Role role, Gender gender, LocalDate createdDate) {
        super( username, phone, password, firstName, lastName, address, birthday, email, role, gender, createdDate);
    }



    public Customer() {
    }

    public List<Order> getOrders_buy() {
        return orders_buy;
    }

    public void setOrders_buy(List<Order> orders_buy) {
        this.orders_buy = orders_buy;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
