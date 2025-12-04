package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.entity.AnalyticsOutbox;
import org.onlinestore.orderservice.entity.Order;

import java.util.List;

/**
 * Сервис для работы с событиями аналитики.
 * Содержит методы для создания и обработки аналитических событий.
 */
public interface AnalyticsOutboxService {

    /**
     * Создает событие аналитики на основе заказа.
     *
     * @param order объект заказа
     */
    void createAnalyticsOutbox(Order order);

    /**
     * Обновляет статус списка событий аналитики.
     *
     * @param analyticsOutboxes список событий аналитики
     */
    void updateEventStatus(List<AnalyticsOutbox> analyticsOutboxes);

    /**
     * Получает все события аналитики со статусом NEW.
     *
     * @return список событий аналитики
     */
    List<AnalyticsOutbox> getAllAnalyticsOutboxNewStatus();

    /**
     * Обрабатывает все события аналитики.
     */
    void processAnalyticsEvents();

}