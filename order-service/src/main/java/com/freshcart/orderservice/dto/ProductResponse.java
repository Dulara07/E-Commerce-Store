package com.freshcart.orderservice.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductResponse {
    private Long productId;
    private String name;
    private BigDecimal unitPrice;
    private Integer stockQuantity;
}