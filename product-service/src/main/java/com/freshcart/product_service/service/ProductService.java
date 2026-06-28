package com.freshcart.product_service.service;

import com.freshcart.product_service.dto.ProductRequest;
import com.freshcart.product_service.dto.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long productId);

    void deleteProduct(Long productId);
}