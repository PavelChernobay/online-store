package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.entity.InventoryOutbox;

import java.util.List;

/**
 * Сервис для работы с Outbox-событиями инвентаря.
 * Содержит методы для создания событий, обновления их статуса и получения новых событий.
 */
public interface InventoryOutboxService {

    /**
     * Создает новое событие инвентаря с указанным продуктом и количеством.
     *
     * @param productName название продукта
     * @param quantity    количество продукта
     * @return сущность {@link InventoryOutbox} с созданным событием
     */
    InventoryOutbox createInventoryOutbox(String productName, int quantity);

    /**
     * Обновляет статус списка событий инвентаря.
     *
     * @param inventoryOutboxes список событий инвентаря
     */
    void updateEventStatus(List<InventoryOutbox> inventoryOutboxes);

    /**
     * Получает все события инвентаря со статусом NEW.
     *
     * @return список новых событий {@link InventoryOutbox}
     */
    List<InventoryOutbox> getAllInventoryOutboxNewEventsStatus();

}