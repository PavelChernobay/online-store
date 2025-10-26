package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.OrderResponse;
import org.onlinestore.orderservice.entity.Order;
import org.onlinestore.orderservice.entity.BasketProduct;
import org.onlinestore.orderservice.entity.OrderProduct;
import org.onlinestore.orderservice.entity.Status;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcResponse;
import org.onlinestore.orderservice.grpc.ProductGrpcResponse;
import org.onlinestore.orderservice.grpc.client.InventoryGrpcClient;
import org.onlinestore.orderservice.mapper.OrderMapper;
import org.onlinestore.orderservice.mapper.BasketProductMapper;
import org.onlinestore.orderservice.repository.OrderRepository;
import org.onlinestore.orderservice.service.AnalyticsOutboxService;
import org.onlinestore.orderservice.service.BasketService;
import org.onlinestore.orderservice.service.InventoryOutboxService;
import org.onlinestore.orderservice.service.OrderService;
import org.onlinestore.orderservice.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final BasketService basketService;
    private final BasketProductMapper basketProductMapper;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final OrderMapper orderMapper;
    private final InventoryOutboxService inventoryOutboxService;
    private final AnalyticsOutboxService analyticsOutboxService;

    @Transactional
    @Override
    public OrderResponse createOrder() {
        User user = userService.getCurrentUser();
        BasketResponse basketResponse = basketService.getBasketCurrentUser();

        Order order = Order.builder()
                .user(user)
                .status(Status.CREATED)
                .totalSum(basketResponse.totalSum())
                .build();

        try {
            List<BasketProduct> basketProducts = basketProductMapper
                    .basketProductsResponseToBasketProducts(basketResponse.products());
            List<String> listName = basketProducts.stream()
                    .map(BasketProduct::getName)
                    .toList();

            ProductBatchGrpcResponse inventoryResponse = inventoryGrpcClient.getListProductByName(listName);

            validateQuantityProducts(inventoryResponse, basketProducts);

            buildOrderProducts(basketProducts, order);
            basketProducts.forEach(product ->
                    inventoryOutboxService.createInventoryOutbox(product.getName(), product.getQuantity()));
        } catch (RuntimeException ex) {
            order.setStatus(Status.FAILED);
            orderRepository.save(order);
            throw ex;
        }

        basketService.clearBasket();

        Order saveOrder = orderRepository.saveAndFlush(order);
        analyticsOutboxService.createAnalyticsOutbox(saveOrder);

        return orderMapper.orderToOrderResponse(saveOrder);
    }

    @Override
    public void deleteOrderById(UUID id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderResponse getOrderCurrentUser(UUID userId) {
        return orderMapper.orderToOrderResponse(orderRepository
                .findByUserId(userService.getCurrentUser().getId()));
    }

    private void buildOrderProducts(List<BasketProduct> basketProducts, Order order) {
        List<OrderProduct> orderProducts = basketProducts.stream()
                .map(product -> OrderProduct.builder()
                        .productId(product.getProductId())
                        .order(order)
                        .name(product.getName())
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .sale(product.getSale())
                        .totalSum(product.getTotalSum())
                        .build())
                .toList();
        order.setOrderProducts(orderProducts);
    }

    private void validateQuantityProducts(ProductBatchGrpcResponse inventoryResponse, List<BasketProduct> basketProducts) {
        Map<String, Integer> inventoryProducts = new HashMap<>();
        for (ProductGrpcResponse product : inventoryResponse.getProductResponseList()) {
            String name = product.getName();
            int quantity = product.getQuantity();
            inventoryProducts.put(name, quantity);
        }

        List<String> problems = new ArrayList<>();

        for (BasketProduct basketProduct : basketProducts) {
            String name = basketProduct.getName();
            int requiredQuantity = basketProduct.getQuantity();

            Integer remainder = inventoryProducts.get(name);

            if (requiredQuantity > remainder) {
                problems.add(String.format("Продукта %s не хватает на складе. Остаток %d шт.",name, remainder));
            }
        }

        if (!problems.isEmpty()) {
            throw new ProductNotFoundException(String.join(" ,",problems));
        }
    }

}
