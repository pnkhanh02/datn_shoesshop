package com.example.shoesshop.repository;

import com.example.shoesshop.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    Employee findByUsername(String username);

    <T> Page<Employee> findAll(Specification<T> where, Pageable pageable);
    boolean existsByUsername(String username);

    Employee getEmployeeById(int id);

    Employee deleteById(int id);
}
