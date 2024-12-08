package com.example.shoesshop.repository;

import com.example.shoesshop.dto.TopRatingProductDTO;
import com.example.shoesshop.dto.TopSellingProductDTO;
import com.example.shoesshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    Product getProductById(int id);

    void deleteById(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Product WHERE id IN(:ids)")
    public void deleteByIds(@Param("ids") List<Integer> ids);


    @Query(value = "SELECT " +
            "p.id AS productId, " +
            "MAX(p.name) AS productName, " +
            "MAX(p.image_url) AS productUrl, " +
            "MAX(p.price) AS productPrice, " +
            "SUM(oi.quantity) AS totalQuantitySold " +
            "FROM Product p " +
            "JOIN ProductDetail pd ON p.id = pd.product_id " +
            "JOIN OrderItem oi ON pd.id_detail = oi.product_detailId " +
            "GROUP BY p.id " +
            "ORDER BY totalQuantitySold DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Object[]> findTopSellingProductsRaw();

    @Query(value = """
            SELECT 
                p.id AS productId,
                p.name AS productName,
                p.image_url AS imageUrl,
                p.price AS productPrice,
                AVG(CASE 
                        WHEN f.rating = 'VERY_BAD' THEN 1
                        WHEN f.rating = 'BAD' THEN 2
                        WHEN f.rating = 'AVERAGE' THEN 3
                        WHEN f.rating = 'GOOD' THEN 4
                        WHEN f.rating = 'EXCELLENT' THEN 5
                    END) AS avgRating,
                COUNT(f.feedbackID) AS totalFeedbacks
            FROM 
                Product p
            LEFT JOIN 
                Feedback f ON p.id = f.productId
            GROUP BY 
                p.id, p.name, p.image_url
            ORDER BY 
                avgRating DESC, totalFeedbacks DESC
            LIMIT 5
            """, nativeQuery = true)
    List<Object[]> findTopRatingProductsRaw();
}
