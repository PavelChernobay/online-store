package org.onlinetstore.inventoryservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Сущность продукта в базе данных.
 * Представляет продукт, который хранится на складе, с информацией о количестве, цене и возможной скидке.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity(name = "products")
public class Product {

    /** Уникальный идентификатор продукта */
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    /** Название продукта, должно быть уникальным и не пустым */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /** Количество продукта на складе */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    /** Цена продукта */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /** Скидка на продукт в процентах, если есть */
    @Column(name = "sale")
    private Integer sale;

}
