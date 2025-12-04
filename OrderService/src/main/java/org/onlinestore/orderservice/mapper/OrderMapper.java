package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinestore.orderservice.dto.OrderResponse;
import org.onlinestore.orderservice.entity.Order;

/**
 * Mapper для преобразования сущности {@link Order} в DTO {@link OrderResponse}.
 * <p>
 * Используется для маппинга заказов между слоями persistence и DTO.
 * Также использует {@link OrderProductMapper} для преобразования продуктов заказа.
 */
@Mapper(componentModel = "spring", uses = OrderProductMapper.class)
public interface OrderMapper {

    /**
     * Преобразует сущность заказа {@link Order} в DTO {@link OrderResponse}.
     *
     * @param order сущность заказа
     * @return DTO заказа с id пользователя и списком продуктов заказа
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "products", source = "orderProducts")
    @Mapping(source = "createdAt", target = "createdAt")
    OrderResponse orderToOrderResponse(Order order);

}