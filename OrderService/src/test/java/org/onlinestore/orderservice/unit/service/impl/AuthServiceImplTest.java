package org.onlinestore.orderservice.unit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onlinestore.orderservice.dto.AuthResponse;
import org.onlinestore.orderservice.dto.LoginRequest;
import org.onlinestore.orderservice.dto.RegisterRequest;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.exception.InvalidRefreshTokenException;
import org.onlinestore.orderservice.generator.TestDataGenerator;
import org.onlinestore.orderservice.security.CustomUserDetails;
import org.onlinestore.orderservice.security.jwt.JwtTokenProvider;
import org.onlinestore.orderservice.service.UserService;
import org.onlinestore.orderservice.service.impl.AuthServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    private LoginRequest loginRequest;
    private UserResponse userResponse;
    private RegisterRequest registerRequest;
    private CustomUserDetails customUserDetails;

    @Mock
    private UserService userService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        loginRequest = TestDataGenerator.generateLoginRequest();
        userResponse = TestDataGenerator.generateUserResponse();
        registerRequest = TestDataGenerator.generateRegisterRequest();
        customUserDetails = new CustomUserDetails(TestDataGenerator.generateUser());
    }

    @Test
    void testRegisterSuccess() {
        when(userService.createUser(any(RegisterRequest.class))).thenReturn(userResponse);

        UserResponse result = authService.register(registerRequest);

        assertThat(result).isEqualTo(userResponse);

        verify(userService, times(1)).createUser(any(RegisterRequest.class));
    }

    @Test
    void testLoginSuccess() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(jwtTokenProvider.generateToken(anyString(), anyString(), any(UUID.class)))
                .thenReturn(TestDataGenerator.TOKEN);
        when(jwtTokenProvider.generateRefreshToken(anyString())).thenReturn(TestDataGenerator.REFRESH_TOKEN);

        AuthResponse result = authService.login(loginRequest);

        assertEquals(TestDataGenerator.TOKEN, result.token());
        assertEquals(TestDataGenerator.REFRESH_TOKEN, result.refreshToken());

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authentication, times(1)).getPrincipal();
        verify(jwtTokenProvider, times(1))
                .generateToken(anyString(), anyString(), any(UUID.class));
        verify(jwtTokenProvider, times(1)).generateRefreshToken(anyString());
    }

    @Test
    void testLogin_shouldThrowException_whenInvalidData() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException(TestDataGenerator.INVALID_LOGIN_OR_PASSWORD));

        BadCredentialsException exception = assertThrows(BadCredentialsException.class,
                () -> authService.login(loginRequest));

        assertEquals(TestDataGenerator.INVALID_LOGIN_OR_PASSWORD, exception.getMessage());

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authentication, never()).getPrincipal();
        verify(jwtTokenProvider, never())
                .generateToken(anyString(), anyString(), any(UUID.class));
        verify(jwtTokenProvider, never()).generateRefreshToken(anyString());
    }

    @Test
    void testLogin_shouldThrowException_whenPrincipalIsNull() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(null);

        assertThrows(NullPointerException.class, () -> authService.login(loginRequest));

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authentication, times(1)).getPrincipal();
        verify(jwtTokenProvider, never())
                .generateToken(anyString(), anyString(), any(UUID.class));
        verify(jwtTokenProvider, never()).generateRefreshToken(anyString());
    }

    @Test
    void testLogin_shouldThrowException_whenJwtProviderThrows() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(jwtTokenProvider.generateToken(anyString(), anyString(), any(UUID.class)))
                .thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> authService.login(loginRequest));

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(authentication, times(1)).getPrincipal();
        verify(jwtTokenProvider, times(1))
                .generateToken(anyString(), anyString(), any(UUID.class));
        verify(jwtTokenProvider, never()).generateRefreshToken(anyString());
    }

    @Test
    void testRefreshSuccess() {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.getUsernameFromToken(anyString())).thenReturn(TestDataGenerator.USERNAME);
        when(userService.getUserByUsername(anyString())).thenReturn(userResponse);
        when(jwtTokenProvider.generateToken(anyString(), anyString(), any(UUID.class)))
                .thenReturn(TestDataGenerator.TOKEN);

        AuthResponse result = authService.refresh(TestDataGenerator.REFRESH_TOKEN);

        assertEquals(TestDataGenerator.TOKEN, result.token());
        assertEquals(TestDataGenerator.REFRESH_TOKEN, result.refreshToken());

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(jwtTokenProvider, times(1)).getUsernameFromToken(anyString());
        verify(userService, times(1)).getUserByUsername(anyString());
        verify(jwtTokenProvider, times(1))
                .generateToken(anyString(), anyString(), any(UUID.class));
    }

    @Test
    void testRefresh_shouldThrowException_whenInvalidRefreshToken() {
        when(jwtTokenProvider.validateToken(anyString())).thenThrow(new InvalidRefreshTokenException());

        InvalidRefreshTokenException exception = assertThrows(InvalidRefreshTokenException.class,
                () -> authService.refresh(TestDataGenerator.REFRESH_TOKEN));

        assertEquals(TestDataGenerator.INVALID_REFRESH_TOKEN, exception.getMessage());

        verify(jwtTokenProvider, times(1)).validateToken(anyString());
        verify(jwtTokenProvider, never()).getUsernameFromToken(anyString());
        verify(userService, never()).getUserByUsername(anyString());
        verify(jwtTokenProvider, never())
                .generateToken(anyString(), anyString(), any(UUID.class));
    }

}