package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.OrderProductResponse;
import org.onlinestore.orderservice.entity.OrderProduct;

/**
 * Сервис для работы с продуктами заказов.
 * Содержит методы для сохранения информации о продукте заказа и получения данных о нём.
 */
public interface OrderProductService {

    /**
     * Сохраняет продукт заказа.
     *
     * @param orderProduct сущность {@link OrderProduct} с данными продукта заказа
     * @return DTO {@link OrderProductResponse} с сохранённой информацией о продукте заказа
     */
    OrderProductResponse saveOrderProduct(OrderProduct orderProduct);

}