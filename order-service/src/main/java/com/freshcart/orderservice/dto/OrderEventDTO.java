package com.freshcart.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDTO {
    private Long customerId;
    private Long orderId;
    private String message;
    private LocalDateTime timestamp;
}