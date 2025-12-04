package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link Order}.
 * Предоставляет стандартные методы CRUD через JpaRepository
 * и кастомный метод для поиска заказа по идентификатору пользователя.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    /**
     * Найти заказ по идентификатору пользователя.
     *
     * @param id идентификатор пользователя, для которого нужно получить заказ
     * @return объект {@link Order}, принадлежащий пользователю
     * @throws org.springframework.dao.EmptyResultDataAccessException если заказ для пользователя не найден
     */
    Order findByUserId(UUID id);

}