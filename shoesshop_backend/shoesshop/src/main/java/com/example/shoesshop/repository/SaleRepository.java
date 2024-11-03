package com.example.shoesshop.repository;

import com.example.shoesshop.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
    Sale findById(int id);

    Sale getSaleById(int id);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Sale WHERE id IN (:ids)")
    void deleteByIds(@Param("ids") List<Integer> ids);

    <T> Page<Sale> findAll(Specification<T> where, Pageable pageable);

}
