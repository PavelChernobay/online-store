package org.onlinestore.orderservice.validate;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.exception.UserAlreadyExistsException;
import org.onlinestore.orderservice.exception.UserNotFoundException;
import org.onlinestore.orderservice.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserValidate {

    private final UserRepository userRepository;

    public void checkingAnExistingUser(String username) {
        userRepository.findByUsername(username)
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException();
                });

    }

    public User checkUserExistsById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User checkUserExistsByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }
}
