package com.example.shoesshop.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "PaymentMethod")
public class PaymentMethod implements Serializable {
    @Column(name = "paymentMethodId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "paymentName", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "descriptionPayment", nullable = false, length = 200)
    private String description_payment;

    @OneToMany(mappedBy = "payment_method")
    private List<Order> orders;
}
