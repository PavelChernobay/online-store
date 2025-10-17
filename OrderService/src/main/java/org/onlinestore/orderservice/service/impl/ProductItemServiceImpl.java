package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.dto.ProductItemResponse;
import org.onlinestore.orderservice.entity.ProductItem;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.grpc.ProductGrpcResponse;
import org.onlinestore.orderservice.grpc.client.InventoryGrpcClient;
import org.onlinestore.orderservice.mapper.ProductItemMapper;
import org.onlinestore.orderservice.repository.ProductItemRepository;
import org.onlinestore.orderservice.service.ProductItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {

    private final ProductItemRepository productItemRepository;
    private final InventoryGrpcClient inventoryGrpcClient;
    private final ProductItemMapper productItemMapper;

    @Transactional
    @Override
    public ProductItem createProductItem(CreateProduct createProduct) {
        ProductGrpcResponse productGrpcResponse = inventoryGrpcClient.getProductByName(createProduct.productName());

        if (productGrpcResponse.getQuantity() == 0) {
            throw new ProductNotFoundException(
                    String.format(ProductNotFoundException.PRODUCT_IS_OUT_OF_STOCK,productGrpcResponse.getName()));
        }

        if (createProduct.quantity() > productGrpcResponse.getQuantity()) {
            throw new ProductNotFoundException(
                    String.format(ProductNotFoundException.FEW_PRODUCT_IN_STOCK, productGrpcResponse.getQuantity()));
        }

        return ProductItem.builder()
                .productId(UUID.fromString(productGrpcResponse.getId()))
                .name(productGrpcResponse.getName())
                .quantity(createProduct.quantity())
                .price(BigDecimal.valueOf(productGrpcResponse.getPrice()))
                .sale(productGrpcResponse.getSale())
                .totalSum(BigDecimal.valueOf(productGrpcResponse.getPrice())
                                .multiply(BigDecimal.valueOf(createProduct.quantity()))
                                .multiply(BigDecimal.valueOf(1 - productGrpcResponse.getSale() / 100.0)))
                .build();
    }

    @Override
    public void deleteProductItemById(UUID id) {
        productItemRepository.deleteById(id);
    }

    @Override
    public List<ProductItemResponse> getAllProductItems() {
        return productItemMapper.productItemsToProductItemResponses(productItemRepository.findAll());
    }
}
