package com.example.shoesshop.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "`Product`")
public class Product implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "`name`", length = 50, nullable = false)
    private String name;

    @Column(name = "`description`", length = 200)
    private String description;


    @Column(name = "image_url", length = 255)
    private String image_url;


    @Column(name = "price", nullable = false)
    private float price;


    @ManyToOne
    @JoinColumn(name="saleId", nullable = true)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = true)
    private ProductType typeProduct;

    @OneToMany(mappedBy = "id")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "product_detail")
    private List<ProductDetail> productDetails;


    @Column(name = "`gender_type`")
    @Enumerated(EnumType.STRING)
    private GenderType gender_type;

    public enum GenderType{
        MALE, FEMALE, UNISEX;
    }


    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public Product(String name, String description, String image_url, float price, ProductType typeProduct) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
        this.typeProduct = typeProduct;
    }

    public GenderType getGender_type() {
        return gender_type;
    }

    public void setGender_type(GenderType gender_type) {
        this.gender_type = gender_type;
    }

    public void setProductDetails(List<ProductDetail> productDetails) {
        this.productDetails = productDetails;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public Product() {
    }


    public Product(String name, String description, String image_url, float price, Sale sale, ProductType typeProduct, GenderType gender_type) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.price = price;
        this.sale = sale;
        this.typeProduct = typeProduct;
        this.gender_type = gender_type;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ProductType getTypeProduct() {
        return typeProduct;
    }

    public void setTypeProduct(ProductType typeProduct) {
        this.typeProduct = typeProduct;
    }
}
