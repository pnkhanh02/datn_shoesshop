package com.example.shoesshop.entity;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Account{
    public Admin(String username, String phone, String password, String firstName, String lastName, String address, LocalDate birthday, String email, Role role, Gender gender, LocalDate createdDate) {
        super( username, phone, password, firstName, lastName, address, birthday, email, role, gender, createdDate);
    }


    public Admin() {
    }
}
