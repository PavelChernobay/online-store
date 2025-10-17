package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.onlinestore.orderservice.dto.OrderResponse;
import org.onlinestore.orderservice.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse orderToOrderResponse(Order order);

}
