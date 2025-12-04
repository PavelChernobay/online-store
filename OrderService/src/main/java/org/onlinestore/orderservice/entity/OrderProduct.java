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
 * Сущность продукта в заказе.
 * Содержит информацию о конкретном продукте, его количестве, цене и сумме в рамках заказа.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
@Builder
@Entity(name = "order_products")
public class OrderProduct {

    /** Уникальный идентификатор продукта в заказе */
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    /** Идентификатор продукта */
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    /** Заказ, к которому относится продукт */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    /** Название продукта */
    @Column(name = "name", nullable = false)
    private String name;

    /** Количество продукта в заказе */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /** Цена за единицу продукта */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /** Скидка на продукт (в процентах) */
    @Column(name = "sale")
    private Integer sale;

    /** Общая сумма за данный продукт (price * quantity - скидка) */
    @Column(name = "total_sum", nullable = false)
    @Builder.Default
    private BigDecimal totalSum = BigDecimal.ZERO;

}