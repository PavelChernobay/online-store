package org.onlinestore.notificationservice.mapper;

import org.mapstruct.Mapper;
import org.onlinestore.notificationservice.dto.OrderResponse;
import org.onlinestore.notificationservice.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse orderToOrderResponse(Order order);

    List<OrderResponse> ordersToOrderResponse(List<Order> orders);

}
