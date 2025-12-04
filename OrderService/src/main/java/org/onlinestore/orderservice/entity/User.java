package org.onlinestore.orderservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Сущность пользователя в системе.
 * Содержит информацию о логине, пароле, email, роли пользователя и связанную корзину.
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Setter
@Getter
@Builder
@Entity(name = "users")
public class User {

    /** Уникальный идентификатор пользователя */
    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID id;

    /** Логин пользователя, уникальный и обязательный */
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    /** Пароль пользователя, обязательный */
    @Column(name = "password", nullable = false)
    private String password;

    /** Email пользователя, уникальный и обязательный */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /** Роль пользователя в системе (ROLE_USER, ROLE_ADMIN) */
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    /** Корзина пользователя (связь один к одному) */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Basket basket;
}