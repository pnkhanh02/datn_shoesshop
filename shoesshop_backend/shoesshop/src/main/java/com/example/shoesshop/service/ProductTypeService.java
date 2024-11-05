package com.example.shoesshop.service;

import com.example.shoesshop.entity.ProductType;
import com.example.shoesshop.repository.ProductTypeRepository;
import com.example.shoesshop.request.ProductTypeRequest;
import com.example.shoesshop.specification.ProductTypeSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository productTypeRepository;

    //get All ProductType
    public Page<ProductType> getAllProductType(Pageable pageable, String search) {
        Specification<ProductType> where = null;
        if (!StringUtils.isEmpty(search)) {
            ProductTypeSpecification productTypeSpecification = new ProductTypeSpecification("name", "LIKE", search);
            where = Specification.where(productTypeSpecification);
        }
        return productTypeRepository.findAll(Specification.where(where), pageable);
    }

    //create ProductType
    public void createProductType(ProductTypeRequest productTypeRequest) {
        ProductType productType = new ProductType();
        productType.setName(productTypeRequest.getName());

        productTypeRepository.save(productType);
    }

    //update ProductType
    public void updateProductType(int id, ProductTypeRequest productTypeRequest){
        ProductType productType = productTypeRepository.getProductTypeById(id);
        productType.setName(productTypeRequest.getName());

        productTypeRepository.save(productType);
    }

    //get ProductType by Id
    public ProductType getProductTypeById(int id){
        return productTypeRepository.findById(id);
    }

    //delete ProductType
    public void deteleProductType(int id){
        productTypeRepository.deleteById(id);
    }

    //delete multiple ProductType
    public void deleteProductTypes(List<Integer> ids){
        productTypeRepository.deleteByIds(ids);
    }
}
