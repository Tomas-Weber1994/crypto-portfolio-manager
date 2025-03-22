package com.engeto.crypto_portfolio_manager.service;

import com.engeto.crypto_portfolio_manager.exceptions.CryptoNotFoundException;
import com.engeto.crypto_portfolio_manager.model.Crypto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class CryptoManager {
    private final List<Crypto> cryptoPortfolio = new ArrayList<>();

    public void addCrypto(Crypto crypto) {
        log.info("Adding new cryptocurrency: {} (ID: {}, Symbol: {})", crypto.getName(), crypto.getId(), crypto.getSymbol());
        cryptoPortfolio.add(crypto);
        log.info("Cryptocurrency added successfully. Current portfolio size: {}", cryptoPortfolio.size());
    }

    public void updateCrypto(Crypto newCrypto, int id) throws CryptoNotFoundException {
        Crypto cryptoToUpdate = findCryptoById(id);
        log.info("Updating cryptocurrency with ID {}: {}", id, cryptoToUpdate);
        cryptoToUpdate.setName(newCrypto.getName());
        cryptoToUpdate.setSymbol(newCrypto.getSymbol());
        cryptoToUpdate.setPrice(newCrypto.getPrice());
        cryptoToUpdate.setQuantity(newCrypto.getQuantity());
        log.info("Cryptocurrency with ID {} updated successfully.", id);
    }

    public List<Crypto> getCryptoPortfolio() {
        return new ArrayList<>(cryptoPortfolio);
    }

    public BigDecimal getPortfolioValue() {
        log.info("Calculating the total value of the portfolio.");
        BigDecimal portfolioValue = BigDecimal.valueOf(0);
        for (Crypto crypto : cryptoPortfolio) {
            BigDecimal cryptoValue = crypto.getPrice().multiply(BigDecimal.valueOf(crypto.getQuantity()));
            portfolioValue = portfolioValue.add(cryptoValue);
        }
        log.info("Total portfolio value calculated: {}", portfolioValue);
        return portfolioValue;
    }

    public void sortByName() {
        log.info("Sorting portfolio by cryptocurrency name.");
        Collections.sort(cryptoPortfolio);
        log.info("Portfolio sorted by name.");
    }

    public void sortByPrice() {
        log.info("Sorting portfolio by cryptocurrency price.");
        cryptoPortfolio.sort(Comparator.comparing(Crypto::getPrice));
        log.info("Portfolio sorted by price.");
    }

    public void sortByQuantity() {
        log.info("Sorting portfolio by cryptocurrency quantity.");
        cryptoPortfolio.sort(Comparator.comparing(Crypto::getQuantity));
        log.info("Portfolio sorted by quantity.");
    }

    public Crypto findCryptoById(int id) throws CryptoNotFoundException {
        return this.getCryptoPortfolio()
                .stream()
                .filter(crypto -> crypto.getId() == id)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Cryptocurrency with ID {} not found.", id);
                    return new CryptoNotFoundException("Cryptocurrency with ID " + id + " not found.");
                });
    }
}
