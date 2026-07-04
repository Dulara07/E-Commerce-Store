package com.freshcart.orderservice.messaging;

import com.freshcart.orderservice.config.RabbitMQConfig;
import com.freshcart.orderservice.dto.OrderEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishOrderCreatedEvent(OrderEventDTO event) {
        log.info("Publishing order event to RabbitMQ: {}", event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                event
        );
        log.info("Order event published successfully for orderId: {}", event.getOrderId());
    }
}