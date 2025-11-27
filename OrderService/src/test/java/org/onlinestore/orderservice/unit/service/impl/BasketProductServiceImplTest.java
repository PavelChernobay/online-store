package org.onlinestore.orderservice.unit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.entity.BasketProduct;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.generator.TestDataGenerator;
import org.onlinestore.orderservice.grpc.ProductGrpcResponse;
import org.onlinestore.orderservice.grpc.client.InventoryGrpcClient;
import org.onlinestore.orderservice.service.impl.BasketProductServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasketProductServiceImplTest {

    private BasketProduct basketProduct;
    private ProductGrpcResponse productGrpcResponse;
    private CreateProduct createProduct;

    @Mock
    private InventoryGrpcClient inventoryGrpcClient;

    @InjectMocks
    private BasketProductServiceImpl basketProductService;

    @BeforeEach
    void setUp() {
        basketProduct = TestDataGenerator.generateBasketProduct();
        productGrpcResponse = TestDataGenerator.generateProductGrpsResponse();
        createProduct = TestDataGenerator.generateCreateProduct();
    }

    @Test
    void createProductItem() {
        when(inventoryGrpcClient.getProductByName(anyString(), anyString())).thenReturn(productGrpcResponse);

        BasketProduct result = basketProductService.createProductItem(createProduct);

        assertThat(result.getName()).isEqualTo(basketProduct.getName());
        assertThat(result.getQuantity()).isEqualTo(basketProduct.getQuantity());
        assertThat(result.getPrice().compareTo(basketProduct.getPrice())).isZero();
        assertThat(result.getTotalSum().compareTo(basketProduct.getTotalSum())).isZero();

        verify(inventoryGrpcClient, times(1)).getProductByName(anyString(), anyString());
    }

    @Test
    void testCreateProduct_shouldThrowException_whenQuantityEqualsZero() {
        ProductGrpcResponse grpcResponse = ProductGrpcResponse.newBuilder()
                .setName(TestDataGenerator.PRODUCT_NAME)
                .setQuantity(0)
                .build();
        when(inventoryGrpcClient.getProductByName(anyString(), anyString())).thenReturn(grpcResponse);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> basketProductService.createProductItem(createProduct));

        String expected = String.format(TestDataGenerator.PRODUCT_IS_OUT_OF_STOCK, TestDataGenerator.PRODUCT_NAME);

        assertEquals(expected, exception.getMessage());

        verify(inventoryGrpcClient, times(1)).getProductByName(anyString(), anyString());
    }

    @Test
    void testCreateProduct_shouldThrowException_whenFewProductInStock() {
        ProductGrpcResponse grpcResponse = ProductGrpcResponse.newBuilder()
                .setQuantity(3)
                .build();
        when(inventoryGrpcClient.getProductByName(anyString(), anyString())).thenReturn(grpcResponse);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> basketProductService.createProductItem(createProduct));

        String expected = String.format(TestDataGenerator.FEW_PRODUCT_IN_STOCK, grpcResponse.getQuantity());

        assertEquals(expected, exception.getMessage());

        verify(inventoryGrpcClient, times(1)).getProductByName(anyString(), anyString());
    }

}