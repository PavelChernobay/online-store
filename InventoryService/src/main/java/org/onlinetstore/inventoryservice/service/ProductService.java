package org.onlinetstore.inventoryservice.service;

import org.onlinetstore.inventoryservice.dto.CreateProduct;
import org.onlinetstore.inventoryservice.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Сервис для работы с продуктами.
 * <p>
 * Предоставляет методы для создания, получения, обновления и удаления продуктов,
 * а также для обновления количества товаров на складе.
 */
public interface ProductService {

    /**
     * Создает новый продукт.
     *
     * @param createProduct DTO {@link CreateProduct} с данными для создания продукта
     * @return DTO {@link ProductResponse} с информацией о созданном продукте
     */
    ProductResponse addProduct(CreateProduct createProduct);

    /**
     * Возвращает страницу всех продуктов.
     *
     * @param page      номер страницы
     * @param size      количество продуктов на странице
     * @param sortBy    поле для сортировки
     * @param ascending направление сортировки
     * @return страница DTO {@link ProductResponse} с продуктами
     */
    Page<ProductResponse> getAllProducts(int page, int size, String sortBy, boolean ascending);

    /**
     * Получает продукт по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return DTO {@link ProductResponse} с информацией о продукте
     */
    ProductResponse getProductById(UUID id);

    /**
     * Удаляет продукт по его идентификатору.
     *
     * @param id идентификатор продукта
     */
    void deleteProductById(UUID id);

    /**
     * Обновляет количество продукта на складе.
     *
     * @param productName имя продукта
     * @param quantity    новое количество
     */
    void updateProductQuantities(String productName, int quantity);

}
