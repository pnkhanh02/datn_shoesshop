package com.example.shoesshop.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "`Account`")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "`role`", discriminatorType = DiscriminatorType.STRING)
public class Account implements Serializable {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", length = 50, nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "email", length = 50, nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "`password`", length = 255, nullable = false)
    private String password;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "address", length = 200, nullable = false)
    private String address;

    @Column(name = "birthday", updatable = true)
    private LocalDate birthday;

    public enum Gender {
        MALE, FEMALE, UNKNOWN;
    }

    public enum Role {
        ADMIN, EMPLOYEE, CUSTOMER;
    }

    @Column(name = "`role`", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "Gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;



    @Column(name = "create_date", updatable = false)
    @CreationTimestamp
    private LocalDate createdDate;


    @PrePersist
    public void prePersist() {
        if (role == null) {
            role = Role.CUSTOMER;
        }

    }



    //constructor
    public Account(String username, String phone, String password, String firstName, String lastName, String address, LocalDate birthday, String email, Role role, Gender gender, LocalDate createdDate) {
        this.username = username;
        this.phone = phone;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthday = birthday;
        this.email = email;
        this.role = role;
        this.gender = gender;
        this.createdDate = createdDate;
    }

    public Account(int id, String username, String password, String firstName, String lastName, String address, LocalDate birthday, String email, Role role, Gender gender, LocalDate createdDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthday = birthday;
        this.email = email;
        this.role = role;
        this.gender = gender;
        this.createdDate = createdDate;
    }

    public Account() {
    }

    //getter, setter


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
