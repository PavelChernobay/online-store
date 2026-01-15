package org.onlinetstore.inventoryservice.exception;

import org.onlinetstore.inventoryservice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Глобальный обработчик исключений для InventoryService.
 * Перехватывает исключения и возвращает структурированные ответы в формате {@link ErrorResponse}.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключение {@link ProductNotFoundException}, возникающее при попытке получить
     * или удалить несуществующий продукт.
     *
     * @param ex исключение {@link ProductNotFoundException}
     * @return {@link ResponseEntity} с {@link ErrorResponse}, содержащий код статуса 404 и сообщение об ошибке
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
