package com.freshcart.orderservice.service.impl;

import com.freshcart.orderservice.client.ProductServiceClient;
import com.freshcart.orderservice.dto.OrderEventDTO;
import com.freshcart.orderservice.dto.OrderRequest;
import com.freshcart.orderservice.dto.OrderResponse;
import com.freshcart.orderservice.dto.ProductResponse;
import com.freshcart.orderservice.entity.Order;
import com.freshcart.orderservice.messaging.OrderEventPublisher;
import com.freshcart.orderservice.repository.OrderRepository;
import com.freshcart.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository      orderRepository;
    private final ProductServiceClient productServiceClient;
    private final OrderEventPublisher  orderEventPublisher;

    @Override
    public OrderResponse createOrder(OrderRequest request) {

        // STEP 1 - Call Product Service to get product details
        log.info("Fetching product with ID: {}", request.getProductId());
        ProductResponse product = productServiceClient
                .getProductById(request.getProductId());

        // STEP 2 - Calculate total price
        BigDecimal totalPrice = product.getUnitPrice()
                .multiply(BigDecimal.valueOf(request.getQuantity()));
        log.info("Total price calculated: {}", totalPrice);

        // STEP 3 - Build and save the order
        Order order = Order.builder()
                .customerId(request.getCustomerId())
                .productId(request.getProductId())
                .productName(product.getName())
                .quantity(request.getQuantity())
                .unitPrice(product.getUnitPrice())
                .totalPrice(totalPrice)
                .status("CREATED")
                .orderDate(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        log.info("Order saved with ID: {}", savedOrder.getOrderId());

        // STEP 4 - Publish event to RabbitMQ
        OrderEventDTO event = OrderEventDTO.builder()
                .customerId(savedOrder.getCustomerId())
                .orderId(savedOrder.getOrderId())
                .message("Order placed successfully for product: "
                        + product.getName())
                .timestamp(LocalDateTime.now())
                .build();

        orderEventPublisher.publishOrderCreatedEvent(event);

        // STEP 5 - Return response
        return OrderResponse.builder()
                .orderId(savedOrder.getOrderId())
                .customerId(savedOrder.getCustomerId())
                .productId(savedOrder.getProductId())
                .productName(savedOrder.getProductName())
                .quantity(savedOrder.getQuantity())
                .unitPrice(savedOrder.getUnitPrice())
                .totalPrice(savedOrder.getTotalPrice())
                .status(savedOrder.getStatus())
                .orderDate(savedOrder.getOrderDate())
                .build();
    }
}