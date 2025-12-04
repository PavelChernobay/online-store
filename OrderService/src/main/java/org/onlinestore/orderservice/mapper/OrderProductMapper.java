package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinestore.orderservice.dto.OrderProductResponse;
import org.onlinestore.orderservice.entity.OrderProduct;

import java.util.List;

/**
 * Mapper для преобразования сущности {@link OrderProduct} в DTO {@link OrderProductResponse} и обратно.
 * <p>
 * Используется для маппинга продуктов заказов между слоями persistence и DTO.
 */
@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    /**
     * Преобразует сущность продукта заказа {@link OrderProduct} в DTO {@link OrderProductResponse}.
     *
     * @param orderProduct сущность продукта заказа
     * @return DTO продукта заказа с id заказа
     */
    @Mapping(target = "orderId", source = "order.id")
    OrderProductResponse orderProductToOrderProductResponse(OrderProduct orderProduct);

    /**
     * Преобразует DTO продукта заказа {@link OrderProductResponse} обратно в сущность {@link OrderProduct}.
     *
     * @param orderProductResponse DTO продукта заказа
     * @return сущность продукта заказа
     */
    @Mapping(target = "order.id", source = "orderId")
    OrderProduct orderProductResponseToOrderProduct(OrderProductResponse orderProductResponse);

    /**
     * Преобразует список сущностей продуктов заказов в список DTO продуктов заказов.
     *
     * @param orderProducts список сущностей продуктов заказов
     * @return список DTO продуктов заказов
     */
    List<OrderProductResponse> orderProductsToOrderProductResponses(List<OrderProduct> orderProducts);

    /**
     * Преобразует список DTO продуктов заказов обратно в список сущностей продуктов заказов.
     *
     * @param orderProductResponses список DTO продуктов заказов
     * @return список сущностей продуктов заказов
     */
    List<OrderProduct> orderProductResponsesToOrderProducts(List<OrderProductResponse> orderProductResponses);

}