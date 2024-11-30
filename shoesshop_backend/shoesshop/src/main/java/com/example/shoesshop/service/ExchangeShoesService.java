package com.example.shoesshop.service;

import com.example.shoesshop.entity.*;
import com.example.shoesshop.repository.ExchangeShoesRepository;
import com.example.shoesshop.request.ExchangeShoesRequest;
import com.example.shoesshop.request.FeedbackRequest;
import com.example.shoesshop.specification.ExchangeShoesSpecification;
import com.example.shoesshop.specification.FeedbackSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExchangeShoesService {
    @Autowired
    private ExchangeShoesRepository exchangeShoesRepository;

    @Autowired
    private CustomerService customerService;

    public Page<ExchangeShoes> getAllExchangeShoes(Pageable pageable, String search) {
        Specification<ExchangeShoes> where = null;
        if(!StringUtils.isEmpty(search)){
            ExchangeShoesSpecification exchangeShoesSpecification = new ExchangeShoesSpecification("exchangeShoesName","LIKE", search);
            where = Specification.where(exchangeShoesSpecification);
        }
        return exchangeShoesRepository.findAll(Specification.where(where), pageable);
    }

    public void createExchangeShoes(ExchangeShoesRequest exchangeShoesRequest) {
        Customer customer = customerService.getCustomerById(exchangeShoesRequest.getCustomerId());
        //LocalDate creating_date = LocalDate.now();
        ExchangeShoes exchangeShoes = new ExchangeShoes();

        exchangeShoes.setExchangeShoesName(exchangeShoesRequest.getExchangeShoesName());
        exchangeShoes.setExchangeShoesType(exchangeShoesRequest.getExchangeShoesType());
        exchangeShoes.setPurchaseDate(exchangeShoesRequest.getPurchaseDate());
        exchangeShoes.setPrice(exchangeShoesRequest.getPrice());
        exchangeShoes.setDescription(exchangeShoesRequest.getDescription());
        exchangeShoes.setImg_url(exchangeShoesRequest.getImg_url());
        exchangeShoes.setCustomer(customer);
        exchangeShoes.setStatus(ExchangeShoes.STATUS.PENDING);
        exchangeShoes.setUsed(false);

        exchangeShoesRepository.save(exchangeShoes);
    }

    public ArrayList<ExchangeShoes> getAll() {
        return exchangeShoesRepository.findAll();
    }

    public ExchangeShoes getExchangeShoesById(int id) {
        return exchangeShoesRepository.getExchangeShoesById(id);
    }

    public void deleteExchangeShoes(int id) {
        exchangeShoesRepository.deleteById(id);
    }

    public void deleteCheckboxExchangeShoes(List<Integer> ids) {
        exchangeShoesRepository.deleteByIds(ids);
    }
}
