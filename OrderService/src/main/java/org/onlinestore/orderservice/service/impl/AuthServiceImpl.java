package org.onlinestore.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.dto.AuthResponse;
import org.onlinestore.orderservice.dto.LoginRequest;
import org.onlinestore.orderservice.dto.RegisterRequest;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.exception.InvalidRefreshTokenException;
import org.onlinestore.orderservice.security.CustomUserDetails;
import org.onlinestore.orderservice.security.jwt.JwtTokenProvider;
import org.onlinestore.orderservice.service.AuthService;
import org.onlinestore.orderservice.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserResponse register(RegisterRequest registerRequest) {
        return userService.createUser(registerRequest);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.username(), loginRequest.password());

        CustomUserDetails customUserDetails = (CustomUserDetails) authenticationManager
                .authenticate(authToken).getPrincipal();
        User user = customUserDetails.getUser();

        String token = jwtTokenProvider.generateToken(user.getUsername(), user.getEmail(), user.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUsername());

        return new AuthResponse(token, refreshToken);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        UserResponse user = userService.getUserByUsername(username);
        String token = jwtTokenProvider.generateToken(username, user.email(), user.id());

        return new AuthResponse(token, refreshToken);
    }
}
