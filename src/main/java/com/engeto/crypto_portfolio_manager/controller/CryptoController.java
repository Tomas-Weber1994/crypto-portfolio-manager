package com.engeto.crypto_portfolio_manager.controller;

import com.engeto.crypto_portfolio_manager.exceptions.CryptoNotFoundException;
import com.engeto.crypto_portfolio_manager.model.Crypto;
import com.engeto.crypto_portfolio_manager.service.CryptoManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/cryptos")
public class CryptoController {
    @Autowired
    private CryptoManager cryptoManager;

    @PostMapping
    public ResponseEntity<String> addCrypto(@Valid @RequestBody Crypto crypto) {
        cryptoManager.addCrypto(crypto);
        return new ResponseEntity<>("Crypto has been successfully added!", HttpStatus.CREATED);
    }

    @GetMapping
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
                            .body("Error! " + sortKey + " is an invalid sorting method for cryptocurrencies!");
                }
            }
        }
        return new ResponseEntity<>(cryptoManager.getCryptoPortfolio(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Crypto> getCryptoDetails(@PathVariable Integer id) throws CryptoNotFoundException {
        return new ResponseEntity<>(cryptoManager.findCryptoById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> getCryptoDetails(@Valid @RequestBody Crypto updatedCrypto,
                                                   @PathVariable Integer id) throws CryptoNotFoundException {
        if (!updatedCrypto.getId().equals(id)) {
            return new ResponseEntity<>("ID in the request body does not match the ID in the URL!", HttpStatus.BAD_REQUEST);
        }
        cryptoManager.updateCrypto(updatedCrypto, id);
        return new ResponseEntity<>("Crypto has been successfully updated!", HttpStatus.OK);
    }

    @GetMapping("/portfolio-value")
    public ResponseEntity<String> getCryptoDetails() {
        BigDecimal portfolioValue = cryptoManager.getPortfolioValue();
        return new ResponseEntity<>("The total value of the cryptocurrency portfolio is: " + portfolioValue + " CZK", HttpStatus.OK);
    }
}
