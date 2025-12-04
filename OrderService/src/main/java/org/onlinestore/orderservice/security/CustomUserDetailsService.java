package org.onlinestore.orderservice.security;

import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.repository.UserRepository;
import org.onlinestore.orderservice.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link UserDetailsService} для загрузки пользователя
 * в процессе аутентификации Spring Security.
 * <p>
 * Выполняет поиск пользователя по username через {@link UserRepository}.
 * Если пользователь не найден — выбрасывается {@link UsernameNotFoundException}.
 * Возвращает объект {@link CustomUserDetails}, содержащий данные пользователя.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Загружает пользователя по его имени.
     *
     * @param username имя пользователя
     * @return объект {@link CustomUserDetails} с данными пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     *
     * @see UserRepository#findByUsername(String)
     * @see CustomUserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new CustomUserDetails(user);
    }
}