package org.onlinestore.orderservice.validate;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.exception.UserAlreadyExistsException;
import org.onlinestore.orderservice.exception.UserNotFoundException;
import org.onlinestore.orderservice.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Компонент для валидации пользователей.
 * Содержит методы для проверки существования пользователя по имени или идентификатору,
 * а также для проверки, что пользователь с таким именем ещё не зарегистрирован.
 */
@Component
@RequiredArgsConstructor
public class UserValidate {

    private final UserRepository userRepository;

    /**
     * Проверяет, что пользователь с указанным именем ещё не зарегистрирован.
     *
     * @param username имя пользователя
     * @throws UserAlreadyExistsException если пользователь с таким именем уже существует
     */
    public void checkingAnExistingUser(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });
    }

    /**
     * Проверяет существование пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект {@link User}, если пользователь найден
     * @throws UserNotFoundException если пользователь не найден
     */
    public User checkUserExistsById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    /**
     * Проверяет существование пользователя по его имени.
     *
     * @param username имя пользователя
     * @return объект {@link User}, если пользователь найден
     * @throws UserNotFoundException если пользователь не найден
     */
    public User checkUserExistsByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }
}