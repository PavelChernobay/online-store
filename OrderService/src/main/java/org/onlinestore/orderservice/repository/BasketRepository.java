package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.Basket;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link Basket}.
 * <p>
 * Предоставляет стандартные CRUD-операции и методы для поиска корзины пользователя.
 */
public interface BasketRepository extends JpaRepository<Basket, UUID> {

    /**
     * Находит корзину по ID пользователя.
     *
     * @param id ID пользователя
     * @return {@link Optional} с найденной корзиной или пустой, если корзина не существует
     */
    Optional<Basket> findByUserId(UUID id);

    /**
     * Находит корзину по ID пользователя вместе с продуктами в корзине.
     * Используется {@link EntityGraph} для подгрузки связанной коллекции {@code basketProducts}.
     *
     * @param id ID пользователя
     * @return {@link Optional} с корзиной и продуктами или пустой, если корзина не существует
     */
    @EntityGraph(attributePaths = "basketProducts")
    Optional<Basket> findWithProductItemsByUserId(UUID id);

    /**
     * Удаляет корзину пользователя по ID пользователя.
     *
     * @param id ID пользователя
     */
    void deleteByUserId(UUID id);

}