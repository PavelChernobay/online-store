package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinestore.orderservice.dto.OrderResponse;
import org.onlinestore.orderservice.entity.Order;

@Mapper(componentModel = "spring", uses = OrderProductMapper.class)
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "products", source = "orderProducts")
    @Mapping(source = "createdAt", target = "createdAt")
    OrderResponse orderToOrderResponse(Order order);

}
