package com.example.shoesshop.service;

import com.example.shoesshop.entity.Account;
import com.example.shoesshop.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Optional<Account> findByEmail(String email){
        return accountRepository.findByEmail(email);
    }
}
