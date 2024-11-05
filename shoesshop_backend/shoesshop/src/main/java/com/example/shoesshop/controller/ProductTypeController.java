package com.example.shoesshop.controller;

import com.example.shoesshop.dto.ProductTypeDTO;
import com.example.shoesshop.entity.ProductType;
import com.example.shoesshop.request.ProductTypeRequest;
import com.example.shoesshop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;


@RestController
@RequestMapping(value = "api/v1/productTypes")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductTypeController {
    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping(value = "all")
    public ResponseEntity<?> getAllProductType(Pageable pageable, @RequestParam String search) {
        Page<ProductType> entityPage = productTypeService.getAllProductType(pageable, search);
        Page<ProductTypeDTO> dtoPage = entityPage.map(new Function<ProductType, ProductTypeDTO>() {
            @Override
            public ProductTypeDTO apply(ProductType productType) {
                ProductTypeDTO productTypeDTO = new ProductTypeDTO();
                productTypeDTO.setId(productType.getId());
                productTypeDTO.setName(productType.getName());

                return productTypeDTO;
            }
        });

        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createProductType(@RequestBody ProductTypeRequest productTypeRequest) {
        productTypeService.createProductType(productTypeRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> updateProductType(@PathVariable(name = "id") int id, @RequestBody ProductTypeRequest productTypeRequest) {
        productTypeService.updateProductType(id, productTypeRequest);
        return new ResponseEntity<String>("Update successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProductTypeById(@PathVariable(name = "id") int id){
        ProductType productType= productTypeService.getProductTypeById(id);
        ProductTypeDTO productTypeDTO = new ProductTypeDTO();
        productTypeDTO.setId(productType.getId());
        productTypeDTO.setName(productType.getName());

        return new ResponseEntity<ProductTypeDTO>(productTypeDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deleteProductType(@PathVariable(name = "id") int id) {
        productTypeService.deteleProductType(id);
        return new ResponseEntity<String>("Delete successfull!", HttpStatus.OK);
    }

    @DeleteMapping()
    public void deleteProductTypes(@RequestParam(name = "ids") List<Integer> ids) {
        productTypeService.deleteProductTypes(ids);
    }
}
