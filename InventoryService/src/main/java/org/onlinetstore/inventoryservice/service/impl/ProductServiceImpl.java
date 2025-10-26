package org.onlinetstore.inventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinetstore.inventoryservice.dto.CreateProduct;
import org.onlinetstore.inventoryservice.dto.ProductResponse;
import org.onlinetstore.inventoryservice.entity.Product;
import org.onlinetstore.inventoryservice.mapper.ProductMapper;
import org.onlinetstore.inventoryservice.repository.ProductRepository;
import org.onlinetstore.inventoryservice.service.ProductService;
import org.onlinetstore.inventoryservice.validate.ProductValidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductValidate productValidate;
    private final ProductMapper productMapper;

    @Transactional
    @Override
    public ProductResponse addProduct(CreateProduct createProduct) {
        Product product = productValidate.checkProductByName(createProduct.name());

        if (product == null) {
            product = productMapper.CreateProductToProduct(createProduct);
            return productMapper.ProductToProductResponse(productRepository.save(product));
        }

        product.setPrice(createProduct.price());
        product.setQuantity(product.getQuantity() + createProduct.quantity());
        product.setSale(createProduct.sale());

        return productMapper.ProductToProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getAllProducts(int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<Product> productsPage = productRepository.findAll(pageable);
        List<ProductResponse> productResponses = productMapper.ProductsToProductResponses(productsPage.getContent());

        return new PageImpl<>(productResponses, pageable, productsPage.getTotalElements());
    }

    @Override
    public ProductResponse getProductById(UUID id) {
        return productMapper.ProductToProductResponse(productValidate.checkProductById(id));
    }

    @Override
    public void deleteProductById(UUID id) {
        productRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void updateProductQuantities(String productName, int quantity) {
        Product product = productValidate.checkProductByName(productName);
        product.setQuantity(product.getQuantity() - quantity);
    }
}
