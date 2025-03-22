package com.engeto.crypto_portfolio_manager.controller;

import com.engeto.crypto_portfolio_manager.exceptions.CryptoNotFoundException;
import com.engeto.crypto_portfolio_manager.model.Crypto;
import com.engeto.crypto_portfolio_manager.service.CryptoManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CryptoController {
    private final CryptoManager cryptoManager = new CryptoManager();

    @PostMapping("/cryptos")
    public ResponseEntity<String> addCrypto(@RequestBody Crypto crypto) {
        cryptoManager.addCrypto(crypto);
        return new ResponseEntity<>("Crypto has been successfully added!", HttpStatus.CREATED);
    }

    @GetMapping("/cryptos")
    public ResponseEntity<?> retrieveCrypto(@RequestParam(required = false) String sort) {
        if (sort != null) {
            String sortKey = sort.toLowerCase();
            switch (sortKey) {
                case "price" -> cryptoManager.sortByPrice();
                case "name" -> cryptoManager.sortByName();
                case "quantity" -> cryptoManager.sortByQuantity();
                default -> {
                    return ResponseEntity
                            .badRequest()
                            .body("Chyba! " + sortKey + " je neplatný způsob řazení kryptoměn!");
                }
            }
        }
        return new ResponseEntity<>(cryptoManager.getCryptoPortfolio(), HttpStatus.OK);
    }

    @GetMapping("/cryptos/{id}")
    public ResponseEntity<Crypto> getCryptoDetails(@PathVariable int id) throws CryptoNotFoundException {
        return new ResponseEntity<>(cryptoManager.findCryptoById(id), HttpStatus.OK);
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Controller is working!";
    }
}
