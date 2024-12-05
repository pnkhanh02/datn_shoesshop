package com.example.shoesshop.repository;

import com.example.shoesshop.entity.ExchangeShoes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface ExchangeShoesRepository extends JpaRepository<ExchangeShoes, Integer> {
    ExchangeShoes getExchangeShoesById(int id);

    void deleteById(int id);

    @Transactional
    @Query("SELECT e FROM ExchangeShoes e WHERE e.customer.id = :customerId")
    <T> Page<ExchangeShoes> findAllByCustomerId(@Param("customerId") int customerId, Pageable pageable);


    @Modifying
    @Transactional
    @Query("DELETE FROM ExchangeShoes WHERE id IN(:ids)")
    void deleteByIds(@Param("ids") List<Integer> ids);

    <T> Page<ExchangeShoes> findAll(Specification<T> where, Pageable pageable);

    ArrayList<ExchangeShoes> findAll();
}
