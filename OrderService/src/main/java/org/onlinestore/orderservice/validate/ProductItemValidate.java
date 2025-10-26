package org.onlinestore.orderservice.validate;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.entity.BasketProduct;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.repository.BasketProductRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductItemValidate {

    private BasketProductRepository basketProductRepository;

    public BasketProduct checkProductItemById(UUID id) {
        return basketProductRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException(ProductNotFoundException.PRODUCT_NOT_FOUND));
    }

}
