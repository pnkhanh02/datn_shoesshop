package com.example.shoesshop.controller;

import com.example.shoesshop.entity.Account;
import com.example.shoesshop.jwt.JwtTokenStore;
import com.example.shoesshop.request.AccountRequest;
import com.example.shoesshop.request.LoginRequest;
import com.example.shoesshop.response.ResponseObject;
import com.example.shoesshop.security.PasswordEncoder;
import com.example.shoesshop.service.AccountService;
import com.example.shoesshop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    AccountService accountService;

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody AccountRequest account) throws ParseException {

        boolean checkExist =
                accountService.checkExistEmailOrUsername(account.getEmail(), account.getUsername());

        if(checkExist)
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(401, "null", "Email or Phone already exists"));

        accountService.createAccount(account);

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, null, "success"));

    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {

        Optional<Account> account = accountService.findByEmail((loginRequest.getEmail()));
        if (account.isEmpty() || !PasswordEncoder.getInstance().matches(loginRequest.getPassword(), account.get().getPassword())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(401, null, "Email or Password incorrect"));
        }

        String token = authService.loginWithEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        JwtTokenStore.getInstance().storeToken(loginRequest.getEmail(), token);
        return ResponseEntity.status(HttpStatus.OK).body
                (new ResponseObject(200,account,token));

    }


}