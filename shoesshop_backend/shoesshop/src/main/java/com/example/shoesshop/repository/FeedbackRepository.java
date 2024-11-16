package com.example.shoesshop.repository;

import com.example.shoesshop.entity.Feedback;
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

public interface FeedbackRepository extends JpaRepository<Feedback, Integer>  {
    Feedback getFeedbackById(int id);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Feedback WHERE id IN(:ids)")
    void deleteByIds(@Param("ids") List<Integer> ids);

    <T> Page<Feedback> findAll(Specification<T> where, Pageable pageable);

    ArrayList<Feedback> findAll();
}
