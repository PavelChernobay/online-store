package org.onlinetstore.inventoryservice.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация Swagger/OpenAPI для документации REST API.
 * <p>
 * Настраивает схему безопасности для использования JWT-токенов.
 * Все эндпоинты защищены с использованием схемы "bearerAuth".
 * <p>
 * Аннотация {@link SecurityScheme} задаёт параметры для Swagger UI.
 */
@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    /**
     * Создаёт кастомный бин OpenAPI с настройкой схемы безопасности.
     * <p>
     * Добавляет SecurityRequirement и SecurityScheme для JWT,
     * чтобы Swagger UI мог использовать токены для аутентификации.
     *
     * @return объект {@link OpenAPI} с настройкой безопасности
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new io.swagger.v3.oas.models.security.SecurityScheme()
                                        .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
