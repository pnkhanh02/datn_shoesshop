package com.example.shoesshop.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("EMPLOYEE")
public class Employee extends Account{
    @OneToMany(mappedBy = "employee")
    private List<Order> orders_check;

    public List<Order> getOrders_check() {
        return orders_check;
    }

    public void setOrders_check(List<Order> orders_check) {
        this.orders_check = orders_check;
    }

    public Employee(String username, String phone, String password, String firstName, String lastName, String address, LocalDate birthday, String email, Role role, Gender gender, LocalDate createdDate) {
        super( username, phone, password, firstName, lastName, address, birthday, email, role, gender, createdDate);
    }


    public Employee() {
    }
}
