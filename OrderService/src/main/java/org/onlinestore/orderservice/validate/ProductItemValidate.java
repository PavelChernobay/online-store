package org.onlinestore.orderservice.validate;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.entity.ProductItem;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.repository.ProductItemRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductItemValidate {

    private ProductItemRepository productItemRepository;

    public ProductItem checkProductItemById(UUID id) {
        return productItemRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException(ProductNotFoundException.PRODUCT_NOT_FOUND));
    }

}
