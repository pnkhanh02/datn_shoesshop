package com.example.shoesshop.service;

import com.example.shoesshop.entity.Product;
import com.example.shoesshop.entity.ProductDetail;
import com.example.shoesshop.repository.ProductDetailRepository;
import com.example.shoesshop.repository.ProductRepository;
import com.example.shoesshop.request.ProductDetailRequest;
import com.example.shoesshop.request.ProductRequest;
import com.example.shoesshop.specification.ProductDetailSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ProductDetailService {
    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    public Page<ProductDetail> getAllProductDetails(Pageable pageable, String search) {
        Specification<ProductDetail> where = null;
        if(!StringUtils.isEmpty(search)){
            ProductDetailSpecification searchSpecification = new ProductDetailSpecification("name","LIKE", search);
            where = Specification.where(searchSpecification);
        }
        return productDetailRepository.findAll(Specification.where(where), pageable);
    }

    public void createProductDetail(ProductDetailRequest productDetailRequest) {
        Product product = productRepository.getProductById(productDetailRequest.getProduct_id());
        ProductDetail productDetail = new ProductDetail();
        productDetail.setQuantity(productDetailRequest.getQuantity());
        productDetail.setImg_url(productDetailRequest.getImg_url());
        productDetail.setColor(productDetailRequest.getColor());
        productDetail.setSize(productDetailRequest.getSize());
        productDetail.setProduct_detail(product);

        productDetailRepository.save(productDetail);
    }

    public void updateProductDetail(int id, ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = productDetailRepository.getDetailById(id);
        productDetail.setQuantity(productDetailRequest.getQuantity());
        productDetail.setImg_url(productDetailRequest.getImg_url());
        Product product = productDetail.getProduct_detail();
        List<ProductDetail> productDetails = product.getProductDetails();
        for(ProductDetail productDetail1 : productDetails){
            if(productDetail1.getColor().equals(productDetailRequest.getColor()) ){
                productDetail1.setImg_url(productDetailRequest.getImg_url());
                productDetailRepository.save(productDetail1);
            }

        }
        productDetail.setColor(productDetailRequest.getColor());
        productDetail.setSize(productDetailRequest.getSize());
        productDetailRepository.save(productDetail);
    }

    public ProductDetail getProductDetailById(int id) {
        return productDetailRepository.getDetailById(id);
    }

    public void deleteProductDetail(int id) {
        productDetailRepository.deleteById(id);
    }

    public void deleteProductDetails(List<Integer> ids) {
        productDetailRepository.deleteByIds(ids);
    }
}
