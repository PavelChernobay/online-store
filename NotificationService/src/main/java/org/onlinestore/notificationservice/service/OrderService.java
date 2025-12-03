package org.onlinestore.notificationservice.service;

import dto.AnalyticsKafkaEvent;
import org.onlinestore.notificationservice.dto.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface OrderService {

    void createOrder(AnalyticsKafkaEvent analyticsKafkaEvent);

    Page<OrderResponse> getAllOrders(int page, int size, String sortBy, boolean ascending);

    Page<OrderResponse> getAllOrdersByOrderId(int page, int size, String sortBy, boolean ascending, UUID orderId);

    Page<OrderResponse> getAllOrdersByUserId(int page, int size, String sortBy, boolean ascending, UUID userId);

}
