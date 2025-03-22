package com.engeto.crypto_portfolio_manager.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CryptoNotFoundException.class)
    public ResponseEntity<String> handleCryptoNotFoundException(CryptoNotFoundException ex) {
        log.error("Chyba při hledání kryptoměny: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleException(Exception ex) {
//        log.error("Neznámá chyba: {}", ex.getMessage());
//        return new ResponseEntity<>("Došlo k neočekávané chybě. Zkuste to prosím znovu později.", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}
