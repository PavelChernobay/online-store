package org.onlinestore.notificationservice.repository;

import org.onlinestore.notificationservice.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью Order.
 * Содержит базовые CRUD-операции и дополнительные методы фильтрации.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    /**
     * Получение заказов по идентификатору orderId.
     *
     * @param pageable объект пагинации и сортировки
     * @param orderId идентификатор внешнего заказа
     * @return страница заказов
     */
    Page<Order> findAllByOrderId(Pageable pageable, UUID orderId);

    /**
     * Получение заказов по идентификатору пользователя.
     *
     * @param pageable объект пагинации и сортировки
     * @param userId идентификатор пользователя
     * @return страница заказов
     */
    Page<Order> findAllByUserId(Pageable pageable, UUID userId);

}
