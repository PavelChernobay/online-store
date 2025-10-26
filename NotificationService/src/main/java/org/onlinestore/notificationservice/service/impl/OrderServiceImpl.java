package org.onlinestore.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.notificationservice.dto.AnalyticsKafkaEvent;
import org.onlinestore.notificationservice.dto.OrderResponse;
import org.onlinestore.notificationservice.entity.Order;
import org.onlinestore.notificationservice.mapper.OrderMapper;
import org.onlinestore.notificationservice.repository.OrderRepository;
import org.onlinestore.notificationservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse createOrder(AnalyticsKafkaEvent consumer) {
        Order order = Order.builder()
                .orderId(consumer.orderId())
                .productId(consumer.productId())
                .userId(consumer.userId())
                .quantity(consumer.quantity())
                .price(consumer.price())
                .sale(consumer.sale())
                .totalPrice(consumer.totalPrice())
                .build();

        return orderMapper.orderToOrderResponse(orderRepository.save(order));
    }


    @Override
    public Page<OrderResponse> getAllOrders(int page, int size, String sortBy, boolean ascending) {
        return null;
    }

    @Override
    public Page<OrderResponse> getAllOrdersByOrderId(int page, int size, String sortBy, boolean ascending) {
        return null;
    }

    @Override
    public Page<OrderResponse> getAllOrdersByUserId(int page, int size, String sortBy, boolean ascending) {
        return null;
    }
}
