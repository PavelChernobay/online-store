package org.onlinetstore.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.onlinetstore.inventoryservice.dto.CreateProduct;
import org.onlinetstore.inventoryservice.dto.ProductResponse;
import org.onlinetstore.inventoryservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Контроллер для работы с продуктами.
 * Содержит эндпоинты для создания, получения, получения списка и удаления продуктов.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * Создает новый продукт.
     *
     * @param createProduct DTO {@link CreateProduct} с данными нового продукта
     * @return DTO {@link ProductResponse} с информацией о созданном продукте
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProduct createProduct) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(createProduct));
    }

    /**
     * Получает продукт по идентификатору.
     *
     * @param id UUID продукта
     * @return DTO {@link ProductResponse} с информацией о продукте
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    /**
     * Получает страницу всех продуктов с возможностью пагинации и сортировки.
     *
     * @param page      номер страницы
     * @param size      количество продуктов на странице
     * @param sortBy    поле для сортировки
     * @param ascending направление сортировки
     * @return страница DTO {@link ProductResponse} с продуктами
     */
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size, sortBy, ascending));
    }

    /**
     * Удаляет продукт по идентификатору.
     *
     * @param id UUID продукта
     * @return HTTP статус 204 No Content при успешном удалении
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable UUID id) {
        productService.deleteProductById(id);

        return ResponseEntity.noContent().build();
    }

}
