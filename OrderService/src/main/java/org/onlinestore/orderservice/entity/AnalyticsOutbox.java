package org.onlinestore.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Сущность для хранения событий аналитики заказов в таблице Outbox.
 * Используется для последующей публикации в Kafka.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Builder
@Entity(name = "analytics_outbox")
public class AnalyticsOutbox {

    /** Уникальный идентификатор записи */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /** Идентификатор заказа */
    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    /** Идентификатор продукта */
    @Column(name = "product_id", nullable = false)
    private UUID productId;

    /** Идентификатор пользователя, сделавшего заказ */
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    /** Количество товара в заказе */
    @Column(name = "quantity", nullable = false)
    private int quantity;

    /** Цена одного товара */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /** Скидка на товар в процентах */
    @Column(name = "sale", nullable = false)
    private int sale;

    /** Итоговая стоимость товара с учетом количества и скидки */
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    /** Статус события (например, NEW, SENT) */
    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", nullable = false)
    private EventStatus eventStatus;

    /** Дата и время создания записи */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /** Дата и время последнего обновления записи */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
