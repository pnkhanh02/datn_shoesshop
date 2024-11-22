package com.example.shoesshop.request;

import java.util.List;

public class ProductComparisonRequest {
    private List<Integer> productIds;

    public List<Integer> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Integer> productIds) {
        this.productIds = productIds;
    }
}
