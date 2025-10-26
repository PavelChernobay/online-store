package org.onlinestore.notificationservice.service;

import org.onlinestore.notificationservice.dto.AnalyticsKafkaEvent;
import org.onlinestore.notificationservice.dto.OrderResponse;
import org.springframework.data.domain.Page;

public interface OrderService {

    OrderResponse createOrder(AnalyticsKafkaEvent analyticsKafkaEvent);

    Page<OrderResponse> getAllOrders(int page, int size, String sortBy, boolean ascending);

    Page<OrderResponse> getAllOrdersByOrderId(int page, int size, String sortBy, boolean ascending);

    Page<OrderResponse> getAllOrdersByUserId(int page, int size, String sortBy, boolean ascending);

}
