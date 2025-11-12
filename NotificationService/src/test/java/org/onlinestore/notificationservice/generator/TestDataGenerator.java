package org.onlinestore.notificationservice.generator;

import org.onlinestore.notificationservice.dto.AnalyticsKafkaEvent;
import org.onlinestore.notificationservice.dto.OrderResponse;
import org.onlinestore.notificationservice.entity.Order;

import java.math.BigDecimal;
import java.util.UUID;

public class TestDataGenerator {

    public static final int QUANTITY = 5;
    public static final int PRICE = 100;
    public static final int TOTAL_PRICE = 500;
    public static final int PAGE = 0;
    public static final int SIZE = 10;
    public static final String SORT_BY = "createdAt";
    public static final boolean ASCENDING = true;

    public static Order generateOrder() {
        return Order.builder()
                .id(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .quantity(QUANTITY)
                .price(BigDecimal.valueOf(PRICE))
                .totalPrice(BigDecimal.valueOf(TOTAL_PRICE))
                .build();
    }

    public static OrderResponse generateOrderResponse() {
        return OrderResponse.builder()
                .id(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .quantity(QUANTITY)
                .price(BigDecimal.valueOf(PRICE))
                .totalPrice(BigDecimal.valueOf(TOTAL_PRICE))
                .build();
    }

    public static AnalyticsKafkaEvent generateAnalyticsKafkaEvent() {
        return AnalyticsKafkaEvent.builder()
                .orderId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .quantity(QUANTITY)
                .price(BigDecimal.valueOf(PRICE))
                .totalPrice(BigDecimal.valueOf(TOTAL_PRICE))
                .build();
    }

}
