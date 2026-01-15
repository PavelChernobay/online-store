package org.onlinetstore.inventoryservice.repository;

import org.onlinetstore.inventoryservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностями {@link Product}.
 * <p>
 * Предоставляет стандартные CRUD-операции, а также дополнительные методы для поиска продукта по имени.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    /**
     * Ищет продукт по его имени.
     *
     * @param name имя продукта
     * @return Optional с найденным продуктом или пустой, если продукта с таким именем нет
     */
    Optional<Product> findByName(String name);

}
