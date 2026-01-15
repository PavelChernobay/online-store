package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.EventStatus;
import org.onlinestore.orderservice.entity.InventoryOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link InventoryOutbox}.
 * <p>
 * Предоставляет стандартные CRUD-операции и дополнительные методы для выборки по статусу события.
 */
public interface InventoryOutboxRepository extends JpaRepository<InventoryOutbox, Long> {

    /**
     * Находит все записи из Outbox с указанным статусом события.
     *
     * @param eventStatus статус события {@link EventStatus}
     * @return список {@link InventoryOutbox} с указанным статусом
     */
    List<InventoryOutbox> findAllByEventStatus(EventStatus eventStatus);

}