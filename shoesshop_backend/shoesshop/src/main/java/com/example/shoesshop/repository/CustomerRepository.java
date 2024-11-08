package com.example.shoesshop.repository;

import com.example.shoesshop.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer>  {
    Customer findByUsername(String username);

    <T> Page<Customer> findAll(Specification<T> where, Pageable pageable);
    boolean existsByUsername(String username);

    Customer getCustomerById(int id);

    Customer deleteById(int id);
}
