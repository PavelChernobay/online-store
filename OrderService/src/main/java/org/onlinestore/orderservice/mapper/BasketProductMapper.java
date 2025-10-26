package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinestore.orderservice.dto.BasketProductResponse;
import org.onlinestore.orderservice.entity.BasketProduct;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BasketProductMapper {

    @Mapping(target = "basketId", source = "basket.id")
    BasketProductResponse basketProductToBasketProductResponse(BasketProduct basketProduct);

    @Mapping(target = "basket.id", source = "basketId")
    BasketProduct basketProductResponseToBasketProduct(BasketProductResponse basketProductResponse);

    List<BasketProduct> basketProductsResponseToBasketProducts(List<BasketProductResponse> basketProductResponses);

    List<BasketProductResponse> basketProductsToBasketProductResponses(List<BasketProduct> basketProducts);

}
