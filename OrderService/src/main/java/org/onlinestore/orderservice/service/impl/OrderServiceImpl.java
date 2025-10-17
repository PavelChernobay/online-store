package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.OrderResponse;
import org.onlinestore.orderservice.entity.Order;
import org.onlinestore.orderservice.entity.ProductItem;
import org.onlinestore.orderservice.entity.Status;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcResponse;
import org.onlinestore.orderservice.grpc.ProductGrpcResponse;
import org.onlinestore.orderservice.grpc.client.InventoryGrpcClient;
import org.onlinestore.orderservice.mapper.OrderMapper;
import org.onlinestore.orderservice.mapper.ProductItemMapper;
import org.onlinestore.orderservice.repository.OrderRepository;
import org.onlinestore.orderservice.service.BasketService;
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
    private final ProductItemMapper productItemMapper;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final OrderMapper orderMapper;

    @Transactional
    @Override
    public OrderResponse createOrder() {
        User user = userService.getCurrentUser();
        BasketResponse basketResponse = basketService.getBasketCurrentUser();

        Order order = Order.builder()
                .user(user)
                .status(Status.CREATED)
                .totalPrice(basketResponse.totalSum())
                .build();

        try {
            List<ProductItem> productItems = productItemMapper
                    .productItemResponsesToProductItems(basketResponse.products());
            List<String> listName = productItems.stream()
                    .map(ProductItem::getName)
                    .toList();

            ProductBatchGrpcResponse inventoryResponse = inventoryGrpcClient.getListProductByName(listName);

            validateQuantityProducts(inventoryResponse, productItems);

            productItems.forEach(productItem -> {
                productItem.setOrder(order);
                order.getProductItems().add(productItem);
            });
        } catch (RuntimeException ex) {
            order.setStatus(Status.FAILED);
            orderRepository.save(order);
            throw ex;
        }

        basketService.clearBasket();
        Order saveOrder = orderRepository.save(order);

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

    private void validateQuantityProducts(ProductBatchGrpcResponse inventoryResponse, List<ProductItem> productItems) {
        Map<String, Integer> inventoryProducts = new HashMap<>();
        for (ProductGrpcResponse product : inventoryResponse.getProductResponseList()) {
            String name = product.getName();
            int quantity = product.getQuantity();
            inventoryProducts.put(name, quantity);
        }

        List<String> problems = new ArrayList<>();

        for (ProductItem productItem : productItems) {
            String name = productItem.getName();
            int requiredQuantity = productItem.getQuantity();

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
