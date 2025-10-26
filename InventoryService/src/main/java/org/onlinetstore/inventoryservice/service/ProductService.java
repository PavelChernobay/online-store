package org.onlinetstore.inventoryservice.service;

import org.onlinetstore.inventoryservice.dto.CreateProduct;
import org.onlinetstore.inventoryservice.dto.ProductResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductService {

    ProductResponse addProduct(CreateProduct createProduct);

    Page<ProductResponse> getAllProducts(int page, int size, String sortBy, boolean ascending);

    ProductResponse getProductById(UUID id);

    void deleteProductById(UUID id);

    void updateProductQuantities(String productName, int quantity);

}
