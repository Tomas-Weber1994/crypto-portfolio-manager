package com.engeto.crypto_portfolio_manager.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @ExceptionHandler(CryptoNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCryptoNotFoundException(CryptoNotFoundException ex, HttpServletRequest request) {
        logError(ex);
        return createErrorResponse(request, ex.getMessage(), HttpStatus.NOT_FOUND, null);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFormatException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        logError(ex);
        Map<String, Object> additionalDetails = new HashMap<>();
        additionalDetails.put("exampleInput", CryptoJsonExampleFormat.CRYPTO_JSON_EXAMPLE_FORMAT);
        return createErrorResponse(request, "The provided data format is incorrect!", HttpStatus.BAD_REQUEST, additionalDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        logError(ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return createErrorResponse(request, "Data validation failed!", HttpStatus.BAD_REQUEST, Map.of("errors", errors));
    }

    private void logError(Throwable ex) {
        log.error("User input contains an error: {}", ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> createErrorResponse(HttpServletRequest request, String message, HttpStatus status, Map<String, Object> additionalDetails) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("message", message);
        errorDetails.put("dateTime", LocalDateTime.now().format(formatter));
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("path", request.getRequestURI());

        if (additionalDetails != null) {
            errorDetails.putAll(additionalDetails);
        }

        return new ResponseEntity<>(errorDetails, status);
    }
}
