package com.example.shoesshop.controller;

import com.example.shoesshop.dto.ProductDTO;
import com.example.shoesshop.entity.Product;
import com.example.shoesshop.request.ProductRequest;
import com.example.shoesshop.service.ProductService;
import com.example.shoesshop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping(value = "api/v1/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping(value = "/all")
    public ResponseEntity<?> getAllProducts(Pageable pageable, @RequestParam String search){
        Page<Product> entitiesPage = productService.getAllProducts(pageable, search);
        Page<ProductDTO> dtoPage = entitiesPage.map(new Function<Product, ProductDTO>() {
            @Override
            public ProductDTO apply(Product product) {
                ProductDTO dto = new ProductDTO();

                dto.setId(product.getId());
                dto.setName(product.getName());
                dto.setDescription(product.getDescription());
                dto.setImage_url(product.getImage_url());
                dto.setPrice(product.getPrice());
                dto.setType_id(product.getTypeProduct().getId());
                dto.setType_name(product.getTypeProduct().getName());
                dto.setGender_for(product.getGender_type().toString());

                if(product.getSale() != null){
                    dto.setSale_id(product.getSale().getId());
                    dto.setSale_percent(product.getSale().getPercent_sale());
                }

                return dto;

            }
        });
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping(value = "/full")
    public ResponseEntity<?> getFullProducts(){
        List<Product> products = productService.getFullProducts();
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products){
            ProductDTO dto = new ProductDTO();

            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setImage_url(product.getImage_url());
            dto.setPrice(product.getPrice());
            dto.setType_id(product.getTypeProduct().getId());
            dto.setType_name(product.getTypeProduct().getName());
            dto.setGender_for(product.getGender_type().toString());

            if(product.getSale() != null){
                dto.setSale_id(product.getSale().getId());
                dto.setSale_percent(product.getSale().getPercent_sale());
            }
            productDTOS.add(dto);
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(name = "id") int id, @RequestBody ProductRequest productRequest){
        productService.updateProduct(id, productRequest);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable(name = "id") int id){
        Product product = productService.getProductById(id);
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setImage_url(product.getImage_url());
        productDTO.setPrice(product.getPrice());
        productDTO.setType_id(product.getTypeProduct().getId());
        productDTO.setType_name(product.getTypeProduct().getName());
        productDTO.setGender_for(product.getGender_type().toString());

        if(product.getSale() != null){
            productDTO.setSale_id(product.getSale().getId());
            productDTO.setSale_percent(product.getSale().getPercent_sale());
        }
            return new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(name = "id") int id){
        productService.deleteProduct(id);
        return new ResponseEntity<String>("Delete successfull!", HttpStatus.OK);
    }

    @DeleteMapping()
    public void deleteProducts(@RequestParam(name="ids") List<Integer> ids){
        productService.deleteProducts(ids);
    }
}
