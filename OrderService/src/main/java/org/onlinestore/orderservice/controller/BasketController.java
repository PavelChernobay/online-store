package org.onlinestore.orderservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.dto.ProductItemResponse;
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

@RestController
@RequestMapping("/api/baskets")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @PostMapping
    public ResponseEntity<BasketResponse> addProductToBasket(@Valid @RequestBody CreateProduct createProduct) {
        return ResponseEntity.ok(basketService.addProductToBasket(createProduct));
    }

    @GetMapping
    public ResponseEntity<BasketResponse> getBasketCurrentUser() {
        return ResponseEntity.ok(basketService.getBasketCurrentUser());
    }

    @DeleteMapping
    public ResponseEntity<BasketResponse> deleteProductInBasket(@RequestParam UUID id) {
        return ResponseEntity.ok(basketService.deleteProductToBasket(id));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductItemResponse>> getAllProductsInBasket() {
        return ResponseEntity.ok(basketService.getAllProductsToBasket());
    }

    @DeleteMapping("/products")
    public ResponseEntity<Void> clearProductInBasket() {
        basketService.clearBasket();

        return ResponseEntity.noContent().build();
    }

}
