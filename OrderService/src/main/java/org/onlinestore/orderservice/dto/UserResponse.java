package org.onlinestore.orderservice.dto;

import lombok.Builder;

import java.util.UUID;


/**
 * DTO для передачи информации о пользователе.
 * Используется в ответах контроллеров, связанных с пользователями,
 * например, {@link org.onlinestore.orderservice.controller.UserController}.
 *
 * @param id       уникальный идентификатор пользователя
 * @param username имя пользователя
 * @param email    email пользователя
 * @param role     роль пользователя (например, USER, ADMIN)
 */
@Builder
public record UserResponse(
        UUID id,
        String username,
        String email,
        String role
) {
}
