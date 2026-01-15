package org.onlinestore.orderservice.repository;

import org.onlinestore.orderservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с сущностью {@link User}.
 * Предоставляет стандартные методы CRUD через JpaRepository
 * и кастомный метод для поиска пользователя по имени.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Найти пользователя по имени.
     *
     * @param username имя пользователя для поиска
     * @return Optional, содержащий {@link User}, если пользователь найден,
     *         или пустой Optional, если пользователь не найден
     */
    Optional<User> findByUsername(String username);

}