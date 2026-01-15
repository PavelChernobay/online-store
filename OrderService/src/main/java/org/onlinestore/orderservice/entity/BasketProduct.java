package org.onlinestore.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Сущность товара в корзине пользователя.
 * Связывает корзину и конкретный продукт с указанием количества, цены и скидки.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
@Builder
@Entity(name = "basket_products")
public class BasketProduct {

    /** Уникальный идентификатор товара в корзине */
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    /** Идентификатор продукта */
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    /** Корзина, к которой относится данный товар */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    /** Название продукта */
    @Column(name = "name", nullable = false)
    private String name;

    /** Количество продукта в корзине */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /** Цена за единицу продукта */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /** Скидка на продукт в процентах */
    @Column(name = "sale")
    private Integer sale;

    /** Общая сумма за данный товар с учетом количества и скидки */
    @Column(name = "total_sum", nullable = false)
    @Builder.Default
    private BigDecimal totalSum = BigDecimal.ZERO;
}