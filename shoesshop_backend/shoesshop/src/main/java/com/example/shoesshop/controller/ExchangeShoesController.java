package com.example.shoesshop.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/exchange-shoes")
@CrossOrigin(origins = "http://localhost:3000")
public class ExchangeShoesController {
}
