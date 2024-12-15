package com.example.shoesshop.service;

import com.example.shoesshop.entity.*;
import com.example.shoesshop.repository.ExchangeShoesRepository;
import com.example.shoesshop.request.ExchangeShoesRequest;
import com.example.shoesshop.request.ExchangeShoesUpdateRequest;
import com.example.shoesshop.request.ExchangeShoesUsedRequest;
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
import java.time.format.DateTimeFormatter;
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

    public Page<ExchangeShoes> findAllByCustomerId(int customerId, Pageable pageable){
        return exchangeShoesRepository.findAllByCustomerId(customerId, pageable);
    }

    public void createExchangeShoes(ExchangeShoesRequest exchangeShoesRequest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate purchaseDate = LocalDate.parse(exchangeShoesRequest.getPurchaseDate(), formatter);
        Customer customer = customerService.getCustomerById(exchangeShoesRequest.getCustomerId());
        ExchangeShoes exchangeShoes = new ExchangeShoes();

        exchangeShoes.setExchangeShoesName(exchangeShoesRequest.getExchangeShoesName());
        exchangeShoes.setExchangeShoesType(exchangeShoesRequest.getExchangeShoesType());
        exchangeShoes.setPurchaseDate(purchaseDate);
        exchangeShoes.setPrice(exchangeShoesRequest.getPrice());
        exchangeShoes.setDescription(exchangeShoesRequest.getDescription());
        exchangeShoes.setImg_url(exchangeShoesRequest.getImg_url());
        exchangeShoes.setCustomer(customer);
        exchangeShoes.setStatus(ExchangeShoes.STATUS.PENDING);
        exchangeShoes.setUsed(false);

        exchangeShoesRepository.save(exchangeShoes);
    }

    public void updateStatusExchangeShoes(int id, ExchangeShoesUpdateRequest exchangeShoesUpdateRequest){
        ExchangeShoes exchangeShoes = exchangeShoesRepository.getExchangeShoesById(id);

        if(exchangeShoes != null){
            exchangeShoes.setStatus(exchangeShoesUpdateRequest.getStatus());
            exchangeShoes.setExchangeShoesSales(exchangeShoesUpdateRequest.getStatus() == ExchangeShoes.STATUS.APPROVE
                    ? exchangeShoesUpdateRequest.getExchangeShoesSales()
                    : 0);
            exchangeShoesRepository.save(exchangeShoes);
        }
    }

    public void updateUsedExchangeShoes(int id, ExchangeShoesUsedRequest exchangeShoesUsedRequest){
        ExchangeShoes exchangeShoes = exchangeShoesRepository.getExchangeShoesById(id);

        if(exchangeShoes != null){
            exchangeShoes.setUsed(exchangeShoesUsedRequest.getUsed());
            exchangeShoesRepository.save(exchangeShoes);
        }
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
