package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.dto.BasketProductResponse;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с корзиной пользователя.
 * Содержит методы для добавления, удаления и получения товаров в корзине,
 * а также очистки корзины.
 */
public interface BasketService {

    /**
     * Добавляет новый продукт в корзину текущего пользователя.
     *
     * @param createProduct DTO с данными продукта для добавления {@link CreateProduct}
     * @return DTO текущей корзины {@link BasketResponse}
     */
    BasketResponse addProductToBasket(CreateProduct createProduct);

    /**
     * Удаляет продукт из корзины по идентификатору элемента корзины.
     *
     * @param id идентификатор элемента корзины
     * @return DTO текущей корзины {@link BasketResponse}
     */
    BasketResponse deleteProductToBasket(UUID id);

    /**
     * Получает список всех товаров текущей корзины.
     *
     * @return список DTO товаров в корзине {@link BasketProductResponse}
     */
    List<BasketProductResponse> getAllProductsToBasket();

    /**
     * Получает DTO корзины текущего пользователя.
     *
     * @return DTO текущей корзины {@link BasketResponse}
     */
    BasketResponse getBasketCurrentUser();

    /**
     * Очищает корзину текущего пользователя.
     */
    void clearBasket();

}