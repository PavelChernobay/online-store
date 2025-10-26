package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.entity.Basket;

@Mapper(componentModel = "spring", uses = BasketProductMapper.class)
public interface BasketMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "products", source = "basketProducts")
    BasketResponse basketToBasketResponse(Basket basket);

}
