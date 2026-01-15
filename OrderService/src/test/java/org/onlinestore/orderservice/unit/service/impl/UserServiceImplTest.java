package org.onlinestore.orderservice.unit.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.onlinestore.orderservice.dto.RegisterRequest;
import org.onlinestore.orderservice.dto.UserResponse;
import org.onlinestore.orderservice.entity.Role;
import org.onlinestore.orderservice.entity.User;
import org.onlinestore.orderservice.exception.PasswordsDoNotMatcherException;
import org.onlinestore.orderservice.exception.UserAlreadyExistsException;
import org.onlinestore.orderservice.exception.UserNotFoundException;
import org.onlinestore.orderservice.generator.TestDataGenerator;
import org.onlinestore.orderservice.mapper.UserMapper;
import org.onlinestore.orderservice.repository.UserRepository;
import org.onlinestore.orderservice.security.CustomUserDetails;
import org.onlinestore.orderservice.service.impl.UserServiceImpl;
import org.onlinestore.orderservice.validate.UserValidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private User user;
    private UserResponse userResponse;
    private RegisterRequest registerRequest;
    private RegisterRequest registerRequestPasswordDoNotMatch;
    private Page<User> userPage;
    private Page<UserResponse> userResponsePage;
    private UserResponse userResponseWithNewRole;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidate userValidate;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void setUp() {
        user = TestDataGenerator.generateUser();
        userResponse = TestDataGenerator.generateUserResponse();
        registerRequest = TestDataGenerator.generateRegisterRequest();
        registerRequestPasswordDoNotMatch = TestDataGenerator.generateRegisterRequestWithPasswordDoNotMatch();
        userPage = TestDataGenerator.generatePageUsers();
        userResponsePage = TestDataGenerator.generatePageUserResponse();
        userResponseWithNewRole = TestDataGenerator.generateUserResponseWithNewRole();
    }

    @Test
    void testCreateUserSuccess() {
        doNothing().when(userValidate).checkingAnExistingUser(anyString());
        when(passwordEncoder.encode(anyString())).thenReturn(TestDataGenerator.PASSWORD);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse result = userServiceImpl.createUser(registerRequest);

        assertThat(result).isEqualTo(userResponse);

        verify(userValidate, times(1)).checkingAnExistingUser(anyString());
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).userToUserResponse(any(User.class));
    }

    @Test
    void testCreateUser_shouldThrowException_whenUserAlreadyExists() {
        doThrow(new UserAlreadyExistsException())
                .when(userValidate).checkingAnExistingUser(anyString());

        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> userServiceImpl.createUser(registerRequest));

        assertEquals(TestDataGenerator.USER_ALREADY_EXISTS, exception.getMessage());

        verify(userValidate, times(1)).checkingAnExistingUser(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(userMapper, never()).userToUserResponse(any(User.class));
    }

    @Test
    void testCreateUser_shouldThrowException_whenPasswordDoNotMatch() {
        PasswordsDoNotMatcherException exception = assertThrows(PasswordsDoNotMatcherException.class,
                () -> userServiceImpl.createUser(registerRequestPasswordDoNotMatch));

        assertEquals(TestDataGenerator.PASSWORD_DO_NOT_MATCH, exception.getMessage());

        verify(userValidate, never()).checkingAnExistingUser(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
        verify(userMapper, never()).userToUserResponse(any(User.class));
    }

    @Test
    void testGetAllUsersSuccess() {
        when(userRepository.findAll(any(Pageable.class)))
                .thenReturn(userPage);
        when(userMapper.usersToUserResponses(anyList()))
                .thenReturn(userResponsePage.getContent());

        Page<UserResponse> result = userServiceImpl.getAllUsers(
                TestDataGenerator.PAGE, TestDataGenerator.SIZE, TestDataGenerator.SORT_BY, TestDataGenerator.ASCENDING);

        assertThat(result.getContent()).isEqualTo(userResponsePage.getContent());
        assertThat(result.getTotalElements()).isEqualTo(userResponsePage.getTotalElements());
        assertThat(result.getPageable().getPageNumber()).isEqualTo(TestDataGenerator.PAGE);
        assertThat(result.getPageable().getPageSize()).isEqualTo(TestDataGenerator.SIZE);

        verify(userRepository, times(1)).findAll(any(Pageable.class));
        verify(userMapper, times(1)).usersToUserResponses(anyList());
    }

    @Test
    void testUpdateRoleUserSuccess() {
        when(userValidate.checkUserExistsById(any(UUID.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userToUserResponse(any(User.class))).thenReturn(userResponseWithNewRole);

        UserResponse result = userServiceImpl.updateRoleUser(UUID.randomUUID(), Role.ROLE_ADMIN.name());

        assertEquals(Role.ROLE_ADMIN.name(), result.role());

        verify(userValidate, times(1)).checkUserExistsById(any(UUID.class));
        verify(userRepository, times(1)).save(any(User.class));
        verify(userMapper, times(1)).userToUserResponse(any(User.class));
    }

    @Test
    void testUpdateRoleUser_shouldThrowException_whenUserNotFoundById() {
        doThrow(new UserNotFoundException()).when(userValidate).checkUserExistsById(any(UUID.class));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userServiceImpl.updateRoleUser(UUID.randomUUID(), Role.ROLE_ADMIN.name()));

        assertEquals(TestDataGenerator.USER_NOT_FOUND, exception.getMessage());

        verify(userValidate, times(1)).checkUserExistsById(any(UUID.class));
        verify(userRepository, never()).save(any(User.class));
        verify(userMapper, never()).userToUserResponse(any(User.class));
    }

    @Test
    void testGetCurrentUserSuccess() {
        checkCurrentUser();

        User result = userServiceImpl.getCurrentUser();

        assertThat(result).isEqualTo(user);
    }

    @Test
    void testGetInfoCurrentUser() {
        checkCurrentUser();
        when(userMapper.userToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse result = userServiceImpl.getInfoCurrentUser();

        assertThat(result).isEqualTo(userResponse);

        verify(userMapper, times(1)).userToUserResponse(any(User.class));
    }

    @Test
    void testDeleteUserByIdSuccess() {
        when(userValidate.checkUserExistsById(any(UUID.class))).thenReturn(user);

        userServiceImpl.deleteUserById(UUID.randomUUID());

        verify(userValidate, times(1)).checkUserExistsById(any(UUID.class));
        verify(userRepository, times(1)).deleteById(any(UUID.class));
    }

    @Test
    void testDeleteUserById_shouldThrowException_whenUserNotFoundById() {
        doThrow(new UserNotFoundException()).when(userValidate).checkUserExistsById(any(UUID.class));

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userServiceImpl.deleteUserById(UUID.randomUUID()));

        assertEquals(TestDataGenerator.USER_NOT_FOUND, exception.getMessage());

        verify(userValidate, times(1)).checkUserExistsById(any(UUID.class));
        verify(userRepository, never()).deleteById(any(UUID.class));
    }

    @Test
    void testGetUserByUsernameSuccess() {
        when(userValidate.checkUserExistsByUsername(anyString())).thenReturn(user);
        when(userMapper.userToUserResponse(any(User.class))).thenReturn(userResponse);

        UserResponse result = userServiceImpl.getUserByUsername(TestDataGenerator.USERNAME);

        assertThat(result).isEqualTo(userResponse);

        verify(userValidate, times(1)).checkUserExistsByUsername(anyString());
        verify(userMapper, times(1)).userToUserResponse(any(User.class));
    }

    @Test
    void testGetUserByUsername_shouldThrowException_whenUserNotFoundByUsername() {
        doThrow(new UserNotFoundException()).when(userValidate).checkUserExistsByUsername(anyString());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userServiceImpl.getUserByUsername(TestDataGenerator.USERNAME));

        assertEquals(TestDataGenerator.USER_NOT_FOUND, exception.getMessage());

        verify(userValidate, times(1)).checkUserExistsByUsername(anyString());
        verify(userMapper, never()).userToUserResponse(any(User.class));
    }

    private void checkCurrentUser() {
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        when(authentication.getPrincipal()).thenReturn(customUserDetails);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

}