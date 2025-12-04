package org.onlinestore.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.onlinestore.notificationservice.dto.OrderResponse;
import org.onlinestore.notificationservice.entity.Order;

import java.util.List;

/**
 * OrderMapper выполняет преобразование сущностей Order
 * в DTO OrderResponse для передачи данных наружу.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    /**
     * Конвертация одной сущности Order в DTO OrderResponse.
     *
     * @param order сущность заказа
     * @return DTO с данными заказа
     */
    OrderResponse orderToOrderResponse(Order order);

    /**
     * Конвертация списка сущностей Order в список DTO OrderResponse.
     *
     * @param orders список сущностей заказов
     * @return список DTO заказов
     */
    List<OrderResponse> ordersToOrderResponse(List<Order> orders);

}
