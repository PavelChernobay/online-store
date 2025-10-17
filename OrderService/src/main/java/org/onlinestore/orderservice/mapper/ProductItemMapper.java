package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinestore.orderservice.dto.ProductItemResponse;
import org.onlinestore.orderservice.entity.ProductItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductItemMapper {

    @Mapping(target = "basketId", source = "basket.id")
    @Mapping(target = "orderId", source = "order.id")
    ProductItemResponse productItemToProductItemResponse(ProductItem productItem);

    @Mapping(target = "basket.id", source = "basketId")
    @Mapping(target = "order.id", source = "orderId")
    ProductItem productItemResponseToProductItem(ProductItemResponse productItemResponse);

    List<ProductItem> productItemResponsesToProductItems(List<ProductItemResponse> productItemResponses);

    List<ProductItemResponse> productItemsToProductItemResponses(List<ProductItem> productItems);

}
