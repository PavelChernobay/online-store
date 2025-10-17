package org.onlinestore.orderservice.validate;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.entity.Basket;
import org.onlinestore.orderservice.exception.BasketNotFoundException;
import org.onlinestore.orderservice.repository.BasketRepository;
import org.onlinestore.orderservice.service.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BasketValidate {

    private final BasketRepository basketRepository;
    private final UserService userService;

    public Basket checkBasketWithProductItemsByUserId(UUID userId) {
        return basketRepository.findWithProductItemsByUserId(userService.getCurrentUser().getId())
                .orElseThrow(BasketNotFoundException::new);
    }

}
