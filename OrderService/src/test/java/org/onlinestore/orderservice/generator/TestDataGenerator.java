package org.onlinestore.orderservice.generator;

import org.onlinestore.orderservice.dto.BasketProductResponse;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.dto.LoginRequest;
import org.onlinestore.orderservice.dto.OrderProductResponse;
import org.onlinestore.orderservice.dto.OrderResponse;
import org.onlinestore.orderservice.dto.RegisterRequest;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.entity.AnalyticsOutbox;
import org.onlinestore.orderservice.entity.Basket;
import org.onlinestore.orderservice.entity.BasketProduct;
import org.onlinestore.orderservice.entity.EventStatus;
import org.onlinestore.orderservice.entity.InventoryOutbox;
import org.onlinestore.orderservice.entity.Order;
import org.onlinestore.orderservice.entity.OrderProduct;
import org.onlinestore.orderservice.entity.Role;
import org.onlinestore.orderservice.entity.Status;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.grpc.ProductBatchGrpcResponse;
import org.onlinestore.orderservice.grpc.ProductGrpcResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class TestDataGenerator {

    public static final String USERNAME = "username";
    public static final String EMAIL = "user@email.ru";
    public static final String PASSWORD = "password";
    public static final String USER_ALREADY_EXISTS = "Пользователь с таким именем уже существует";
    public static final String PASSWORD_DO_NOT_MATCH = "Пароли не совпадают";
    public static final String REPEAT_PASSWORD_DO_NOT_MATCH = "passwordDoNotMatch";
    public static final String USER_NOT_FOUND = "Пользователь не существует";
    public static final int PAGE = 0;
    public static final int SIZE = 3;
    public static final String SORT_BY = "username";
    public static final boolean ASCENDING = true;
    public static final String TOKEN = "token";
    public static final String REFRESH_TOKEN = "refreshToken";
    public static final String INVALID_LOGIN_OR_PASSWORD = "Неверный логин или пароль";
    public static final String INVALID_REFRESH_TOKEN = "Не валидный токен, необходимо авторизоваться!";
    public static final String PRODUCT_NAME = "Яблоко";
    public static final int QUANTITY = 5;
    public static final BigDecimal TOTAL_SUM = BigDecimal.valueOf(50);
    public static final BigDecimal PRICE = BigDecimal.valueOf(10);
    public static final String PRODUCT_IS_OUT_OF_STOCK = "Продукт %s закончился";
    public static final String FEW_PRODUCT_IN_STOCK = "Недостаточно товара на складе. Остаток товара %d шт.";
    public static final String NOT_BASKET = "У Вас еще нет корзины, нужно добавить товар";
    public static final String PRODUCT_NOT_FOUND_TO_BASKET = "Товара в корзине нет";
    public static final long ID = 1L;

    public static User generateUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .username(USERNAME)
                .email(EMAIL)
                .role(Role.ROLE_USER)
                .build();
    }

    public static UserResponse generateUserResponse() {
        return UserResponse.builder()
                .id(UUID.randomUUID())
                .username(USERNAME)
                .email(EMAIL)
                .role(Role.ROLE_USER.name())
                .build();
    }

    public static RegisterRequest generateRegisterRequest() {
        return RegisterRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .repeatPassword(PASSWORD)
                .build();
    }

    public static RegisterRequest generateRegisterRequestWithPasswordDoNotMatch() {
        return RegisterRequest.builder()
                .username(TestDataGenerator.USERNAME)
                .email(TestDataGenerator.EMAIL)
                .password(TestDataGenerator.PASSWORD)
                .repeatPassword(REPEAT_PASSWORD_DO_NOT_MATCH)
                .build();
    }

    public static Page<User> generatePageUsers() {
        return new PageImpl<>(List.of(generateUser(), generateUser()));
    }

    public static Page<UserResponse> generatePageUserResponse() {
        return new PageImpl<>(List.of(generateUserResponse(), generateUserResponse()));
    }

    public static UserResponse generateUserResponseWithNewRole() {
        return UserResponse.builder()
                .role(Role.ROLE_ADMIN.name())
                .build();
    }

    public static LoginRequest generateLoginRequest() {
        return LoginRequest.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .build();
    }

    public static OrderProductResponse generateOrderProductResponse() {
        return OrderProductResponse.builder()
                .id(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .orderId(UUID.randomUUID())
                .name(PRODUCT_NAME)
                .quantity(QUANTITY)
                .price(PRICE)
                .totalSum(TOTAL_SUM)
                .build();
    }

    public static OrderProduct generateOrderProduct() {
        return OrderProduct.builder()
                .id(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .name(PRODUCT_NAME)
                .quantity(QUANTITY)
                .price(PRICE)
                .totalSum(TOTAL_SUM)
                .build();
    }

    public static CreateProduct generateCreateProduct() {
        return CreateProduct.builder()
                .productName(PRODUCT_NAME)
                .quantity(QUANTITY)
                .build();
    }

    public static BasketProduct generateBasketProduct() {
        return BasketProduct.builder()
                .id(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .name(PRODUCT_NAME)
                .quantity(QUANTITY)
                .price(PRICE)
                .totalSum(TOTAL_SUM)
                .build();
    }

    public static BasketProductResponse generateBasketProductResponse() {
        return  BasketProductResponse.builder()
                .id(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .basketId(UUID.randomUUID())
                .name(PRODUCT_NAME)
                .quantity(QUANTITY)
                .price(PRICE)
                .totalSum(TOTAL_SUM)
                .build();
    }

    public static ProductGrpcResponse generateProductGrpsResponse() {
        return ProductGrpcResponse.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setName(PRODUCT_NAME)
                .setQuantity(QUANTITY)
                .setPrice(PRICE.doubleValue())
                .build();
    }

    public static InventoryOutbox generateInventoryOutbox() {
        return InventoryOutbox.builder()
                .id(ID)
                .productName(PRODUCT_NAME)
                .quantity(QUANTITY)
                .eventStatus(EventStatus.NEW)
                .build();
    }

    public static Order generateOrder() {
        return Order.builder()
                .id(UUID.randomUUID())
                .status(Status.CREATED)
                .totalSum(TOTAL_SUM)
                .build();
    }

    public static OrderResponse generateOrderResponse() {
        return OrderResponse.builder()
                .id(UUID.randomUUID())
                .status(Status.CREATED.name())
                .totalSum(TOTAL_SUM)
                .build();
    }

    public static AnalyticsOutbox generateAnalyticsOutbox() {
        return AnalyticsOutbox.builder()
                .id(ID)
                .orderId(UUID.randomUUID())
                .productId(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .quantity(QUANTITY)
                .price(PRICE)
                .totalPrice(TOTAL_SUM)
                .eventStatus(EventStatus.NEW)
                .build();
    }

    public static BasketResponse generateBasketResponse() {
        return BasketResponse.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .totalSum(TOTAL_SUM)
                .build();
    }

    public static Basket generateBasket() {
        return Basket.builder()
                .id(UUID.randomUUID())
                .totalSum(TOTAL_SUM)
                .build();
    }

    public static ProductBatchGrpcResponse generateProductBatchGrpcResponse() {
        return ProductBatchGrpcResponse.newBuilder()
                .addProductResponse(ProductGrpcResponse.newBuilder()
                        .setName(PRODUCT_NAME)
                        .setQuantity(QUANTITY)
                        .build())
                .build();
    }

}
