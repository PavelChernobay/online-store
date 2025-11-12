package org.onlinestore.notificationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.notificationservice.dto.AnalyticsKafkaEvent;
import org.onlinestore.notificationservice.dto.OrderResponse;
import org.onlinestore.notificationservice.entity.Order;
import org.onlinestore.notificationservice.mapper.OrderMapper;
import org.onlinestore.notificationservice.repository.OrderRepository;
import org.onlinestore.notificationservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        Pageable pageable = getPageable(page, size, sortBy, ascending);

        Page<Order> all = orderRepository.findAll(pageable);

        return getPageOrderResponses(all, pageable);
    }

    private PageImpl<OrderResponse> getPageOrderResponses(Page<Order> all, Pageable pageable) {
        List<OrderResponse> orderResponses = orderMapper.ordersToOrderResponse(all.getContent());

        return new PageImpl<>(orderResponses, pageable, all.getTotalElements());
    }

    private Pageable getPageable(int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        return PageRequest.of(page, size, sort);
    }

    @Override
    public Page<OrderResponse> getAllOrdersByOrderId(int page, int size, String sortBy, boolean ascending, UUID orderId) {
        Pageable pageable = getPageable(page, size, sortBy, ascending);
        Page<Order> allByOrderId = orderRepository.findAllByOrderId(pageable, orderId);

        return getPageOrderResponses(allByOrderId, pageable);
    }

    @Override
    public Page<OrderResponse> getAllOrdersByUserId(int page, int size, String sortBy, boolean ascending, UUID userId) {
        Pageable pageable = getPageable(page, size, sortBy, ascending);
        Page<Order> allByUserId = orderRepository.findAllByUserId(pageable, userId);

        return getPageOrderResponses(allByUserId, pageable);
    }
}
