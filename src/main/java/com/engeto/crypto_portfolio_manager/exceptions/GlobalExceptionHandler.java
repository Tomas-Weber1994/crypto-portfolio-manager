package com.engeto.crypto_portfolio_manager.exceptions;

import com.engeto.crypto_portfolio_manager.constants.CryptoJsonExampleFormat;
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

    @ExceptionHandler({CryptoNotFoundException.class, TooManyRequestsException.class, HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, Object>> handleException(Exception ex, HttpServletRequest request) {
        logError(ex);

        // Default error message and status
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = ex.getMessage();

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("dateTime", LocalDateTime.now().format(formatter));
        errorDetails.put("path", request.getRequestURI());

        switch (ex) {
            case CryptoNotFoundException cryptoNotFoundException -> status = HttpStatus.NOT_FOUND;
            case TooManyRequestsException tooManyRequestsException -> status = HttpStatus.TOO_MANY_REQUESTS;
            case HttpMessageNotReadableException httpMessageNotReadableException -> {
                status = HttpStatus.BAD_REQUEST;
                message = "The provided input is incorrect!";
                errorDetails.put("exampleInput", CryptoJsonExampleFormat.CRYPTO_JSON_EXAMPLE_FORMAT);
            }
            case MethodArgumentNotValidException methodArgumentNotValidException -> {
                status = HttpStatus.BAD_REQUEST;
                message = "Data validation failed!";
                Map<String, String> errors = new HashMap<>();
                methodArgumentNotValidException.getBindingResult().getFieldErrors()
                        .forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
                errorDetails.put("errors", errors);
            }
            default -> {
            }
        }

        errorDetails.put("message", message);
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());

        return new ResponseEntity<>(errorDetails, status);
    }

    private void logError(Throwable ex) {
        log.error("An error occurred: {}", ex.getMessage());
    }
}
