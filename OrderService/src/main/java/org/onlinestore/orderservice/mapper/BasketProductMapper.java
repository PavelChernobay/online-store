package org.onlinestore.orderservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.onlinestore.orderservice.dto.BasketProductResponse;
import org.onlinestore.orderservice.entity.BasketProduct;

import java.util.List;

/**
 * Mapper для преобразования сущности {@link BasketProduct} в DTO {@link BasketProductResponse} и обратно.
 * <p>
 * Используется для маппинга продуктов корзины между слоями persistence и DTO.
 */
@Mapper(componentModel = "spring")
public interface BasketProductMapper {

    /**
     * Преобразует сущность продукта корзины {@link BasketProduct} в DTO {@link BasketProductResponse}.
     *
     * @param basketProduct сущность продукта корзины
     * @return DTO продукта корзины с id корзины
     */
    @Mapping(target = "basketId", source = "basket.id")
    BasketProductResponse basketProductToBasketProductResponse(BasketProduct basketProduct);

    /**
     * Преобразует DTO продукта корзины {@link BasketProductResponse} обратно в сущность {@link BasketProduct}.
     *
     * @param basketProductResponse DTO продукта корзины
     * @return сущность продукта корзины с привязкой к корзине по id
     */
    @Mapping(target = "basket.id", source = "basketId")
    BasketProduct basketProductResponseToBasketProduct(BasketProductResponse basketProductResponse);

    /**
     * Преобразует список DTO продуктов корзины {@link BasketProductResponse} в список сущностей {@link BasketProduct}.
     *
     * @param basketProductResponses список DTO продуктов корзины
     * @return список сущностей продуктов корзины
     */
    List<BasketProduct> basketProductsResponseToBasketProducts(List<BasketProductResponse> basketProductResponses);

    /**
     * Преобразует список сущностей продуктов корзины {@link BasketProduct} в список DTO {@link BasketProductResponse}.
     *
     * @param basketProducts список сущностей продуктов корзины
     * @return список DTO продуктов корзины
     */
    List<BasketProductResponse> basketProductsToBasketProductResponses(List<BasketProduct> basketProducts);

}