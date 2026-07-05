package com.freshcart.notificationservice.listener;

import com.freshcart.notificationservice.dto.OrderEventDTO;
import com.freshcart.notificationservice.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderEventListenerTest {

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private OrderEventListener orderEventListener;

    private OrderEventDTO event;

    @BeforeEach
    void setUp() {
        event = OrderEventDTO.builder()
                .customerId(101L)
                .orderId(5001L)
                .message("Order placed successfully for product: Rice 5kg")
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Test
    void handleOrderCreatedEvent_delegatesToNotificationService() {
        orderEventListener.handleOrderCreatedEvent(event);

        verify(notificationService, times(1)).processOrderNotification(event);
    }
}
