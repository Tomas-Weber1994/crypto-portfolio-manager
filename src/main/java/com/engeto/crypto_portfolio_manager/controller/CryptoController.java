package com.engeto.crypto_portfolio_manager.controller;

import com.engeto.crypto_portfolio_manager.model.Crypto;
import com.engeto.crypto_portfolio_manager.service.CryptoManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
//@RequestMapping("/app")
public class CryptoController {
    private final CryptoManager cryptoManager = new CryptoManager();

    @PostMapping("/cryptos")
    public ResponseEntity<String> addCrypto(@RequestBody Crypto crypto) {
        cryptoManager.addCrypto(crypto);
        return new ResponseEntity<>("Crypto added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Controller is working!";
    }
}

