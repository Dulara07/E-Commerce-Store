package com.freshcart.product_service.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long productId;

    private String name;

    private String description;

    private String category;

    private BigDecimal unitPrice;

    private Integer stockQuantity;
}
