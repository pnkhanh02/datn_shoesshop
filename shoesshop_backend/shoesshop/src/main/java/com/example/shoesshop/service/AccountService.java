package com.example.shoesshop.service;

import com.example.shoesshop.entity.Account;
import com.example.shoesshop.entity.Customer;
import com.example.shoesshop.repository.AccountRepository;
import com.example.shoesshop.request.AccountRequest;
import com.example.shoesshop.security.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Optional<Account> findByEmail(String email){
        return accountRepository.findByEmail(email);
    }

    // check exist of email or username
    public boolean checkExistEmailOrUsername(String email, String username){
        Optional<Account> optionalEmail = accountRepository.findByEmail(email);
        Optional<Account> optionalUserName = accountRepository.findByUsername(username);

        if(optionalEmail.isPresent()) return true;
        if(optionalUserName.isPresent()) return true;

        return false;
    }

    // create new account
    public void createAccount(AccountRequest accountRequest) throws ParseException {
        LocalDate createDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthday =  LocalDate.parse(accountRequest.getBirthday(), formatter);
        String password = PasswordEncoder.getInstance().encodePassword(accountRequest.getPassword());

        Customer customer = new Customer();
        customer.setUsername(accountRequest.getUsername());
        customer.setPassword(password);
        customer.setFirstName(accountRequest.getFirstName());
        customer.setLastName(accountRequest.getLastName());
        customer.setAddress(accountRequest.getAddress());
        customer.setBirthday(birthday);
        customer.setEmail(accountRequest.getEmail());
        customer.setPhone(accountRequest.getPhone());
        customer.setRole(accountRequest.getRole() != null ? accountRequest.getRole() : Account.Role.CUSTOMER);
        customer.setGender(accountRequest.getGender());
        customer.setCreatedDate(createDate);

        accountRepository.save(customer);
    }
}
