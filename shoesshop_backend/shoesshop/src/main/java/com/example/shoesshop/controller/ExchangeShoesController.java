package com.example.shoesshop.controller;

import com.example.shoesshop.request.ExchangeShoesRequest;
import com.example.shoesshop.request.FeedbackRequest;
import com.example.shoesshop.service.ExchangeShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/exchange-shoes")
@CrossOrigin(origins = "http://localhost:3000")
public class ExchangeShoesController {
    @Autowired
    private ExchangeShoesService exchangeShoesService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> createExchangeShoes(@RequestBody ExchangeShoesRequest exchangeShoesRequest){
        exchangeShoesService.createExchangeShoes(exchangeShoesRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }
}
