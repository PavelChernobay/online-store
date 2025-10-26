package org.onlinetstore.inventoryservice.validate;

import lombok.RequiredArgsConstructor;
import org.onlinetstore.inventoryservice.entity.Product;
import org.onlinetstore.inventoryservice.exception.ProductNotFoundException;
import org.onlinetstore.inventoryservice.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductValidate {

    private final ProductRepository productRepository;

    public Product checkProductByName(String name) {
        return productRepository.findByName(name).orElse(null);
    }

    public Product checkProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

}
