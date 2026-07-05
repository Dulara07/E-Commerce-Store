package com.freshcart.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Notification Data per the assignment spec: customerId, orderId, plus the
 * allowed extra fields (timestamp, message). No persistence - this is a
 * mock/logged notification only.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private Long customerId;
    private Long orderId;
    private String message;
    private LocalDateTime timestamp;
}
