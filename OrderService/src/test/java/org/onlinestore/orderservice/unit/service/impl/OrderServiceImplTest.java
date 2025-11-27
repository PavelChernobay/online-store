package org.onlinestore.orderservice.unit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.OrderResponse;
import org.onlinestore.orderservice.entity.BasketProduct;
import org.onlinestore.orderservice.entity.InventoryOutbox;
import org.onlinestore.orderservice.entity.Order;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.generator.TestDataGenerator;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcResponse;
import org.onlinestore.orderservice.grpc.client.InventoryGrpcClient;
import org.onlinestore.orderservice.mapper.BasketProductMapper;
import org.onlinestore.orderservice.mapper.OrderMapper;
import org.onlinestore.orderservice.repository.OrderRepository;
import org.onlinestore.orderservice.service.AnalyticsOutboxService;
import org.onlinestore.orderservice.service.BasketService;
import org.onlinestore.orderservice.service.InventoryOutboxService;
import org.onlinestore.orderservice.service.UserService;
import org.onlinestore.orderservice.service.impl.OrderServiceImpl;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private User user;
    private BasketResponse basketResponse;
    private BasketProduct basketProduct;
    private ProductBatchGrpcResponse productBatchGrpcResponse;
    private InventoryOutbox inventoryOutbox;
    private Order order;
    private OrderResponse orderResponse;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private BasketService basketService;

    @Mock
    private BasketProductMapper basketProductMapper;

    @Mock
    private InventoryGrpcClient inventoryGrpcClient;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private InventoryOutboxService inventoryOutboxService;

    @Mock
    private AnalyticsOutboxService analyticsOutboxService;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        user = TestDataGenerator.generateUser();
        basketResponse = TestDataGenerator.generateBasketResponse();
        basketProduct = TestDataGenerator.generateBasketProduct();
        productBatchGrpcResponse = TestDataGenerator.generateProductBatchGrpcResponse();
        inventoryOutbox = TestDataGenerator.generateInventoryOutbox();
        order = TestDataGenerator.generateOrder();
        orderResponse = TestDataGenerator.generateOrderResponse();
    }

    @Test
    void createOrderSuccess() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(basketService.getBasketCurrentUser()).thenReturn(basketResponse);
        when(basketProductMapper.basketProductsResponseToBasketProducts(any()))
                .thenReturn(List.of(basketProduct));
        when(inventoryGrpcClient.getListProductByName(any(), anyString())).thenReturn(productBatchGrpcResponse);
        when(inventoryOutboxService.createInventoryOutbox(any(), anyInt()))
                .thenReturn(inventoryOutbox);
        when(orderRepository.saveAndFlush(any())).thenReturn(order);
        when(orderMapper.orderToOrderResponse(any())).thenReturn(orderResponse);

        OrderResponse result = orderService.createOrder();

        assertThat(result).isEqualTo(orderResponse);

        verify(userService, times(1)).getCurrentUser();
        verify(basketService, times(1)).getBasketCurrentUser();
        verify(basketProductMapper, times(1))
                .basketProductsResponseToBasketProducts(any());
        verify(inventoryGrpcClient, times(1)).getListProductByName(any(), anyString());
        verify(inventoryOutboxService, times(1)).createInventoryOutbox(any(), anyInt());
        verify(basketService, times(1)).clearBasket();
        verify(orderRepository, times(1)).saveAndFlush(any());
        verify(analyticsOutboxService, times(1)).createAnalyticsOutbox(any());
        verify(orderMapper, times(1)).orderToOrderResponse(any());
    }

    @Test
    void createOrder_shouldThrowException_whenStatusFailed() {
        ProductBatchGrpcResponse grpcResponse = productBatchGrpcResponse.toBuilder().setProductResponse(0, productBatchGrpcResponse.getProductResponse(0)
                        .toBuilder()
                        .setQuantity(0)
                        .build())
                .build();

        when(userService.getCurrentUser()).thenReturn(user);
        when(basketService.getBasketCurrentUser()).thenReturn(basketResponse);
        when(basketProductMapper.basketProductsResponseToBasketProducts(any()))
                .thenReturn(List.of(basketProduct));
        when(inventoryGrpcClient.getListProductByName(any(), anyString())).thenReturn(grpcResponse);

        assertThrows(RuntimeException.class, () -> orderService.createOrder());

        verify(userService, times(1)).getCurrentUser();
        verify(basketService, times(1)).getBasketCurrentUser();
        verify(basketProductMapper, times(1))
                .basketProductsResponseToBasketProducts(any());
        verify(inventoryGrpcClient, times(1)).getListProductByName(any(), anyString());
        verify(inventoryOutboxService, never()).createInventoryOutbox(any(), anyInt());
        verify(basketService, never()).clearBasket();
        verify(orderRepository, never()).saveAndFlush(any());
        verify(analyticsOutboxService, never()).createAnalyticsOutbox(any());
        verify(orderMapper, never()).orderToOrderResponse(any());
    }

    @Test
    void deleteOrderById() {
        orderRepository.deleteById(UUID.randomUUID());

        verify(orderRepository, times(1)).deleteById(any());
    }

    @Test
    void getOrderCurrentUser() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(orderMapper.orderToOrderResponse(any())).thenReturn(orderResponse);

        OrderResponse result = orderService.getOrderCurrentUser(user.getId());

        assertThat(result).isEqualTo(orderResponse);

        verify(userService, times(1)).getCurrentUser();
        verify(orderMapper, times(1)).orderToOrderResponse(any());
    }
}