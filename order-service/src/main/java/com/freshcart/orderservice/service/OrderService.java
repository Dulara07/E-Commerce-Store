package com.freshcart.orderservice.service;

import com.freshcart.orderservice.dto.OrderRequest;
import com.freshcart.orderservice.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrderById(Long id);
}