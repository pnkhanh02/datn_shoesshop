package com.example.shoesshop.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "`ProductType`")
public class ProductType implements Serializable {
    @Column(name = "type_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type_name", length = 50, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "typeProduct")
    private List<Product> products;
    public ProductType() {
    }

    public ProductType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
