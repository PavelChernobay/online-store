package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.entity.BasketProduct;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.grpc.ProductGrpcResponse;
import org.onlinestore.orderservice.grpc.client.InventoryGrpcClient;
import org.onlinestore.orderservice.service.BasketProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketProductServiceImpl implements BasketProductService {

    private final InventoryGrpcClient inventoryGrpcClient;

    @Transactional
    @Override
    public BasketProduct createProductItem(CreateProduct createProduct) {
        log.info("trace_id = {}, запрос на склад, для проверки товара", createProduct.getTraceId());

        ProductGrpcResponse productGrpcResponse = inventoryGrpcClient.getProductByName(
                createProduct.getProductName(), createProduct.getTraceId().toString());

        if (productGrpcResponse.getQuantity() == 0) {
            log.warn("trace_id = {}, товар отсутствует на складе.", createProduct.getTraceId());
            throw new ProductNotFoundException(
                    String.format(ProductNotFoundException.PRODUCT_IS_OUT_OF_STOCK,productGrpcResponse.getName()));
        }

        if (createProduct.getQuantity() > productGrpcResponse.getQuantity()) {
            log.warn("trace_id = {}, недостаточное количество товара на складе.", createProduct.getTraceId());
            throw new ProductNotFoundException(
                    String.format(ProductNotFoundException.FEW_PRODUCT_IN_STOCK, productGrpcResponse.getQuantity()));
        }

        return BasketProduct.builder()
                .productId(UUID.fromString(productGrpcResponse.getId()))
                .name(productGrpcResponse.getName())
                .quantity(createProduct.getQuantity())
                .price(BigDecimal.valueOf(productGrpcResponse.getPrice()))
                .sale(productGrpcResponse.getSale())
                .totalSum(BigDecimal.valueOf(productGrpcResponse.getPrice())
                                .multiply(BigDecimal.valueOf(createProduct.getQuantity()))
                                .multiply(BigDecimal.valueOf(1 - productGrpcResponse.getSale() / 100.0)))
                .build();
    }

}
