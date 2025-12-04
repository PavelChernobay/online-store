package org.onlinetstore.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * DTO для передачи информации об ошибке в ответе API.
 * Используется при обработке исключений и возврате клиенту HTTP-ответа с ошибкой.
 *
 * @param statusCode HTTP-статус ошибки (например, 400, 404, 500)
 * @param message    сообщение с описанием причины ошибки
 * @param timestamp  дата и время возникновения ошибки в формате "dd.MM.yyyy HH:mm:ss"
 */
@Builder
public record ErrorResponse(
        int statusCode,
        String message,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
        LocalDateTime timestamp
) {
        private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";
}
