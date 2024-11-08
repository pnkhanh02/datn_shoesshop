package com.example.shoesshop.repository;

import com.example.shoesshop.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByUsername(String username);

    <T> Page<Admin> findAll(Specification<T> where, Pageable pageable);
    boolean existsByUsername(String username);

    Admin getAdminById(int id);

    void deleteById(int id);
}
