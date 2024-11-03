package com.example.shoesshop.controller;

import com.example.shoesshop.entity.Account;
import com.example.shoesshop.jwt.JWTUtility;
import com.example.shoesshop.jwt.JwtInterceptor;
import com.example.shoesshop.response.ResponseObject;
import com.example.shoesshop.service.AccountService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin(origins = "http://localhost:3000")
public class AccountController {
    @Autowired
    AccountService accountService;
    @GetMapping("/findToken")
    public ResponseEntity<?> findByTk(@RequestParam String token) {
        if(token.isBlank()){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(201, null,"token is blank"));
        }
        token = "Bearer " + token;
        boolean isAuthenticated = JwtInterceptor.getInstance().isValidToken(token);
        if(isAuthenticated){
            Claims claims = JWTUtility.getInstance().parseToken(token);
            String email = claims.getSubject();
            if (email != null) {
                Optional<Account> account = accountService.findByEmail(email);
                if (account.isPresent()) {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, account,"OK"));
                }else {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(201, null,"user is not exist"));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(201, null,"user is not exist"));
    }
}
