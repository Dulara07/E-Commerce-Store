package com.freshcart.product_service.service.impl;

import com.freshcart.product_service.dto.ProductRequest;
import com.freshcart.product_service.dto.ProductResponse;
import com.freshcart.product_service.entity.Product;
import com.freshcart.product_service.exception.ProductNotFoundException;
import com.freshcart.product_service.repository.ProductRepository;
import com.freshcart.product_service.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        Product product = toEntity(request);

        Product savedProduct = productRepository.save(product);

        return toResponse(savedProduct);
    }

    @Override
    public ProductResponse getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return toResponse(product);
    }

    @Override
    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        productRepository.delete(product);
    }

    // ==========================
    // Private Mapping Methods
    // ==========================

    private Product toEntity(ProductRequest request) {

        return Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .unitPrice(request.getUnitPrice())
                .stockQuantity(request.getStockQuantity())
                .build();
    }

    private ProductResponse toResponse(Product product) {

        return ProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .category(product.getCategory())
                .unitPrice(product.getUnitPrice())
                .stockQuantity(product.getStockQuantity())
                .build();
    }
}
