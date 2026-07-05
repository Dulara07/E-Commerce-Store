package com.freshcart.notificationservice.service.impl;

import com.freshcart.notificationservice.dto.Notification;
import com.freshcart.notificationservice.dto.OrderEventDTO;
import com.freshcart.notificationservice.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Override
    public Notification processOrderNotification(OrderEventDTO event) {
        Notification notification = Notification.builder()
                .customerId(event.getCustomerId())
                .orderId(event.getOrderId())
                .message(event.getMessage())
                .timestamp(event.getTimestamp())
                .build();

        log.info("Notification for customerId={}, orderId={}: {} (at {})",
                notification.getCustomerId(),
                notification.getOrderId(),
                notification.getMessage(),
                notification.getTimestamp());

        return notification;
    }
}
