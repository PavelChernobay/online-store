package org.onlinetstore.inventoryservice.validate;

import lombok.RequiredArgsConstructor;
import org.onlinetstore.inventoryservice.entity.Product;
import org.onlinetstore.inventoryservice.exception.ProductNotFoundException;
import org.onlinetstore.inventoryservice.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Класс для проверки существования продуктов.
 * <p>
 * Содержит методы для проверки продукта по имени или по идентификатору.
 * Используется для валидации перед выполнением операций с продуктами.
 */
@Component
@RequiredArgsConstructor
public class ProductValidate {

    private final ProductRepository productRepository;

    /**
     * Проверяет существование продукта по имени.
     *
     * @param name имя продукта
     * @return объект {@link Product}, если найден, иначе {@code null}
     */
    public Product checkProductByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    /**
     * Проверяет существование продукта по идентификатору.
     *
     * @param id идентификатор продукта
     * @return объект {@link Product}, если найден
     * @throws ProductNotFoundException если продукт с указанным id не найден
     */
    public Product checkProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

}
