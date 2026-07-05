package com.freshcart.notificationservice.listener;

import com.freshcart.notificationservice.config.RabbitMQConfig;
import com.freshcart.notificationservice.dto.OrderEventDTO;
import com.freshcart.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleOrderCreatedEvent(OrderEventDTO event) {
        log.info("Received order event from RabbitMQ: {}", event);
        notificationService.processOrderNotification(event);
    }
}
