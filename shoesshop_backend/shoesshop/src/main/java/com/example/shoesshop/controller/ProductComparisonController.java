package com.example.shoesshop.controller;

import com.example.shoesshop.dto.ProductComparisonDTO;
import com.example.shoesshop.request.ProductComparisonRequest;
import com.example.shoesshop.service.ProductComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/products/comparison")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductComparisonController {
    @Autowired
    private ProductComparisonService comparisonService;

    @PostMapping
    public ResponseEntity<List<ProductComparisonDTO>> compareProducts(
            @RequestBody ProductComparisonRequest request){
        List<ProductComparisonDTO> comparisonResults = comparisonService.compareProducts(request);
        return ResponseEntity.ok(comparisonResults);
    }
}
