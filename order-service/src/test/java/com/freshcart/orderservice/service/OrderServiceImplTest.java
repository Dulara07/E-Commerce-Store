package com.freshcart.orderservice.service;

import com.freshcart.orderservice.client.ProductServiceClient;
import com.freshcart.orderservice.dto.OrderEventDTO;
import com.freshcart.orderservice.dto.OrderRequest;
import com.freshcart.orderservice.dto.OrderResponse;
import com.freshcart.orderservice.dto.ProductResponse;
import com.freshcart.orderservice.entity.Order;
import com.freshcart.orderservice.messaging.OrderEventPublisher;
import com.freshcart.orderservice.repository.OrderRepository;
import com.freshcart.orderservice.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductServiceClient productServiceClient;

    @Mock
    private OrderEventPublisher orderEventPublisher;

    @InjectMocks
    private OrderServiceImpl orderService;

    private ProductResponse mockProduct;
    private OrderRequest orderRequest;

    @BeforeEach
    void setUp() {
        mockProduct = new ProductResponse();
        mockProduct.setProductId(1L);
        mockProduct.setName("Rice 5kg");
        mockProduct.setUnitPrice(new BigDecimal("1800.00"));
        mockProduct.setStockQuantity(50);

        orderRequest = new OrderRequest();
        orderRequest.setCustomerId(101L);
        orderRequest.setProductId(1L);
        orderRequest.setQuantity(2);
    }

    @Test
    void createOrder_shouldCalculateTotalPriceCorrectly() {
        when(productServiceClient.getProductById(1L))
                .thenReturn(mockProduct);

        Order savedOrder = Order.builder()
                .orderId(5001L)
                .customerId(101L)
                .productId(1L)
                .productName("Rice 5kg")
                .quantity(2)
                .unitPrice(new BigDecimal("1800.00"))
                .totalPrice(new BigDecimal("3600.00"))
                .status("CREATED")
                .orderDate(LocalDateTime.now())
                .build();

        when(orderRepository.save(any(Order.class)))
                .thenReturn(savedOrder);

        OrderResponse response = orderService.createOrder(orderRequest);

        assertThat(response.getTotalPrice()).isEqualByComparingTo("3600.00");
        assertThat(response.getProductName()).isEqualTo("Rice 5kg");
        assertThat(response.getStatus()).isEqualTo("CREATED");
    }

    @Test
    void createOrder_shouldCallProductServiceOnce() {
        when(productServiceClient.getProductById(1L))
                .thenReturn(mockProduct);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order o = invocation.getArgument(0);
                    o.setOrderId(5001L);
                    return o;
                });

        orderService.createOrder(orderRequest);

        verify(productServiceClient, times(1)).getProductById(1L);
    }

    @Test
    void createOrder_shouldSaveOrderToDatabase() {
        when(productServiceClient.getProductById(1L))
                .thenReturn(mockProduct);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order o = invocation.getArgument(0);
                    o.setOrderId(5001L);
                    return o;
                });

        orderService.createOrder(orderRequest);

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void createOrder_shouldPublishRabbitMQEvent() {
        when(productServiceClient.getProductById(1L))
                .thenReturn(mockProduct);
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> {
                    Order o = invocation.getArgument(0);
                    o.setOrderId(5001L);
                    return o;
                });

        orderService.createOrder(orderRequest);

        verify(orderEventPublisher, times(1))
                .publishOrderCreatedEvent(any(OrderEventDTO.class));
    }
}