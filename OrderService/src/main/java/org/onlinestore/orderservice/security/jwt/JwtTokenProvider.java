package org.onlinestore.orderservice.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * Провайдер для генерации и валидации JWT токенов.
 * <p>
 * Используется для создания access/refresh токенов,
 * получения данных из токена и проверки его валидности.
 */
@Component
@Slf4j
public class JwtTokenProvider {

    /** Секретный ключ для подписи JWT. */
    private final SecretKey key;

    /** Время жизни access токена в миллисекундах. */
    private final long jwtExpirationInMs;

    /** Время жизни refresh токена в миллисекундах. */
    private final long jwtRefreshExpirationInMs;

    /**
     * Создаёт компонент для работы с JWT.
     *
     * @param secret секрет для подписи JWT
     * @param jwtExpirationInMs время жизни access токена в миллисекундах
     * @param jwtRefreshExpirationInMs время жизни refresh токена в миллисекундах
     */
    public JwtTokenProvider(@Value("${spring.security.jwt.secret}") String secret,
                            @Value("${spring.security.jwt.expiration-ms}") long jwtExpirationInMs,
                            @Value("${spring.security.jwt.refresh-expiration-ms}") long jwtRefreshExpirationInMs) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.jwtExpirationInMs = jwtExpirationInMs;
        this.jwtRefreshExpirationInMs = jwtRefreshExpirationInMs;
    }

    /**
     * Генерирует access токен для пользователя.
     *
     * @param username имя пользователя
     * @param email email пользователя
     * @param id уникальный идентификатор пользователя
     * @return подписанный JWT access токен
     */
    public String generateToken(String username, String email, UUID id) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .subject(username)
                .claim("email", email)
                .claim("id", id)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    /**
     * Генерирует refresh токен.
     *
     * @param username имя пользователя
     * @return JWT refresh токен
     */
    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtRefreshExpirationInMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    /**
     * Извлекает username из JWT-токена.
     *
     * @param token JWT токен
     * @return имя пользователя
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    /**
     * Проверяет валидность токена.
     *
     * @param token JWT токен
     * @return true, если токен валидный
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (Exception ex) {
            log.error("Invalid JWT token", ex);
            return false;
        }
    }

    /**
     * Проверяет, что токен принадлежит указанному пользователю и является валидным.
     *
     * @param token JWT токен
     * @param userDetails данные пользователя
     * @return true, если токен корректен и принадлежит пользователю
     */
    public boolean validateTokenForUser(String token, UserDetails userDetails) {
        String usernameFromToken = getUsernameFromToken(token);
        return usernameFromToken.equals(userDetails.getUsername()) && validateToken(token);
    }

}