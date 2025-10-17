package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.dto.ProductItemResponse;
import org.onlinestore.orderservice.entity.ProductItem;

import java.util.List;
import java.util.UUID;

public interface ProductItemService {

    ProductItem createProductItem(CreateProduct createProduct);

    void deleteProductItemById(UUID id);

    List<ProductItemResponse> getAllProductItems();

}
