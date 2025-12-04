package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.AnalyticsOutbox;
import org.onlinestore.orderservice.entity.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link AnalyticsOutbox}.
 * <p>
 * Предоставляет стандартные CRUD-операции и дополнительные методы для работы со статусами событий.
 */
@Repository
public interface AnalyticsOutboxRepository extends JpaRepository<AnalyticsOutbox, Long> {

    /**
     * Возвращает список всех записей аналитики с указанным статусом события.
     *
     * @param eventStatus статус события {@link EventStatus}
     * @return список сущностей {@link AnalyticsOutbox} с заданным статусом
     */
    List<AnalyticsOutbox> findAllByEventStatus(EventStatus eventStatus);

}