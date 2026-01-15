package org.onlinestore.orderservice.unit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onlinestore.orderservice.dto.BasketProductResponse;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.entity.Basket;
import org.onlinestore.orderservice.entity.BasketProduct;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.exception.BasketNotFoundException;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.generator.TestDataGenerator;
import org.onlinestore.orderservice.mapper.BasketMapper;
import org.onlinestore.orderservice.mapper.BasketProductMapper;
import org.onlinestore.orderservice.repository.BasketRepository;
import org.onlinestore.orderservice.service.BasketProductService;
import org.onlinestore.orderservice.service.UserService;
import org.onlinestore.orderservice.service.impl.BasketServiceImpl;
import org.onlinestore.orderservice.validate.BasketValidate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BasketServiceImplTest {

    private User user;
    private Basket basket;
    private BasketProduct basketProduct;
    private BasketResponse basketResponse;
    private CreateProduct createProduct;
    private BasketProductResponse basketProductResponse;

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private BasketProductService basketProductService;

    @Mock
    private UserService userService;

    @Mock
    private BasketMapper basketMapper;

    @Mock
    private BasketValidate basketValidate;

    @Mock
    private BasketProductMapper basketProductMapper;

    @InjectMocks
    private BasketServiceImpl basketService;

    @BeforeEach
    void setUp() {
        user = TestDataGenerator.generateUser();
        basket = TestDataGenerator.generateBasket();
        basketProduct = TestDataGenerator.generateBasketProduct();
        basketResponse = TestDataGenerator.generateBasketResponse();
        createProduct = TestDataGenerator.generateCreateProduct();
        basketProductResponse = TestDataGenerator.generateBasketProductResponse();
    }

    @Test
    void addProductToBasket() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(basketRepository.findByUserId(any())).thenReturn(Optional.of(basket));
        when(basketProductService.createProductItem(any())).thenReturn(basketProduct);
        when(basketRepository.save(any())).thenReturn(basket);
        when(basketMapper.basketToBasketResponse(any())).thenReturn(basketResponse);

        BasketResponse result = basketService.addProductToBasket(createProduct);

        assertThat(result).isEqualTo(basketResponse);

        verify(userService, times(1)).getCurrentUser();
        verify(basketRepository, times(1)).findByUserId(any());
        verify(basketProductService, times(1)).createProductItem(any());
        verify(basketRepository, times(1)).save(any());
        verify(basketMapper, times(1)).basketToBasketResponse(any());

    }

    @Test
    void deleteProductToBasketSuccess() {
        Basket test = Basket.builder()
                .id(UUID.randomUUID())
                .totalSum(TestDataGenerator.TOTAL_SUM)
                .basketProducts(new ArrayList<>(List.of(basketProduct)))
                .build();

        when(basketValidate.checkBasketWithProductItemsByUserId(any())).thenReturn(test);
        when(basketMapper.basketToBasketResponse(any())).thenReturn(basketResponse);

        BasketResponse result = basketService.deleteProductToBasket(basketProduct.getId());

        assertThat(result).isEqualTo(basketResponse);

        verify(basketValidate, times(1)).checkBasketWithProductItemsByUserId(any());
        verify(basketMapper, times(1)).basketToBasketResponse(any());
    }

    @Test
    void deleteProductToBasket_shouldThrowException_whenNotBasket() {
        doThrow(new BasketNotFoundException()).when(basketValidate).checkBasketWithProductItemsByUserId(any());

        BasketNotFoundException exception = assertThrows(BasketNotFoundException.class,
                () -> basketService.deleteProductToBasket(basketProduct.getId()));

        assertEquals(TestDataGenerator.NOT_BASKET, exception.getMessage());

        verify(basketValidate, times(1)).checkBasketWithProductItemsByUserId(any());
        verify(basketMapper, never()).basketToBasketResponse(any());
    }

    @Test
    void deleteProductToBasket_shouldThrowException_whenProductNotFoundToBasket() {
        when(basketValidate.checkBasketWithProductItemsByUserId(any())).thenReturn(basket);

        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> basketService.deleteProductToBasket(basketProduct.getId()));

        assertEquals(TestDataGenerator.PRODUCT_NOT_FOUND_TO_BASKET, exception.getMessage());

        verify(basketValidate, times(1)).checkBasketWithProductItemsByUserId(any());
        verify(basketMapper, never()).basketToBasketResponse(any());
    }

    @Test
    void getAllProductsToBasketSuccess() {
        Basket testBasket = Basket.builder()
                .id(UUID.randomUUID())
                .basketProducts(new ArrayList<>(List.of(basketProduct))) // изменяемый список
                .build();
        List<BasketProductResponse> expected = List.of(basketProductResponse);

        when(userService.getCurrentUser()).thenReturn(user);
        when(basketValidate.checkBasketWithProductItemsByUserId(any())).thenReturn(testBasket);
        when(basketProductMapper.basketProductsToBasketProductResponses(any()))
                .thenReturn(expected);

        List<BasketProductResponse> result = basketService.getAllProductsToBasket();

        assertThat(result).isEqualTo(expected);

        verify(userService, times(1)).getCurrentUser();
        verify(basketValidate, times(1)).checkBasketWithProductItemsByUserId(any());
        verify(basketProductMapper, times(1)).basketProductsToBasketProductResponses(any());
    }

    @Test
    void getAllProductsToBasket_shouldThrowException_whenNotBasket() {
        when(userService.getCurrentUser()).thenReturn(user);
        doThrow(new BasketNotFoundException()).when(basketValidate).checkBasketWithProductItemsByUserId(any());

        BasketNotFoundException exception = assertThrows(BasketNotFoundException.class,
                () -> basketService.getAllProductsToBasket());

        assertEquals(TestDataGenerator.NOT_BASKET, exception.getMessage());

        verify(userService, times(1)).getCurrentUser();
        verify(basketValidate, times(1)).checkBasketWithProductItemsByUserId(any());
        verify(basketProductMapper, never()).basketProductsToBasketProductResponses(any());
    }

    @Test
    void getBasketCurrentUserSuccess() {
        when(userService.getCurrentUser()).thenReturn(user);
        when(basketRepository.findWithProductItemsByUserId(any())).thenReturn(Optional.of(basket));
        when(basketMapper.basketToBasketResponse(any())).thenReturn(basketResponse);

        BasketResponse result = basketService.getBasketCurrentUser();

        assertThat(result).isEqualTo(basketResponse);

        verify(userService, times(1)).getCurrentUser();
        verify(basketRepository, times(1)).findWithProductItemsByUserId(any());
        verify(basketMapper, times(1)).basketToBasketResponse(any());
    }

    @Test
    void getBasketCurrentUser_shouldThrowException_whenNotBasket() {
        when(userService.getCurrentUser()).thenReturn(user);
        doThrow(new BasketNotFoundException()).when(basketRepository).findWithProductItemsByUserId(any());

        BasketNotFoundException exception = assertThrows(BasketNotFoundException.class,
                () -> basketService.getBasketCurrentUser());

        assertEquals(TestDataGenerator.NOT_BASKET, exception.getMessage());

        verify(userService, times(1)).getCurrentUser();
        verify(basketRepository, times(1)).findWithProductItemsByUserId(any());
        verify(basketMapper, never()).basketToBasketResponse(any());
    }

    @Test
    void clearBasketSuccess() {
        when(userService.getCurrentUser()).thenReturn(user);

        basketService.clearBasket();

        verify(userService, times(1)).getCurrentUser();
        verify(basketRepository, times(1)).deleteByUserId(any());
    }
}