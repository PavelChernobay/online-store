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

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<Page<OrderResponse>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        return ResponseEntity.ok(orderService.getAllOrders(page, size, sortBy, ascending));
    }

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

    @GetMapping("/{user_id}")
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
