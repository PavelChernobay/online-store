package org.onlinestore.orderservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.dto.BasketProductResponse;
import org.onlinestore.orderservice.service.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для работы с корзиной пользователя.
 * <p>
 * Предоставляет эндпоинты для добавления, удаления и получения товаров в корзине текущего пользователя.
 */
@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    /**
     * Добавляет продукт в корзину текущего пользователя.
     *
     * @param createProduct DTO {@link CreateProduct} с данными продукта для добавления
     * @return DTO {@link BasketResponse} с информацией о текущей корзине
     */
    @PostMapping
    public ResponseEntity<BasketResponse> addProductToBasket(@Valid @RequestBody CreateProduct createProduct) {
        return ResponseEntity.ok(basketService.addProductToBasket(createProduct));
    }

    /**
     * Получает корзину текущего пользователя.
     *
     * @return DTO {@link BasketResponse} с содержимым корзины
     */
    @GetMapping
    public ResponseEntity<BasketResponse> getBasketCurrentUser() {
        return ResponseEntity.ok(basketService.getBasketCurrentUser());
    }

    /**
     * Удаляет продукт из корзины по идентификатору.
     *
     * @param id идентификатор продукта для удаления
     * @return DTO {@link BasketResponse} с обновленной корзиной
     */
    @DeleteMapping
    public ResponseEntity<BasketResponse> deleteProductInBasket(@RequestParam UUID id) {
        return ResponseEntity.ok(basketService.deleteProductToBasket(id));
    }

    /**
     * Получает список всех продуктов, находящихся в корзине текущего пользователя.
     *
     * @return список DTO {@link BasketProductResponse} с информацией о продуктах
     */
    @GetMapping("/products")
    public ResponseEntity<List<BasketProductResponse>> getAllProductsInBasket() {
        return ResponseEntity.ok(basketService.getAllProductsToBasket());
    }

    /**
     * Очищает корзину текущего пользователя.
     */
    @DeleteMapping("/products")
    public ResponseEntity<Void> clearProductInBasket() {
        basketService.clearBasket();

        return ResponseEntity.noContent().build();
    }

}
