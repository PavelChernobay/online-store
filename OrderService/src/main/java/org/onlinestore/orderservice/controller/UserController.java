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

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        return ResponseEntity.ok(userService.getAllUsers(page, size, sortBy, ascending));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserRole(@PathVariable UUID id,
                                                       @RequestParam String role) {
        return ResponseEntity.ok(userService.updateRoleUser(id, role));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getInfoUser() {
        return ResponseEntity.ok(userService.getInfoCurrentUser());
    }

}
