package com.example.shoesshop.controller;

import com.example.shoesshop.dto.LoginDTO;
import com.example.shoesshop.entity.Account;
import com.example.shoesshop.exception.CustomException;
import com.example.shoesshop.exception.ErrorResponseEnum;
import com.example.shoesshop.repository.AccountRepository;
import com.example.shoesshop.request.AccountRequest;
import com.example.shoesshop.request.LoginRequest;
import com.example.shoesshop.response.ResponseObject;
import com.example.shoesshop.security.PasswordEncoder;
import com.example.shoesshop.service.AccountService;
import com.example.shoesshop.utils.JWTTokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JWTTokenUtils jwtTokenUtils;

//    @PostMapping("/signup")
//    public ResponseEntity<?> signUp(@RequestBody AccountRequest account) throws ParseException {
//
//        boolean checkExist =
//                accountService.checkExistEmailOrUsername(account.getEmail(), account.getUsername());
//
//        if (checkExist)
//            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(401, "null", "Email or Phone already exists"));
//
//        accountService.createAccount(account);
//
//        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(200, null, "success"));
//
//    }

    @PostMapping("/login-jwt") //Thực hiện theo cách này thì cần mở public API này ra
    public LoginDTO loginJwt(@RequestBody LoginRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername());
        if (account == null) {
            throw new CustomException(ErrorResponseEnum.USERNAME_NOT_FOUND);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        //So sánh password
        boolean checkPassword = passwordEncoder.matches(request.getPassword(), account.getPassword());
        if (!checkPassword) {
            throw new CustomException(ErrorResponseEnum.PASSWORD_FAILS);
        }
        LoginDTO loginDto = new LoginDTO();
        BeanUtils.copyProperties(account, loginDto);

        //Tạo ra token để return
        String token = jwtTokenUtils.createAccessToken(loginDto);
        loginDto.setToken(token);
        return loginDto;
    }

}
