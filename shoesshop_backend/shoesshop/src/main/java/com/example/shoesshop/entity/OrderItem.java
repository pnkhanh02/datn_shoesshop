package com.example.shoesshop.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OrderItem")
public class OrderItem implements Serializable  {
    @Column(name = "orderItemId")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "sellPrice", nullable = false)
    private float sell_price;

    @Column(name = "subtotal", nullable = false)
    private float subtotal;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name="orderId", nullable = true)
    private Order order;

    @ManyToOne
    @JoinColumn(name="product_detailId", nullable = true)
    private ProductDetail product_detail_order;

    @Column(name = "isfeedback", nullable = false)
    private boolean isFeedbackReceived;

    public OrderItem() {
    }

    public boolean isFeedbackReceived() {
        return isFeedbackReceived;
    }

    public OrderItem(float sell_price, float subtotal, int quantity, Order order, ProductDetail product_detail_order) {
        this.sell_price = sell_price;
        this.subtotal = subtotal;
        this.quantity = quantity;
        this.order = order;
        this.product_detail_order = product_detail_order;
    }

    public void setFeedbackReceived(boolean isFeedbackReceived) {
        this.isFeedbackReceived = isFeedbackReceived;
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductDetail getProduct_detail_order() {
        return product_detail_order;
    }

    public void setProduct_detail_order(ProductDetail product_detail_order) {
        this.product_detail_order = product_detail_order;
    }
}
