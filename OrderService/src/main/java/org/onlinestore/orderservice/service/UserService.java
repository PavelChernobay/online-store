package org.onlinestore.orderservice.service;

import org.onlinestore.orderservice.dto.RegisterRequest;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.entity.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface UserService {

    UserResponse createUser(RegisterRequest registerRequest);

    Page<UserResponse> getAllUsers(int page, int size, String sortBy, boolean ascending);

    UserResponse updateRoleUser(UUID id, String role);

    User getCurrentUser();

    UserResponse getInfoCurrentUser();

    void deleteUserById(UUID id);

    UserResponse getUserByUsername(String username);

}
