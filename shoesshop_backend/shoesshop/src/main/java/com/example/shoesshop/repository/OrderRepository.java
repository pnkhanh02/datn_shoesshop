package com.example.shoesshop.repository;

import com.example.shoesshop.entity.Account;
import com.example.shoesshop.entity.Order;
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

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order getOrderById(int id);
    List<Order> getOrderByCustomer(Account customer);

    void deleteById(int id);
    @Modifying
    @Transactional
    @Query("DELETE FROM Order WHERE id IN(:ids)")

    void deleteByIds(@Param("ids") List<Integer> ids);

    <T> Page<Order> findAll(Specification<T> where, Pageable pageable);

    ArrayList<Order> findByCustomer(Account customer);

    ArrayList<Order> findAll();

    //    ArrayList<Order> findByOderStatus(Order.OderStatus oderStatus);
    @Query("SELECT o FROM Order o WHERE o.orderStatus IN :statuses")

    ArrayList<Order> findByOderStatus(List<Order.OrderStatus> statuses);


    @Query(value = "SELECT COALESCE(SUM(o.totalAmount), 0) AS totalAmount, " +
            "MONTH(o.orderDate) AS month " +
            "FROM `Order` o " +
            "WHERE o.oderStatus IN ('COMPLETE', 'FEEDBACK_COMPLETED') " +
            "AND YEAR(o.orderDate) = YEAR(CURDATE()) " +
            "GROUP BY MONTH(o.orderDate) " +
            "ORDER BY MONTH(o.orderDate)",
            nativeQuery = true)
    List<Object[]> getTotalAmountByMonthForCurrentYear();

    @Query(value = "SELECT MONTH(o.orderDate) AS month, COUNT(*) AS orderCount " +
            "FROM `Order` o " +
            "WHERE o.oderStatus IN ('COMPLETE', 'FEEDBACK_COMPLETED') " +
            "AND YEAR(o.orderDate) = YEAR(CURDATE()) " +
            "GROUP BY MONTH(o.orderDate) " +
            "ORDER BY MONTH(o.orderDate)",
            nativeQuery = true)
    List<Object[]> getOrderCountByMonthForCurrentYear();
}
