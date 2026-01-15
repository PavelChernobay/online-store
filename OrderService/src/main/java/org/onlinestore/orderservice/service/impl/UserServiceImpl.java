package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.RegisterRequest;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.entity.Role;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.exception.PasswordsDoNotMatcherException;
import org.onlinestore.orderservice.mapper.UserMapper;
import org.onlinestore.orderservice.repository.UserRepository;
import org.onlinestore.orderservice.security.CustomUserDetails;
import org.onlinestore.orderservice.service.UserService;
import org.onlinestore.orderservice.validate.UserValidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidate userValidate;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public UserResponse createUser(RegisterRequest registerRequest) {
        if (!registerRequest.password().equals(registerRequest.repeatPassword())) {
            throw new PasswordsDoNotMatcherException();
        }

        userValidate.checkingAnExistingUser(registerRequest.username());

        User user = User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .email(registerRequest.email())
                .role(Role.ROLE_USER)
                .build();

        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    public Page<UserResponse> getAllUsers(int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(page, size, sort);

        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserResponse> userResponses = userMapper.usersToUserResponses(usersPage.getContent());

        return new PageImpl<>(userResponses, pageable, usersPage.getTotalElements());
    }

    @Transactional
    @Override
    public UserResponse updateRoleUser(UUID id, String role) {
        User user = userValidate.checkUserExistsById(id);

        user.setRole(Role.valueOf(role));

        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    public User getCurrentUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userDetails.getUser();
    }

    @Override
    public UserResponse getInfoCurrentUser() {
        return userMapper.userToUserResponse(getCurrentUser());
    }

    @Override
    public void deleteUserById(UUID id) {
        userValidate.checkUserExistsById(id);
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        return userMapper.userToUserResponse(userValidate.checkUserExistsByUsername(username));
    }
}
