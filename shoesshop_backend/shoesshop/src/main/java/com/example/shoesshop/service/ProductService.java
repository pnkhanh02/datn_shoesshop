package com.example.shoesshop.service;

import com.example.shoesshop.dto.TopRatingProductDTO;
import com.example.shoesshop.dto.TopSellingProductDTO;
import com.example.shoesshop.entity.Product;
import com.example.shoesshop.entity.ProductType;
import com.example.shoesshop.entity.Sale;
import com.example.shoesshop.repository.ProductRepository;
import com.example.shoesshop.repository.ProductTypeRepository;
import com.example.shoesshop.repository.SaleRepository;
import com.example.shoesshop.request.ProductRequest;
import com.example.shoesshop.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;

    @Autowired
    private SaleRepository saleRepository;

    //get all product
    public Page<Product> getAllProducts(Pageable pageable, String search) {
        Specification<Product> where = null;
        if (!StringUtils.isEmpty(search)) {
            ProductSpecification searchSpecification = new ProductSpecification("name", "LIKE", search);
            where = Specification.where(searchSpecification);
        }
        return productRepository.findAll(Specification.where(where), pageable);
    }

    //get full product
    public List<Product> getFullProducts() {
        return productRepository.findAll();
    }

    //create product
    public void createProduct(ProductRequest productRequest) {
        ProductType productType = productTypeRepository.findById(productRequest.getType_id());
        Sale sale = null;
        if (0 != productRequest.getSale_id()) {
            sale = saleRepository.findById(productRequest.getSale_id());
        }
        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setImage_url(productRequest.getImage_url());
        product.setPrice(productRequest.getPrice());
        product.setSale(sale);
        product.setTypeProduct(productType);
        product.setGender_type(productRequest.getGenderType());

        productRepository.save(product);
    }

    //update product
    public void updateProduct(int id, ProductRequest productRequest) {
        ProductType productType = productTypeRepository.findById(productRequest.getType_id());
        Product product = productRepository.getProductById(id);
        Sale sale = saleRepository.getSaleById(productRequest.getSale_id());
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setImage_url(productRequest.getImage_url());
        product.setPrice(productRequest.getPrice());
        product.setTypeProduct(productType);
        product.setGender_type(productRequest.getGenderType());
        product.setSale(sale);

        productRepository.save(product);
    }

    //get product by id
    public Product getProductById(int id) {
        return productRepository.getProductById(id);
    }

    //delete product
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    //delete products
    public void deleteProducts(List<Integer> ids) {
        productRepository.deleteByIds(ids);
    }

    //top selling
    public List<TopSellingProductDTO> findTopSellingProducts() {
        List<Object[]> rawResults = productRepository.findTopSellingProductsRaw();
        return rawResults.stream().map(result -> new TopSellingProductDTO(
                ((Number) result[0]).intValue(),      // productId
                (String) result[1],                   // productName
                (String) result[2],                   // productUrl
                ((Number) result[3]).floatValue(),    // productPrice
                ((Number) result[4]).intValue()    // totalQuantitySold
        )).collect(Collectors.toList());
    }

    //top selling
    public List<TopRatingProductDTO> findTopRatingProducts() {
        List<Object[]> rawResults = productRepository.findTopRatingProductsRaw();
        return rawResults.stream().map(result -> new TopRatingProductDTO(
                ((Number) result[0]).intValue(),      // productId
                (String) result[1],                   // productName
                (String) result[2],                   // productUrl
                ((Number) result[3]).floatValue(),    // productPrice
                ((Number) result[4]).floatValue()     //avgRating
        )).collect(Collectors.toList());
    }
}
