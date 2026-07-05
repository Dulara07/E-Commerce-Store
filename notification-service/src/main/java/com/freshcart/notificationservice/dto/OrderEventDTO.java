package com.freshcart.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Mirrors com.freshcart.orderservice.dto.OrderEventDTO. Field names and types
 * must stay in sync with the producer since Jackson2JsonMessageConverter
 * deserializes by field name, not by shared class identity.
 */
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
