package com.example.shoesshop.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ProductDetail")
public class ProductDetail implements Serializable {
    @Column(name = "id_detail")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "img_url", length = 255, nullable = false)
    private String img_url;

    @Column(name = "color", length = 30, nullable = false)
    private String color;

    @Column(name = "size", length = 20, nullable = false)
    private String size;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = true)
    private Product product_detail;

    @OneToMany(mappedBy = "product_detail_order")
    private List<OrderItem> orderItems;

    public ProductDetail(int quantity, String img_url, String color, String size, Product product_detail) {
        this.quantity = quantity;
        this.img_url = img_url;
        this.color = color;
        this.size = size;
        this.product_detail = product_detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Product getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(Product product_detail) {
        this.product_detail = product_detail;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public ProductDetail() {
    }
}
