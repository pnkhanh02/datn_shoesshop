//package com.example.shoesshop.service;
//
//import com.example.shoesshop.entity.Account;
//import com.example.shoesshop.utils.JWTUtility;
//import com.example.shoesshop.repository.AccountRepository;
//import com.example.shoesshop.security.PasswordEncoder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//    private AccountRepository accountRepository;
//
//    @Autowired
//    public AuthService(AccountRepository accountRepository){
//        this.accountRepository = accountRepository;
//    }
//
//    public String loginWithEmailAndPassword(String email, String password){
//        Account account = accountRepository.findByEmail(email).orElse(null);
//        assert account != null;
//        if (PasswordEncoder.getInstance().matches(password, account.getPassword())) {
//            return JWTUtility.getInstance().generateTokenWithEmail(email);
//        }
//        return null;
//    }
//}
