package com.example.shoesshop.repository;

import com.example.shoesshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Product getProductById(int id);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Product WHERE id IN(:ids)")
    public void deleteByIds(@Param("ids") List<Integer> ids);
}
