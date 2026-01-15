package org.onlinestore.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.onlinestore.notificationservice.dto.OrderResponse;
import org.onlinestore.notificationservice.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Контроллер для получения информации о заказах.
 * Предоставляет эндпоинты для получения списка заказов,
 * а также заказов конкретного пользователя или конкретного заказа.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Возвращает страницу всех заказов.
     *
     * @param page      номер страницы
     * @param size      количество элементов на странице
     * @param sortBy    поле для сортировки
     * @param ascending направление сортировки
     * @return страница DTO {@link OrderResponse} со всеми заказами
     */
    @GetMapping("/all")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        return ResponseEntity.ok(orderService.getAllOrders(page, size, sortBy, ascending));
    }

    /**
     * Возвращает страницу заказов по идентификатору заказа.
     *
     * @param orderId   идентификатор заказа
     * @param page      номер страницы
     * @param size      количество элементов на странице
     * @param sortBy    поле для сортировки
     * @param ascending направление сортировки
     * @return страница DTO {@link OrderResponse} с заказами, связанными с указанным ID заказа
     */
    @GetMapping("/{order_id}")
    public ResponseEntity<Page<OrderResponse>> getAllOrdersByOrderId(
            @PathVariable("order_id") UUID orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        return ResponseEntity.ok(orderService.getAllOrdersByOrderId(page, size, sortBy, ascending, orderId));
    }

    /**
     * Возвращает страницу заказов пользователя по его идентификатору.
     *
     * @param userId    идентификатор пользователя
     * @param page      номер страницы
     * @param size      количество элементов на странице
     * @param sortBy    поле для сортировки
     * @param ascending направление сортировки
     * @return страница DTO {@link OrderResponse} с заказами пользователя
     */
    @GetMapping("/users/{user_id}")
    public ResponseEntity<Page<OrderResponse>> getAllOrdersByUserId(
            @PathVariable("user_id") UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        return ResponseEntity.ok(orderService.getAllOrdersByUserId(page, size, sortBy, ascending, userId));
    }

}
