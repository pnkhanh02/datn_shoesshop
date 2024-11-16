package com.example.shoesshop.controller;

import com.example.shoesshop.dto.ProductDTO;
import com.example.shoesshop.dto.ProductDetailDTO;
import com.example.shoesshop.entity.Product;
import com.example.shoesshop.entity.ProductDetail;
import com.example.shoesshop.entity.ProductType;
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

    @GetMapping(value = "/type/{id}")
    public ResponseEntity<?> getProductByType(@PathVariable(name = "id") int id){
        ProductType productType = productTypeService.getProductTypeById(id);
        List<Product> products = productType.getProducts();
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductDTO dto = new ProductDTO();
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setDescription(product.getDescription());
            dto.setImage_url(product.getImage_url());
            dto.setPrice(product.getPrice());
            dto.setType_id(product.getTypeProduct().getId());
            dto.setType_name(product.getTypeProduct().getName());
            dto.setGender_for(product.getGender_type().toString());

            if (product.getSale() != null) {
                dto.setSale_id(product.getSale().getId());
                dto.setSale_percent(product.getSale().getPercent_sale());
            }
            productDTOS.add(dto);
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/allsize")
    public List<String> getAllSize(){
        List<String> sizes = new ArrayList<>();
        List<Product> products = productService.getFullProducts();
        for(Product product: products){
            List<ProductDetail> productDetails = product.getProductDetails();
            for(ProductDetail productDetail : productDetails){
                sizes.add(productDetail.getSize());
            }
        }
        List<String> uniqueList = new ArrayList<>();
        for (String element : sizes) {
            if (!uniqueList.contains(element)) {
                uniqueList.add(element);
            }
        }
        return uniqueList;
    }

    @GetMapping(value = "/filter")
    public ResponseEntity<?> filterProduct(@RequestParam(required = false) String size,
                                           @RequestParam(required = false) String color,
                                           @RequestParam(required = false) Integer type_id){
        if (type_id == null) {
            type_id = 0;
        }
        List<Product> products = new ArrayList<>();
        boolean check= false;
        if(size != null && color != null && type_id != 0){
            ProductType productType = productTypeService.getProductTypeById(type_id);
            List<Product> products1s = productType.getProducts();
            for(Product product : products1s){
                List<ProductDetail> productDetails = product.getProductDetails();
                for(ProductDetail productDetail : productDetails){
                    if(productDetail.getColor().equals(color) && productDetail.getSize().equals(size)){
                        check = true;
                        break;
                    }
                }
                if (check){
                    products.add(product);
                    check = false;
                }
            }
        }else if(size == null){
            if(color != null && type_id!= 0){
                ProductType productType = productTypeService.getProductTypeById(type_id);
                List<Product> products1s =productType.getProducts();
                for(Product product : products1s){
                    List<ProductDetail> productDetails = product.getProductDetails();
                    for(ProductDetail productDetail : productDetails){
                        if(productDetail.getColor().equals(color)){
                            check = true;
                            break;
                        }
                    }
                    if (check){
                        products.add(product);
                        check = false;
                    }
                }

            }else if(color == null && type_id!= 0){
                ProductType productType = productTypeService.getProductTypeById(type_id);
                products = productType.getProducts();
            }else if(color != null && type_id == 0){
                List<Product> products1 = productService.getFullProducts();
                for(Product product : products1){
                    List<ProductDetail> productDetails = product.getProductDetails();
                    for(ProductDetail productDetail : productDetails){
                        if(productDetail.getColor().equals(color)){
                            check = true;
                            break;
                        }
                    }
                    if (check){
                        products.add(product);
                        check = false;
                    }
                }
            }
        }else if (color == null){
            if(type_id == 0){
                List<Product> products1 = productService.getFullProducts();
                for(Product product : products1){
                    List<ProductDetail> productDetails = product.getProductDetails();
                    for(ProductDetail productDetail : productDetails){
                        if(productDetail.getSize().equals(size)){
                            check = true;
                            break;
                        }
                    }
                    if (check){
                        products.add(product);
                        check = false;
                    }
                }
            }else if(type_id !=0){
                ProductType productType = productTypeService.getProductTypeById(type_id);
                products =productType.getProducts();
            }
        }else if (type_id == 0){
            List<Product> products1 = productService.getFullProducts();
            for(Product product : products1){
                List<ProductDetail> productDetails = product.getProductDetails();
                for(ProductDetail productDetail : productDetails){
                    if(productDetail.getSize().equals(size) && productDetail.getColor().equals(color)){
                        check = true;
                        break;
                    }
                }
                if (check){
                    products.add(product);
                    check = false;
                }
            }
        }else{
            ProductType productType = productTypeService.getProductTypeById(type_id);
            products = productType.getProducts();
        }
        if(size == null && color == null && type_id== 0){
            products = productService.getFullProducts();
        }
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product : products){
            ProductDTO dto = new ProductDTO(product.getId(), product.getName(), product.getDescription(), product.getImage_url(),product.getPrice(),product.getTypeProduct().getName(),product.getTypeProduct().getId(), product.getGender_type().toString());
            productDTOS.add(dto);
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/allcolor")
    public List<String> getAllcolor(){
        List<String> colors = new ArrayList<>();
        List<Product> products = productService.getFullProducts();
        for(Product product: products){
            List<ProductDetail> productDetails = product.getProductDetails();
            for(ProductDetail productDetail : productDetails){
                colors.add(productDetail.getColor());
            }
        }
        List<String> uniqueList = new ArrayList<>();
        for (String element : colors) {
            if (!uniqueList.contains(element)) {
                uniqueList.add(element);
            }
        }
        return uniqueList;
    }

    @GetMapping(value = "/productDetail/{id}")
    public ResponseEntity<?> getProductDetail(@PathVariable(name = "id") int id){
        Product product = productService.getProductById(id);
        List<ProductDetail> productDetails = product.getProductDetails();
        ArrayList<ProductDetailDTO> productDetailDTOArrayList = new ArrayList<>();
        for(ProductDetail productDetail : productDetails){
            ProductDetailDTO dto = new ProductDetailDTO();
            dto.setId(productDetail.getId());
            dto.setQuantity(productDetail.getQuantity());
            dto.setImg_url(productDetail.getImg_url());
            dto.setColor(productDetail.getColor());
            dto.setSize(productDetail.getSize());
            dto.setProduct_id(productDetail.getProduct_detail().getId());

            productDetailDTOArrayList.add(dto);
        }
        return new ResponseEntity<>(productDetailDTOArrayList, HttpStatus.OK);
    }
}
