package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.BasketResponse;
import org.onlinestore.orderservice.dto.CreateProduct;
import org.onlinestore.orderservice.dto.ProductItemResponse;
import org.onlinestore.orderservice.entity.Basket;
import org.onlinestore.orderservice.entity.ProductItem;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.exception.BasketNotFoundException;
import org.onlinestore.orderservice.exception.ProductNotFoundException;
import org.onlinestore.orderservice.mapper.BasketMapper;
import org.onlinestore.orderservice.mapper.ProductItemMapper;
import org.onlinestore.orderservice.repository.BasketRepository;
import org.onlinestore.orderservice.service.BasketService;
import org.onlinestore.orderservice.service.ProductItemService;
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
    private final ProductItemService productItemService;
    private final UserService userService;
    private final BasketMapper basketMapper;
    private final BasketValidate basketValidate;
    private final ProductItemMapper productItemMapper;

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

        ProductItem productItem = productItemService.createProductItem(createProduct);

        ProductItem existingProduct = basket.getProductItems().stream()
                .filter(item -> item.getName().equals(productItem.getName()))
                .findFirst()
                .orElse(null);

        if (existingProduct != null) {
            int newQuantity = existingProduct.getQuantity() + productItem.getQuantity();
            existingProduct.setQuantity(newQuantity);
            existingProduct.setPrice(productItem.getPrice());
            existingProduct.setSale(productItem.getSale());
            existingProduct.setTotalSum(productItem.getPrice()
                    .multiply(BigDecimal.valueOf(newQuantity))
                    .multiply(BigDecimal.valueOf(1 - productItem.getSale() / 100.0)));
        } else {
            productItem.setBasket(basket);
            basket.getProductItems().add(productItem);
        }

        BigDecimal totalSum = basket.getProductItems().stream()
                .map(ProductItem::getTotalSum)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        basket.setTotalSum(totalSum);

        return basketMapper.basketToBasketResponse(basketRepository.save(basket));
    }

    @Transactional
    @Override
    public BasketResponse deleteProductToBasket(UUID id) {
        Basket basket = basketValidate.checkBasketWithProductItemsByUserId(id);

        ProductItem removeProductItem = basket.getProductItems().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(
                        ProductNotFoundException.PRODUCT_NOT_FOUND_TO_BASKET));

        basket.setTotalSum(basket.getTotalSum().subtract(removeProductItem.getTotalSum()));
        basket.getProductItems().remove(removeProductItem);

        return basketMapper.basketToBasketResponse(basket);
    }

    @Override
    public List<ProductItemResponse> getAllProductsToBasket() {
        User user = userService.getCurrentUser();
        Basket basket = basketValidate.checkBasketWithProductItemsByUserId(user.getId());

        return productItemMapper.productItemsToProductItemResponses(basket.getProductItems());
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
        Basket basket = basketValidate.checkBasketWithProductItemsByUserId(user.getId());
        basket.getProductItems().forEach(productItem -> productItem.setBasket(null));
        basket.getProductItems().clear();
        basket.setTotalSum(BigDecimal.ZERO);
    }
}
