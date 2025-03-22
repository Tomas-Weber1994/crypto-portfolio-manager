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
        cryptoPortfolio.add(crypto);
    }

    public void updateCrypto(Crypto newCrypto, int id) throws CryptoNotFoundException {
        Crypto cryptoToUpdate = findCryptoById(id);
        cryptoToUpdate.setName(newCrypto.getName());
        cryptoToUpdate.setSymbol(newCrypto.getSymbol());
        cryptoToUpdate.setPrice(newCrypto.getPrice());
        cryptoToUpdate.setQuantity(newCrypto.getQuantity());
    }

    public List<Crypto> getCryptoPortfolio() {
        return new ArrayList<>(cryptoPortfolio);
    }

    public BigDecimal getPortfolioValue() {
        BigDecimal portfolioValue = BigDecimal.valueOf(0);
        for (Crypto crypto : cryptoPortfolio) {
            BigDecimal cryptoValue = crypto.getPrice().multiply(BigDecimal.valueOf(crypto.getQuantity()));
            portfolioValue = portfolioValue.add(cryptoValue);
        }
        return portfolioValue;
    }


    public void sortByName() {
        Collections.sort(cryptoPortfolio);
    }

    public void sortByPrice() {
        cryptoPortfolio.sort(Comparator.comparing(Crypto::getPrice));
    }

    public void sortByQuantity() {
        cryptoPortfolio.sort(Comparator.comparing(Crypto::getQuantity));
    }

    public Crypto findCryptoById(int id) throws CryptoNotFoundException {
        return this.getCryptoPortfolio()
                .stream()
                .filter(crypto -> crypto.getId() == id)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Kryptoměna s ID {} nebyla nalezena.", id);
                    return new CryptoNotFoundException("Kryptoměna s ID " + id + " nebyla nalezena.");
                });
    }
}
