package org.onlinestore.orderservice.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.onlinestore.orderservice.security.CustomUserDetailsService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр, выполняемый один раз на каждый HTTP-запрос.
 * <p>
 * Отвечает за:
 * <ul>
 *     <li>извлечение JWT токена из заголовка Authorization;</li>
 *     <li>базовую проверку токена (формат, срок жизни);</li>
 *     <li>получение username из токена;</li>
 *     <li>загрузку пользователя через {@link CustomUserDetailsService};</li>
 *     <li>дополнительную валидацию — сверяет токен с конкретным пользователем;</li>
 *     <li>установку аутентификации в {@link SecurityContextHolder}.</li>
 * </ul>
 * <p>
 * Если токена нет или он невалиден — фильтр просто пропускает запрос дальше без аутентификации.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Основная логика фильтра, выполняемая на каждый запрос.
     * <p>
     * Алгоритм:
     * <ol>
     *     <li>Извлекаем токен из запроса.</li>
     *     <li>Проверяем валидность токена.</li>
     *     <li>Получаем username из токена.</li>
     *     <li>Загружаем данные пользователя.</li>
     *     <li>Проверяем токен относительно пользователя.</li>
     *     <li>Если всё корректно — устанавливаем Authentication.</li>
     * </ol>
     *
     * @param request     текущий HTTP-запрос
     * @param response    текущий HTTP-ответ
     * @param filterChain цепочка фильтров
     * @throws ServletException в случае ошибки сервлета
     * @throws IOException      в случае ошибки ввода-вывода
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        // Нет токена или не проходит базовую валидацию — продолжаем цепочку
        if (token == null || !jwtTokenProvider.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtTokenProvider.getUsernameFromToken(token);

        // Username отсутствует или пользователь уже аутентифицирован
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Загружаем пользователя из БД
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        // Дополнительная проверка токена по конкретному пользователю
        if (!jwtTokenProvider.validateTokenForUser(token, userDetails)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Устанавливаем Authentication в SecurityContext
        setSecurityContextHolder(request, userDetails);

        filterChain.doFilter(request, response);
    }

    /**
     * Создаёт объект {@link UsernamePasswordAuthenticationToken}
     * и помещает его в {@link SecurityContextHolder}.
     *
     * @param request     текущий HTTP-запрос
     * @param userDetails данные пользователя
     */
    private void setSecurityContextHolder(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * Извлекает JWT токен из заголовка Authorization.
     * <p>
     * Ожидаемый формат:
     * <pre>
     *     Authorization: Bearer {token}
     * </pre>
     *
     * @param request текущий HTTP-запрос
     * @return строка токена или null, если токен отсутствует
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }

        return null;
    }
}