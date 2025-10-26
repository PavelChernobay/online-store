package org.onlinetstore.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        int statusCode,
        String message,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
        LocalDateTime timestamp
) {
}
