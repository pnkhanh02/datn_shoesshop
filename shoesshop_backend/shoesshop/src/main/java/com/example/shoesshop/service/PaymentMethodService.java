package com.example.shoesshop.service;

import com.example.shoesshop.entity.PaymentMethod;
import com.example.shoesshop.repository.PaymentMethodRepository;
import com.example.shoesshop.request.PaymentMethodRequest;
import com.example.shoesshop.specification.PaymentMethodSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class PaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public Page<PaymentMethod> getAllPaymentMethods(Pageable pageable, String search) {
        Specification<PaymentMethod> where = null;
        if(!StringUtils.isEmpty(search)){
            PaymentMethodSpecification specification = new PaymentMethodSpecification("name","LIKE", search);
            where = Specification.where(specification);
        }
        return paymentMethodRepository.findAll(Specification.where(where), pageable);
    }

    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethod getPaymentMethodByName(String name) {
        return paymentMethodRepository.findByName(name);
    }

    public void createPaymentMethod(PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = new PaymentMethod();

        paymentMethod.setName(paymentMethodRequest.getName());
        paymentMethod.setDescription_payment(paymentMethodRequest.getDescription_payment());
        paymentMethodRepository.save(paymentMethod);
    }

    public void updatePaymentMethod(int id, PaymentMethodRequest paymentMethodRequest) {
        PaymentMethod paymentMethod = paymentMethodRepository.getPaymentMethodById(id);
        paymentMethod.setName(paymentMethodRequest.getName());
        paymentMethod.setDescription_payment(paymentMethodRequest.getDescription_payment());
        paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod getPaymentMethodById(int id) {
        return paymentMethodRepository.getPaymentMethodById(id);
    }

    public void deletePaymentMethod(int id) {
        paymentMethodRepository.deleteById(id);
    }

    public void deletePaymentMethods(List<Integer> ids) {
        paymentMethodRepository.deleteByIds(ids);
    }
}
