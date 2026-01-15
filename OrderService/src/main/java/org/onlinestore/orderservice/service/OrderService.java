package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.OrderResponse;

import java.util.UUID;

/**
 * Сервис для работы с заказами.
 * Содержит методы для создания, удаления и получения заказов текущего пользователя.
 */
public interface OrderService {

    /**
     * Создает новый заказ.
     *
     * @return DTO {@link OrderResponse} с информацией о созданном заказе
     */
    OrderResponse createOrder();

    /**
     * Удаляет заказ по его идентификатору.
     *
     * @param id идентификатор заказа
     */
    void deleteOrderById(UUID id);

    /**
     * Получает заказ текущего пользователя по идентификатору пользователя.
     *
     * @param userId идентификатор пользователя
     * @return DTO {@link OrderResponse} с информацией о заказе пользователя
     */
    OrderResponse getOrderCurrentUser(UUID userId);

}