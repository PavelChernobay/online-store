package org.onlinetstore.inventoryservice.unit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onlinetstore.inventoryservice.dto.CreateProduct;
import org.onlinetstore.inventoryservice.dto.ProductResponse;
import org.onlinetstore.inventoryservice.entity.Product;
import org.onlinetstore.inventoryservice.exception.ProductNotFoundException;
import org.onlinetstore.inventoryservice.generator.TestDataGenerator;
import org.onlinetstore.inventoryservice.mapper.ProductMapper;
import org.onlinetstore.inventoryservice.repository.ProductRepository;
import org.onlinetstore.inventoryservice.service.impl.ProductServiceImpl;
import org.onlinetstore.inventoryservice.validate.ProductValidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    private Product product;
    private ProductResponse productResponse;
    private CreateProduct createProduct;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductValidate productValidate;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        product = TestDataGenerator.generateProduct();
        productResponse = TestDataGenerator.generateProductResponse();
        createProduct = TestDataGenerator.generateCreateProduct();
    }

    @Test
    void addProductSuccess_whenProductIsNull() {
        when(productValidate.checkProductByName(any())).thenReturn(null);
        when(productMapper.productToProductResponse(any())).thenReturn(productResponse);

        ProductResponse result = productService.addProduct(createProduct);

        assertThat(result).isEqualTo(productResponse);

        verify(productValidate, times(1)).checkProductByName(any());
        verify(productMapper, times(1)).productToProductResponse(any());
    }

    @Test
    void addProductSuccess_whenProductIsNotNull() {
        when(productValidate.checkProductByName(any())).thenReturn(product);
        when(productMapper.productToProductResponse(any())).thenReturn(productResponse);

        ProductResponse result = productService.addProduct(createProduct);

        assertThat(result).isEqualTo(productResponse);

        verify(productValidate, times(1)).checkProductByName(any());
        verify(productMapper, times(1)).productToProductResponse(any());
    }

    @Test
    void getAllProducts() {
        PageImpl<Product> products = new PageImpl<>(List.of(product));
        PageImpl<ProductResponse> expected = new PageImpl<>(List.of(productResponse));

        when(productRepository.findAll(any(Pageable.class))).thenReturn(products);
        when(productMapper.productsToProductResponses(any())).thenReturn(expected.getContent());

        Page<ProductResponse> result = productService.getAllProducts(0, 10, "username", true);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(TestDataGenerator.PRODUCT_NAME, result.getContent().get(0).name());

        verify(productRepository, times(1)).findAll(any(Pageable.class));
        verify(productMapper, times(1)).productsToProductResponses(any());
    }

    @Test
    void getProductByIdSuccess() {
        when(productValidate.checkProductById(any())).thenReturn(product);
        when(productMapper.productToProductResponse(any())).thenReturn(productResponse);

        ProductResponse result = productService.getProductById(UUID.randomUUID());

        assertThat(result).isEqualTo(productResponse);

        verify(productValidate, times(1)).checkProductById(any());
        verify(productMapper, times(1)).productToProductResponse(any());
    }

    @Test
    void getProductById_shouldThrowException_whenProductNotFound() {
        doThrow(new ProductNotFoundException()).when(productValidate).checkProductById(any());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.getProductById(UUID.randomUUID()));

        assertEquals(TestDataGenerator.PRODUCT_N0T_FOUND, exception.getMessage());

        verify(productValidate, times(1)).checkProductById(any());
        verify(productMapper, never()).productToProductResponse(any());
    }

    @Test
    void deleteProductById() {
        productService.deleteProductById(UUID.randomUUID());

        verify(productRepository, times(1)).deleteById(any());
    }

    @Test
    void updateProductQuantities() {
        when(productValidate.checkProductByName(any())).thenReturn(product);

        productService.updateProductQuantities(TestDataGenerator.PRODUCT_NAME, 3);

        assertEquals(2, product.getQuantity());

        verify(productValidate, times(1)).checkProductByName(any());
    }

    @Test
    void updateProductQuantities_shouldThrowException_whenProductNotFound() {
        doThrow(new ProductNotFoundException()).when(productValidate).checkProductByName(any());

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> productService.updateProductQuantities(TestDataGenerator.PRODUCT_NAME, 3));

        assertEquals(TestDataGenerator.PRODUCT_N0T_FOUND, exception.getMessage());
        assertEquals(TestDataGenerator.QUANTITY, product.getQuantity());

        verify(productValidate, times(1)).checkProductByName(any());
    }

}