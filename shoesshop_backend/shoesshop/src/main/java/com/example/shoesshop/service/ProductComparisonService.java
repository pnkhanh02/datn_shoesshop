package com.example.shoesshop.service;

import com.example.shoesshop.dto.ProductComparisonDTO;
import com.example.shoesshop.entity.Feedback;
import com.example.shoesshop.entity.Product;
import com.example.shoesshop.entity.ProductDetail;
import com.example.shoesshop.repository.FeedbackRepository;
import com.example.shoesshop.repository.ProductRepository;
import com.example.shoesshop.request.ProductComparisonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductComparisonService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    public List<ProductComparisonDTO> compareProducts(ProductComparisonRequest request) {
        List<Product> products = productRepository.findAllById(request.getProductIds());

        List<ProductComparisonDTO> comparisonResults = new ArrayList<>();

        for (Product product : products) {
            ProductComparisonDTO response = new ProductComparisonDTO();
            response.setProductId(product.getId());
            response.setName(product.getName());
            response.setPrice(product.getPrice());
            response.setImageUrl(product.getImage_url());

            // Calculate unique sizes
            Set<String> uniqueSizes = product.getProductDetails().stream()
                    .map(ProductDetail::getSize)
                    .collect(Collectors.toSet());
            response.setUniqueSizes(uniqueSizes.size());

            // Assuming there's a color property in `ProductDetail`
            Set<String> uniqueColors = product.getProductDetails().stream()
                    .map(ProductDetail::getColor) // Replace `getColor` with actual color field
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            response.setUniqueColors(uniqueColors.size());

            // Calculate average rating
//            List<Feedback> feedbacks = feedbackRepository.findByProductFeedback(product);
//            if (!feedbacks.isEmpty()) {
//                double averageRating = feedbacks.stream()
//                        .mapToInt(feedback -> feedback.getRating().ordinal() + 1) // Convert enum to numeric
//                        .average()
//                        .orElse(0.0);
//                response.setAverageRating((float) averageRating);
//            } else {
//                response.setAverageRating(0.0f);
//            }

            comparisonResults.add(response);
        }

        return comparisonResults;
    }
}
