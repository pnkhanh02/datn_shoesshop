package com.example.shoesshop.repository;

import com.example.shoesshop.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer>, JpaSpecificationExecutor<ProductDetail> {

    ProductDetail getDetailById(int id);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductDetail WHERE id IN(:ids)")
    void deleteByIds(@Param("ids") List<Integer> ids);
}
