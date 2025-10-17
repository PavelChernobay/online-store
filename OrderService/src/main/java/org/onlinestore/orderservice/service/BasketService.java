package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.dto.ProductItemResponse;

import java.util.List;
import java.util.UUID;

public interface BasketService {

    BasketResponse addProductToBasket(CreateProduct createProduct);

    BasketResponse deleteProductToBasket(UUID id);

    List<ProductItemResponse> getAllProductsToBasket();

    BasketResponse getBasketCurrentUser();

    void clearBasket();

}
