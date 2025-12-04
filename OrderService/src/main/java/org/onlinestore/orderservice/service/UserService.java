package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.RegisterRequest;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.entity.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Сервис для работы с пользователями.
 * Содержит методы для создания пользователей, управления ролями,
 * получения информации о текущем пользователе и управления списком пользователей.
 */
public interface UserService {

    /**
     * Создает нового пользователя на основе данных регистрации.
     *
     * @param registerRequest DTO {@link RegisterRequest} с данными пользователя
     * @return DTO {@link UserResponse} с информацией о созданном пользователе
     */
    UserResponse createUser(RegisterRequest registerRequest);

    /**
     * Получает страницу всех пользователей с пагинацией и сортировкой.
     *
     * @param page      номер страницы
     * @param size      количество пользователей на странице
     * @param sortBy    поле для сортировки
     * @param ascending направление сортировки
     * @return страница DTO {@link UserResponse} с пользователями
     */
    Page<UserResponse> getAllUsers(int page, int size, String sortBy, boolean ascending);

    /**
     * Обновляет роль пользователя по его идентификатору.
     *
     * @param id   идентификатор пользователя
     * @param role новая роль пользователя
     * @return DTO {@link UserResponse} с обновленной информацией о пользователе
     */
    UserResponse updateRoleUser(UUID id, String role);

    /**
     * Получает сущность текущего пользователя.
     *
     * @return объект {@link User} текущего пользователя
     */
    User getCurrentUser();

    /**
     * Получает информацию о текущем пользователе.
     *
     * @return DTO {@link UserResponse} с информацией о текущем пользователе
     */
    UserResponse getInfoCurrentUser();

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     */
    void deleteUserById(UUID id);

    /**
     * Получает пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return DTO {@link UserResponse} с информацией о найденном пользователе
     */
    UserResponse getUserByUsername(String username);

}