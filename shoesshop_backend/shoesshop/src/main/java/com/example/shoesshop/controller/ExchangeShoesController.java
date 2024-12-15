package com.example.shoesshop.controller;

import com.example.shoesshop.dto.ExchangeShoesDTO;
import com.example.shoesshop.entity.ExchangeShoes;
import com.example.shoesshop.request.ExchangeShoesRequest;
import com.example.shoesshop.request.ExchangeShoesUpdateRequest;
import com.example.shoesshop.request.ExchangeShoesUsedRequest;
import com.example.shoesshop.service.ExchangeShoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1/exchange-shoes")
@CrossOrigin(origins = "http://localhost:3000")
public class ExchangeShoesController {
    @Autowired
    private ExchangeShoesService exchangeShoesService;

    @GetMapping(value = "/getAllExchangeShoes")
    public ResponseEntity<?> getAllExchangeShoes(Pageable pageable, @RequestParam(required = false) String search) {
        Page<ExchangeShoes> exchangeShoesPage = exchangeShoesService.getAllExchangeShoes(pageable, search);
        Page<ExchangeShoesDTO> exchangeShoesDTOPage = exchangeShoesPage.map(exchangeShoes ->
                new ExchangeShoesDTO(
                        exchangeShoes.getId(),
                        exchangeShoes.getExchangeShoesName(),
                        exchangeShoes.getExchangeShoesType(),
                        exchangeShoes.getPurchaseDate().toString(),
                        exchangeShoes.getPrice(),
                        exchangeShoes.getDescription(),
                        exchangeShoes.getImg_url(),
                        exchangeShoes.getStatus(),
                        exchangeShoes.isUsed(),
                        exchangeShoes.getCustomer().getId(),
                        exchangeShoes.getExchangeShoesSales()
                )
        );
        return new ResponseEntity<>(exchangeShoesDTOPage, HttpStatus.OK);
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAll() {
        ArrayList<ExchangeShoes> exchangeShoesList = exchangeShoesService.getAll();
        List<ExchangeShoesDTO> exchangeShoesDTOList = exchangeShoesList.stream()
                .map(exchangeShoes -> new ExchangeShoesDTO(
                                exchangeShoes.getId(),
                                exchangeShoes.getExchangeShoesName(),
                                exchangeShoes.getExchangeShoesType(),
                                exchangeShoes.getPurchaseDate().toString(),
                                exchangeShoes.getPrice(),
                                exchangeShoes.getDescription(),
                                exchangeShoes.getImg_url(),
                                exchangeShoes.getStatus(),
                                exchangeShoes.isUsed(),
                                exchangeShoes.getCustomer().getId(),
                                exchangeShoes.getExchangeShoesSales()
                        )
                ).collect(Collectors.toList());
        return new ResponseEntity<>(exchangeShoesDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createExchangeShoes(@RequestBody ExchangeShoesRequest exchangeShoesRequest) {
        exchangeShoesService.createExchangeShoes(exchangeShoesRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateStatusExchangeShoes(@PathVariable(name = "id") int id, @RequestBody ExchangeShoesUpdateRequest exchangeShoesUpdateRequest) {
        exchangeShoesService.updateStatusExchangeShoes(id, exchangeShoesUpdateRequest);
        return new ResponseEntity<String>("Update successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "updateUsed/{id}")
    public  ResponseEntity<?> updateUsedExchangeShoes(@PathVariable(name = "id") int id, @RequestBody ExchangeShoesUsedRequest exchangeShoesUsedRequest){
        exchangeShoesService.updateUsedExchangeShoes(id, exchangeShoesUsedRequest);
        return new ResponseEntity<String>("Update successfully", HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAllByCustomerId/{customerId}")
    public ResponseEntity<?> findAllByCustomerId(@PathVariable(name = "customerId") int customerId, Pageable pageable) {
        Page<ExchangeShoes> exchangeShoesPage = exchangeShoesService.findAllByCustomerId(customerId, pageable);
        Page<ExchangeShoesDTO> exchangeShoesDTOPage = exchangeShoesPage.map(exchangeShoes ->
                new ExchangeShoesDTO(
                        exchangeShoes.getId(),
                        exchangeShoes.getExchangeShoesName(),
                        exchangeShoes.getExchangeShoesType(),
                        exchangeShoes.getPurchaseDate().toString(),
                        exchangeShoes.getPrice(),
                        exchangeShoes.getDescription(),
                        exchangeShoes.getImg_url(),
                        exchangeShoes.getStatus(),
                        exchangeShoes.isUsed(),
                        exchangeShoes.getCustomer().getId(),
                        exchangeShoes.getExchangeShoesSales()
                )
        );
        return new ResponseEntity<>(exchangeShoesDTOPage, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getExchangeShoesById(@PathVariable(name = "id") int id) {
        ExchangeShoes exchangeShoes = exchangeShoesService.getExchangeShoesById(id);
        ExchangeShoesDTO exchangeShoesDTO = new ExchangeShoesDTO(
                exchangeShoes.getId(),
                exchangeShoes.getExchangeShoesName(),
                exchangeShoes.getExchangeShoesType(),
                exchangeShoes.getPurchaseDate().toString(),
                exchangeShoes.getPrice(),
                exchangeShoes.getDescription(),
                exchangeShoes.getImg_url(),
                exchangeShoes.getStatus(),
                exchangeShoes.isUsed(),
                exchangeShoes.getCustomer().getId(),
                exchangeShoes.getExchangeShoesSales()
        );
        return new ResponseEntity<ExchangeShoesDTO>(exchangeShoesDTO, HttpStatus.OK);
    }

    @DeleteMapping()
    public void deleteCheckboxExchangeShoes(@RequestParam(name = "ids") List<Integer> ids) {
        exchangeShoesService.deleteCheckboxExchangeShoes(ids);
    }
}
