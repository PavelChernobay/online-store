package org.onlinestore.orderservice.validate;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.entity.Basket;
import org.onlinestore.orderservice.exception.BasketNotFoundException;
import org.onlinestore.orderservice.repository.BasketRepository;
import org.onlinestore.orderservice.service.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Компонент для валидации корзины пользователя.
 * Содержит методы для проверки существования корзины и получения корзины с товарами.
 */
@Component
@RequiredArgsConstructor
public class BasketValidate {

    private final BasketRepository basketRepository;
    private final UserService userService;

    /**
     * Проверяет существование корзины пользователя по его идентификатору и возвращает её вместе с товарами.
     *
     * @param userId идентификатор пользователя
     * @return объект {@link Basket} с товарами
     * @throws BasketNotFoundException если корзина не найдена
     */
    public Basket checkBasketWithProductItemsByUserId(UUID userId) {
        return basketRepository.findWithProductItemsByUserId(userService.getCurrentUser().getId())
                .orElseThrow(BasketNotFoundException::new);
    }

}