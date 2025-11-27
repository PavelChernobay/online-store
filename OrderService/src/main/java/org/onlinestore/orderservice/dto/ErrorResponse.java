package org.onlinestore.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        int statusCode,
        String message,
        @JsonFormat(pattern = DATE_TIME_PATTERN)
        LocalDateTime timestamp
) {
        private static final String DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm:ss";
}
