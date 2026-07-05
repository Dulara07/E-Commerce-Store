package com.freshcart.notificationservice.service;

import com.freshcart.notificationservice.dto.Notification;
import com.freshcart.notificationservice.dto.OrderEventDTO;
import com.freshcart.notificationservice.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationServiceImplTest {

    private final NotificationServiceImpl notificationService = new NotificationServiceImpl();

    @Test
    void processOrderNotification_mapsEventFieldsOntoNotification() {
        LocalDateTime now = LocalDateTime.now();
        OrderEventDTO event = OrderEventDTO.builder()
                .customerId(101L)
                .orderId(5001L)
                .message("Order placed successfully for product: Rice 5kg")
                .timestamp(now)
                .build();

        Notification notification = notificationService.processOrderNotification(event);

        assertThat(notification.getCustomerId()).isEqualTo(101L);
        assertThat(notification.getOrderId()).isEqualTo(5001L);
        assertThat(notification.getMessage()).isEqualTo("Order placed successfully for product: Rice 5kg");
        assertThat(notification.getTimestamp()).isEqualTo(now);
    }
}
