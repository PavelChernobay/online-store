package org.onlinestore.orderservice.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Кастомная реализация {@link UserDetails} для интеграции сущности {@link User}
 * со Spring Security.
 * <p>
 * Оборачивает модель пользователя и предоставляет данные,
 * необходимые для аутентификации и авторизации.
 */
@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {

    /** Сущность пользователя, чьи данные используются в системе безопасности. */
    private final User user;

    /**
     * Возвращает список ролей пользователя.
     *
     * @return коллекция прав доступа с ролью пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
    }

    /**
     * @return пароль пользователя
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * @return имя пользователя (username)
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Указывает, что срок действия аккаунта не истёк.
     * Используется реализация по умолчанию — всегда true.
     *
     * @return true — аккаунт не просрочен
     */
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    /**
     * Указывает, что аккаунт не заблокирован.
     * Используется дефолтное значение — true.
     *
     * @return true — аккаунт не заблокирован
     */
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    /**
     * Указывает, что пароль не просрочен.
     * Используется дефолтное значение — true.
     *
     * @return true — пароль действителен
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    /**
     * Указывает, что пользователь включён.
     * Использует значение по умолчанию — true.
     *
     * @return true — пользователь активен
     */
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}