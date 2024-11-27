package com.example.shoesshop.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`Order`")
public class Order implements Serializable  {
    @Column(name = "orderId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "totalAmount", nullable = false)
    private float total_amount;

    @Column(name = "shipping_address")
    private String address;

    @Column(name = "recipient_phone")
    private String phone;

    @Column(name = "orderDate", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDate order_date;

    @Column(name = "oderStatus")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public enum OrderStatus {
        ADDED_TO_CARD, TO_PAY, TO_RECEIVE, COMPLETED, CANCELED, FEEDBACK_COMPLETED;
    }

    @ManyToOne
    @JoinColumn(name="customerId", nullable = true)
    private Account  customer;

    @ManyToOne
    @JoinColumn(name="employeeId", nullable = true)
    private Account  employee;

    @ManyToOne
    @JoinColumn(name="paymentMethodId", nullable = true)
    private PaymentMethod payment_method;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Order(Date orderDate, int customerId, PaymentMethod paymentMethod) {
    }

    public Order(float total_amount, String address, String phone, LocalDate order_date, OrderStatus orderStatus, Account customer, PaymentMethod payment_method) {
        this.total_amount = total_amount;
        this.address = address;
        this.phone = phone;
        this.order_date = order_date;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.payment_method = payment_method;
    }

    public Order(String address, String phone, LocalDate order_date, OrderStatus orderStatus, Account customer, PaymentMethod payment_method) {
        this.address = address;
        this.phone = phone;
        this.order_date = order_date;
        this.orderStatus = orderStatus;
        this.customer = customer;
        this.payment_method = payment_method;
    }

    public Order(String address, String phone, LocalDate order_date, OrderStatus orderStatus, Account customer) {
        this.address = address;
        this.phone = phone;
        this.order_date = order_date;
        this.orderStatus = orderStatus;
        this.customer = customer;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(float total_amount) {
        this.total_amount = total_amount;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Account getCustomer() {
        return customer;
    }

    public void setCustomer(Account customer) {
        this.customer = customer;
    }

    public Account getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PaymentMethod getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(PaymentMethod payment_method) {
        this.payment_method = payment_method;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmployee(Account employee) {
        this.employee = employee;
    }
}
