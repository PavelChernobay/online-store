package org.onlinestore.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.OrderResponse;
import org.onlinestore.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-контроллер для работы с заказами.
 * <p>
 * Предоставляет эндпоинт для создания нового заказа.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Создает новый заказ для текущего пользователя.
     *
     * @return DTO {@link OrderResponse} с информацией о созданном заказе
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder() {
        return ResponseEntity.ok(orderService.createOrder());
    }

}
