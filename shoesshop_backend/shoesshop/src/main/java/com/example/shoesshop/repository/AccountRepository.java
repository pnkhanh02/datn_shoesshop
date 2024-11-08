package com.example.shoesshop.repository;

import com.example.shoesshop.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String email);
    Account findByUsername(String username);

    <T> Page<Account> findAll(Specification<T> where, Pageable pageable);

    boolean existsByUsername(String username);

    Account getAccountById(int id);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Account WHERE id IN(:ids)")
    public void deleteByIds(@Param("ids") List<Integer> ids);
}
