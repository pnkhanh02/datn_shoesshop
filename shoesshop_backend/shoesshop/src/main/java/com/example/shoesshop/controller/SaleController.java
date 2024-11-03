package com.example.shoesshop.controller;

import com.example.shoesshop.dto.SaleDTO;
import com.example.shoesshop.entity.Sale;
import com.example.shoesshop.request.SaleRequest;
import com.example.shoesshop.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping(value = "api/v1/sales")
@CrossOrigin(origins = "http://localhost:3000")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @GetMapping()
    public ResponseEntity<?> getAllSale(Pageable pageable, @RequestParam String search) {
        Page<Sale> entityPage = saleService.getAllSale(pageable, search);
        Page<SaleDTO> dtoPage = entityPage.map(new Function<Sale, SaleDTO>() {
            @Override
            public SaleDTO apply(Sale sale) {
                SaleDTO dto = new SaleDTO();
                dto.setId(sale.getId());
                dto.setSale_info(sale.getSale_info());
                dto.setPercent_sale(sale.getPercent_sale());
                dto.setStart_date(sale.getStart_date().toString());
                dto.setEnd_date(sale.getEnd_date().toString());

                return dto;
            }
        });

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createSale(@RequestBody SaleRequest saleRequest) throws ParseException {
        saleService.createSale(saleRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> updateSale(@PathVariable(name = "id") int id, @RequestBody SaleRequest saleRequest) throws ParseException {
        saleService.updateSale(id, saleRequest);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable(name = "id") int id) {
        Sale sale = saleService.getSaleById(id);

        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setId(sale.getId());
        saleDTO.setSale_info(sale.getSale_info());
        saleDTO.setPercent_sale(sale.getPercent_sale());
        saleDTO.setStart_date(sale.getStart_date().toString());
        saleDTO.setEnd_date(sale.getEnd_date().toString());

        return new ResponseEntity<SaleDTO>(saleDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deleteSale(@PathVariable(name = "id") int id) {
        saleService.deleteSale(id);
        return new ResponseEntity<String>("Delete successful!", HttpStatus.OK);
    }

    @DeleteMapping()
    public void deleteSales(@RequestParam(name = "ids") List<Integer> ids) {
        saleService.deleteSales(ids);
    }
}
