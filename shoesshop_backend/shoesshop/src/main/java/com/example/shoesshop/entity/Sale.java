package com.example.shoesshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Sale")
public class Sale implements Serializable {
    @Column(name = "saleID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "saleInfo", length = 100, nullable = false)
    private String sale_info;

    @Column(name = "percentSale", nullable = false)
    private float percent_sale;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "startSale", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @CreationTimestamp
    private LocalDate start_date;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "endSale", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    @CreationTimestamp
    private LocalDate end_date;

    @OneToMany(mappedBy = "sale")
    private List<Product> products;

    public Sale() {
    }

    public Sale(String sale_info, float percent_sale, LocalDate start_date, LocalDate end_date) {
        this.sale_info = sale_info;
        this.percent_sale = percent_sale;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSale_info() {
        return sale_info;
    }

    public void setSale_info(String sale_info) {
        this.sale_info = sale_info;
    }

    public float getPercent_sale() {
        return percent_sale;
    }

    public void setPercent_sale(float percent_sale) {
        this.percent_sale = percent_sale;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
