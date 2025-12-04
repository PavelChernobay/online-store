package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.entity.Basket;

/**
 * Mapper для преобразования сущности {@link Basket} в {@link BasketResponse}.
 * <p>
 * Использует {@link BasketProductMapper} для маппинга списка продуктов корзины.
 * С помощью MapStruct автоматически генерирует реализацию маппинга.
 */
@Mapper(componentModel = "spring", uses = BasketProductMapper.class)
public interface BasketMapper {

    /**
     * Преобразует сущность корзины {@link Basket} в DTO {@link BasketResponse}.
     *
     * @param basket сущность корзины
     * @return DTO корзины с id пользователя и списком продуктов
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "products", source = "basketProducts")
    BasketResponse basketToBasketResponse(Basket basket);

}