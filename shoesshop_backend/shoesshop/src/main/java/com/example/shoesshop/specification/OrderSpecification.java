package com.example.shoesshop.specification;

import com.example.shoesshop.entity.Order;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification implements Specification<Order> {
    private String field;
    private String operator;
    private Object value;

    public OrderSpecification(String field, String operator, Object value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(operator.equalsIgnoreCase("LIKE")) {
            return criteriaBuilder.like(root.get(field), "%" + value.toString() + "%");
        }
        if (operator.equalsIgnoreCase("=")) {
            return criteriaBuilder.equal(root.get(field), value);
        }
        return null;
    }
}
