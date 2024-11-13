package com.example.shoesshop.repository;

import com.example.shoesshop.entity.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer>  {
    PaymentMethod findByName(String name);
    <T> Page<PaymentMethod> findAll(Specification<T> where, Pageable pageable);

    PaymentMethod getPaymentMethodById(int id);
    List<PaymentMethod> findAll();

    PaymentMethod deleteById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM PaymentMethod WHERE id IN(:ids)")
    void deleteByIds(@Param("ids") List<Integer> ids);
}
