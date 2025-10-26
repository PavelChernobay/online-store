package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.dto.BasketProductResponse;
import org.onlinestore.orderservice.entity.Basket;
import org.onlinestore.orderservice.entity.BasketProduct;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.exception.BasketNotFoundException;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.mapper.BasketMapper;
import org.onlinestore.orderservice.mapper.BasketProductMapper;
import org.onlinestore.orderservice.repository.BasketRepository;
import org.onlinestore.orderservice.service.BasketService;
import org.onlinestore.orderservice.service.BasketProductService;
import org.onlinestore.orderservice.service.UserService;
import org.onlinestore.orderservice.validate.BasketValidate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketProductService basketProductService;
    private final UserService userService;
    private final BasketMapper basketMapper;
    private final BasketValidate basketValidate;
    private final BasketProductMapper basketProductMapper;

    @Transactional
    @Override
    public BasketResponse addProductToBasket(CreateProduct createProduct) {
        User user = userService.getCurrentUser();
        Basket basket = basketRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Basket newBasket = new Basket();
                    newBasket.setUser(user);
                    return newBasket;
                });

        BasketProduct basketProduct = basketProductService.createProductItem(createProduct);

        BasketProduct existingProduct = basket.getBasketProducts().stream()
                .filter(item -> item.getName().equals(basketProduct.getName()))
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {
            int newQuantity = existingProduct.getQuantity() + basketProduct.getQuantity();
            existingProduct.setQuantity(newQuantity);
            existingProduct.setPrice(basketProduct.getPrice());
            existingProduct.setSale(basketProduct.getSale());
            existingProduct.setTotalSum(basketProduct.getPrice()
                    .multiply(BigDecimal.valueOf(newQuantity))
                    .multiply(BigDecimal.valueOf(1 - basketProduct.getSale() / 100.0)));
        } else {
            basketProduct.setBasket(basket);
            basket.getBasketProducts().add(basketProduct);
        }

        BigDecimal totalSum = basket.getBasketProducts().stream()
                .map(BasketProduct::getTotalSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        basket.setTotalSum(totalSum);

        return basketMapper.basketToBasketResponse(basketRepository.save(basket));
    }

    @Transactional
    @Override
    public BasketResponse deleteProductToBasket(UUID id) {
        Basket basket = basketValidate.checkBasketWithProductItemsByUserId(id);

        BasketProduct removeBasketProduct = basket.getBasketProducts().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(
                        ProductNotFoundException.PRODUCT_NOT_FOUND_TO_BASKET));

        basket.setTotalSum(basket.getTotalSum().subtract(removeBasketProduct.getTotalSum()));
        basket.getBasketProducts().remove(removeBasketProduct);

        return basketMapper.basketToBasketResponse(basket);
    }

    @Override
    public List<BasketProductResponse> getAllProductsToBasket() {
        User user = userService.getCurrentUser();
        Basket basket = basketValidate.checkBasketWithProductItemsByUserId(user.getId());

        return basketProductMapper.basketProductsToBasketProductResponses(basket.getBasketProducts());
    }

    @Override
    public BasketResponse getBasketCurrentUser() {
        return basketMapper.basketToBasketResponse(
                basketRepository.findWithProductItemsByUserId(userService.getCurrentUser().getId())
                        .orElseThrow(BasketNotFoundException::new));
    }

    @Transactional
    @Override
    public void clearBasket() {
        User user = userService.getCurrentUser();
        basketRepository.deleteByUserId(user.getId());
    }
}
