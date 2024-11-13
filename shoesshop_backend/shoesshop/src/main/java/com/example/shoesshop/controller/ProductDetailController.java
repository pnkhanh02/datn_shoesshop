package com.example.shoesshop.controller;

import com.example.shoesshop.dto.ProductDetailDTO;
import com.example.shoesshop.entity.PaymentMethod;
import com.example.shoesshop.entity.ProductDetail;
import com.example.shoesshop.request.ProductDetailRequest;
import com.example.shoesshop.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping(value = "api/v1/productDetails")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductDetailController {
    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping()
    public ResponseEntity<?> getAllProductDetails(Pageable pageable, @RequestParam String search){
        Page<ProductDetail> entitiesPage = productDetailService.getAllProductDetails(pageable, search);
        Page<ProductDetailDTO> dtoPage = entitiesPage.map(new Function<ProductDetail, ProductDetailDTO>() {
            @Override
            public ProductDetailDTO apply(ProductDetail productDetail) {
                ProductDetailDTO dto = new ProductDetailDTO();

                dto.setId(productDetail.getId());
                dto.setQuantity(productDetail.getQuantity());
                dto.setImg_url(productDetail.getImg_url());
                dto.setColor(productDetail.getColor());
                dto.setSize(productDetail.getSize());
                dto.setProduct_id(productDetail.getProduct_detail().getId());

                return dto;
            }
        });
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createProductDetail(@RequestBody ProductDetailRequest productDetailRequest){
        productDetailService.createProductDetail(productDetailRequest);
        return new ResponseEntity<String>("Create successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "update/{id}")
    public ResponseEntity<?> updateProductDetail(@PathVariable(name = "id") int id, @RequestBody ProductDetailRequest productDetailRequest){
        productDetailService.updateProductDetail(id, productDetailRequest);
        return new ResponseEntity<String>("Update successfull!", HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getProductDetailById(@PathVariable(name = "id") int id){
        ProductDetail productDetail = productDetailService.getProductDetailById(id);
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();

        productDetailDTO.setId(productDetail.getId());
        productDetailDTO.setQuantity(productDetail.getQuantity());
        productDetailDTO.setImg_url(productDetail.getImg_url());
        productDetailDTO.setColor(productDetail.getColor());
        productDetailDTO.setSize(productDetail.getSize());
        productDetailDTO.setProduct_id(productDetail.getProduct_detail().getId());

        return new ResponseEntity<ProductDetailDTO>(productDetailDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<?> deleteProductDeatil(@PathVariable(name = "id") int id){
        ProductDetail productDetail = productDetailService.getProductDetailById(id);
        if(productDetail == null){
            return new ResponseEntity<String>("No value found", HttpStatus.BAD_REQUEST);
        }
        productDetailService.deleteProductDetail(id);
        return new ResponseEntity<String>("Delete successfull!", HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteProductDetails(@RequestParam(name="ids") List<Integer> ids){
        for(int id : ids){
            ProductDetail productDetail = productDetailService.getProductDetailById(id);
            if(productDetail == null){
                return new ResponseEntity<String>("No value found", HttpStatus.BAD_REQUEST);
            }
        }
        productDetailService.deleteProductDetails(ids);
        return new ResponseEntity<String>(" Delete ProductDetails successful", HttpStatus.OK);
    }
}
