package com.example.shoesshop.repository;

import com.example.shoesshop.entity.ChatHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Integer> {
    @Transactional
    @Query("SELECT c FROM ChatHistory c WHERE c.customer.id = :customerId")
    List<ChatHistory> findAllByCustomerId(@Param("customerId") int customerId);
}
