package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link OrderProduct}.
 * <p>
 * Предоставляет стандартные CRUD-операции для объектов {@link OrderProduct}.
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, UUID> {
}