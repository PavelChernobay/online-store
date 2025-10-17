package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.OrderResponse;

import java.util.UUID;

public interface OrderService {

    OrderResponse createOrder();

    void deleteOrderById(UUID id);

    OrderResponse getOrderCurrentUser(UUID userId);
}
