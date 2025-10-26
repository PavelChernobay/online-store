package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinestore.orderservice.dto.OrderProductResponse;
import org.onlinestore.orderservice.entity.OrderProduct;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderProductMapper {

    @Mapping(target = "orderId", source = "order.id")
    OrderProductResponse orderProductToOrderProductResponse(OrderProduct orderProduct);

    @Mapping(target = "order.id", source = "orderId")
    OrderProduct orderProductResponseToOrderProduct(OrderProductResponse orderProductResponse);

    List<OrderProductResponse> orderProductsToOrderProductResponses(List<OrderProduct> orderProducts);

    List<OrderProduct> orderProductResponsesToOrderProducts(List<OrderProductResponse> orderProductResponses);

}
