package org.onlinestore.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST-контроллер для работы с пользователями.
 * <p>
 * Предоставляет эндпоинты для получения списка пользователей, обновления роли,
 * удаления пользователя и получения информации о текущем пользователе.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Возвращает страницу всех пользователей.
     *
     * @param page      номер страницы
     * @param size      количество пользователей на странице
     * @param sortBy    поле для сортировки
     * @param ascending направление сортировки
     * @return страница DTO {@link UserResponse} с пользователями
     */
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortBy, ascending));
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return {@link ResponseEntity} с HTTP статусом 204 (No Content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }

    /**
     * Обновляет роль пользователя по идентификатору.
     *
     * @param id   идентификатор пользователя
     * @param role новая роль пользователя
     * @return DTO {@link UserResponse} с обновленными данными пользователя
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable UUID id,
                                                       @RequestParam String role) {
        return ResponseEntity.ok(userService.updateRoleUser(id, role));
    }

    /**
     * Возвращает информацию о текущем пользователе.
     *
     * @return DTO {@link UserResponse} с информацией о текущем пользователе
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getInfoUser() {
        return ResponseEntity.ok(userService.getInfoCurrentUser());
    }

}
