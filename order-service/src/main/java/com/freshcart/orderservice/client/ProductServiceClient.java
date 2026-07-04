package com.freshcart.orderservice.client;

import com.freshcart.orderservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductServiceClient {

    @GetMapping("/products/{id}")
    ProductResponse getProductById(@PathVariable("id") Long productId);
}