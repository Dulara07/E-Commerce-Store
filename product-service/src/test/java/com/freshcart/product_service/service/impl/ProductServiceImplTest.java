package com.freshcart.product_service.service.impl;

import com.freshcart.product_service.dto.ProductRequest;
import com.freshcart.product_service.dto.ProductResponse;
import com.freshcart.product_service.entity.Product;
import com.freshcart.product_service.exception.ProductNotFoundException;
import com.freshcart.product_service.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    void createProduct_savesAndReturnsMappedResponse() {
        ProductRequest request = ProductRequest.builder()
                .name("Organic Apples")
                .description("Fresh organic apples")
                .category("Fruits")
                .unitPrice(BigDecimal.valueOf(4.99))
                .stockQuantity(50)
                .build();

        Product savedProduct = Product.builder()
                .productId(1L)
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .unitPrice(request.getUnitPrice())
                .stockQuantity(request.getStockQuantity())
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        ProductResponse response = productService.createProduct(request);

        assertThat(response.getProductId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Organic Apples");
        assertThat(response.getUnitPrice()).isEqualByComparingTo("4.99");
        assertThat(response.getStockQuantity()).isEqualTo(50);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(productCaptor.capture());
        assertThat(productCaptor.getValue().getName()).isEqualTo("Organic Apples");
    }

    @Test
    void getProductById_returnsMappedResponse_whenProductExists() {
        Product product = Product.builder()
                .productId(1L)
                .name("Organic Apples")
                .unitPrice(BigDecimal.valueOf(4.99))
                .stockQuantity(50)
                .build();

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertThat(response.getProductId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Organic Apples");
    }

    @Test
    void getProductById_throwsProductNotFoundException_whenProductMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deleteProduct_deletesProduct_whenProductExists() {
        Product product = Product.builder().productId(1L).build();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_throwsProductNotFoundException_whenProductMissing() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deleteProduct(99L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository, never()).delete(any());
    }
}
