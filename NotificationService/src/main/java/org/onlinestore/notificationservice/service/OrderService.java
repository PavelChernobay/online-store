package org.onlinestore.notificationservice.service;

import dto.AnalyticsKafkaEvent;
import org.onlinestore.notificationservice.dto.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Сервис для работы с заказами.
 * Содержит методы для создания заказов и получения заказов с фильтрацией и пагинацией.
 */
public interface OrderService {

    /**
     * Создает заказ на основе события из Kafka.
     *
     * @param analyticsKafkaEvent объект события из Kafka
     */
    void createOrder(AnalyticsKafkaEvent analyticsKafkaEvent);

    /**
     * Получает страницу всех заказов.
     *
     * @param page      номер страницы
     * @param size      количество заказов на странице
     * @param sortBy    поле для сортировки
     * @param ascending направление сортировки
     * @return страница DTO {@link OrderResponse} с заказами
     */
    Page<OrderResponse> getAllOrders(int page, int size, String sortBy, boolean ascending);

    /**
     * Получает страницу заказов по идентификатору внешнего заказа.
     *
     * @param page      номер страницы
     * @param size      количество заказов на странице
     * @param sortBy    поле для сортировки
     * @param ascending направление сортировки
     * @param orderId   идентификатор внешнего заказа
     * @return страница DTO {@link OrderResponse} с заказами
     */
    Page<OrderResponse> getAllOrdersByOrderId(int page, int size, String sortBy, boolean ascending, UUID orderId);

    /**
     * Получает страницу заказов по идентификатору пользователя.
     *
     * @param page      номер страницы
     * @param size      количество заказов на странице
     * @param sortBy    поле для сортировки
     * @param ascending направление сортировки
     * @param userId    идентификатор пользователя
     * @return страница DTO {@link OrderResponse} с заказами
     */
    Page<OrderResponse> getAllOrdersByUserId(int page, int size, String sortBy, boolean ascending, UUID userId);

}
