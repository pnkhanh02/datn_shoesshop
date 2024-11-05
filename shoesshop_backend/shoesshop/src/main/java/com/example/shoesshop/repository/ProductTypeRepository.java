package com.example.shoesshop.repository;

import com.example.shoesshop.entity.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductType, Integer> {
    ProductType findById(int typeId);

    ProductType getProductTypeById(int id);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductType WHERE id IN(:ids)")
    public void deleteByIds(@Param("ids") List<Integer> ids);

    <T> Page<ProductType> findAll(Specification<T> where, Pageable pageable);

}
