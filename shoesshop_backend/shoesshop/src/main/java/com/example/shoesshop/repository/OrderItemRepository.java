package com.example.shoesshop.repository;

import com.example.shoesshop.entity.Order;
import com.example.shoesshop.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    OrderItem getOrderItemById(int id);

    List<OrderItem> getOrderItemByOrder(Order order);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM OrderItem WHERE id IN(:ids)")
    void deleteByIds(@Param("ids") List<Integer> ids);

    <T> Page<OrderItem> findAll(Specification<T> where, Pageable pageable);
}
