package org.onlinestore.orderservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сущность корзины пользователя.
 * Содержит список товаров и суммарную стоимость.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
@Builder
@Entity(name = "baskets")
public class Basket {

    /** Уникальный идентификатор корзины */
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    /** Пользователь, которому принадлежит корзина */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /** Список товаров в корзине */
    @OneToMany(mappedBy = "basket", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BasketProduct> basketProducts = new ArrayList<>();

    /** Общая сумма корзины */
    @Column(name = "total_sum", nullable = false)
    @Builder.Default
    private BigDecimal totalSum = BigDecimal.ZERO;

}