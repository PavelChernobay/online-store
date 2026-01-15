package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link BasketProduct}.
 * <p>
 * Предоставляет стандартные CRUD-операции и методы для поиска продуктов в корзине.
 */
@Repository
public interface BasketProductRepository extends JpaRepository<BasketProduct, UUID> {

    /**
     * Ищет продукт в корзине по его имени.
     *
     * @param productName имя продукта
     * @return {@link Optional} с найденным {@link BasketProduct} или пустой, если продукт не найден
     */
    Optional<BasketProduct> findByName(String productName);

}