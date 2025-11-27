package org.onlinestore.notificationservice.unit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onlinestore.notificationservice.dto.AnalyticsKafkaEvent;
import org.onlinestore.notificationservice.dto.OrderResponse;
import org.onlinestore.notificationservice.entity.Order;
import org.onlinestore.notificationservice.generator.TestDataGenerator;
import org.onlinestore.notificationservice.mapper.OrderMapper;
import org.onlinestore.notificationservice.repository.OrderRepository;
import org.onlinestore.notificationservice.service.impl.OrderServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private Order order;
    private OrderResponse orderResponse;
    private AnalyticsKafkaEvent analyticsKafkaEvent;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        order = TestDataGenerator.generateOrder();
        orderResponse = TestDataGenerator.generateOrderResponse();
        analyticsKafkaEvent = TestDataGenerator.generateAnalyticsKafkaEvent();
    }

    @Test
    void createOrderSuccess() {
        orderService.createOrder(analyticsKafkaEvent);

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void getAllOrders() {
        PageImpl<Order> orders = new PageImpl<>(List.of(order));
        PageImpl<OrderResponse> orderResponses = new PageImpl<>(List.of(orderResponse));

        when(orderRepository.findAll(any(Pageable.class))).thenReturn(orders);
        when(orderMapper.ordersToOrderResponse(anyList())).thenReturn(orderResponses.getContent());

        Page<OrderResponse> result = orderService.getAllOrders(
                TestDataGenerator.PAGE, TestDataGenerator.SIZE, TestDataGenerator.SORT_BY, TestDataGenerator.ASCENDING);

        assertThat(result.getContent().size()).isEqualTo(orderResponses.getContent().size());
        assertThat(result.getContent().get(0)).isEqualTo(orderResponses.getContent().get(0));
        assertThat(result.getTotalElements()).isEqualTo(1);

        verify(orderRepository, times(1)).findAll(any(Pageable.class));
        verify(orderMapper, times(1)).ordersToOrderResponse(anyList());

    }

    @Test
    void getAllOrdersByOrderId() {
        PageImpl<Order> orders = new PageImpl<>(List.of(order));
        PageImpl<OrderResponse> orderResponses = new PageImpl<>(List.of(orderResponse));

        when(orderRepository.findAllByOrderId(any(Pageable.class), any())).thenReturn(orders);
        when(orderMapper.ordersToOrderResponse(anyList())).thenReturn(orderResponses.getContent());

        Page<OrderResponse> result = orderService.getAllOrdersByOrderId(
                TestDataGenerator.PAGE, TestDataGenerator.SIZE, TestDataGenerator.SORT_BY,
                TestDataGenerator.ASCENDING, UUID.randomUUID());

        assertThat(result.getContent().size()).isEqualTo(orderResponses.getContent().size());
        assertThat(result.getContent().get(0)).isEqualTo(orderResponses.getContent().get(0));
        assertThat(result.getTotalElements()).isEqualTo(1);

        verify(orderRepository, times(1))
                .findAllByOrderId(any(Pageable.class), any());
        verify(orderMapper, times(1)).ordersToOrderResponse(anyList());
    }

    @Test
    void getAllOrdersByUserId() {
        PageImpl<Order> orders = new PageImpl<>(List.of(order));
        PageImpl<OrderResponse> orderResponses = new PageImpl<>(List.of(orderResponse));

        when(orderRepository.findAllByUserId(any(Pageable.class), any())).thenReturn(orders);
        when(orderMapper.ordersToOrderResponse(anyList())).thenReturn(orderResponses.getContent());

        Page<OrderResponse> result = orderService.getAllOrdersByUserId(
                TestDataGenerator.PAGE, TestDataGenerator.SIZE, TestDataGenerator.SORT_BY,
                TestDataGenerator.ASCENDING, UUID.randomUUID());

        assertThat(result.getContent().size()).isEqualTo(orderResponses.getContent().size());
        assertThat(result.getContent().get(0)).isEqualTo(orderResponses.getContent().get(0));
        assertThat(result.getTotalElements()).isEqualTo(1);

        verify(orderRepository, times(1))
                .findAllByUserId(any(Pageable.class), any());
        verify(orderMapper, times(1)).ordersToOrderResponse(anyList());
    }
}