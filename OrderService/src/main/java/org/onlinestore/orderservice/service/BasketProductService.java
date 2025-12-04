package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.entity.BasketProduct;

/**
 * Сервис для работы с товарами в корзине.
 * Содержит методы для создания элементов корзины.
 */
public interface BasketProductService {

    /**
     * Создает новый элемент корзины на основе данных о продукте.
     *
     * @param createProduct DTO с данными для создания продукта {@link CreateProduct}
     * @return сущность корзинного продукта {@link BasketProduct}
     */
    BasketProduct createProductItem(CreateProduct createProduct);

}