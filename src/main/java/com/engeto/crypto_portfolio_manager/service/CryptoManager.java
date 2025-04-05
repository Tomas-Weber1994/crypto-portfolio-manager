package com.engeto.crypto_portfolio_manager.service;

import com.engeto.crypto_portfolio_manager.exceptions.CryptoNotFoundException;
import com.engeto.crypto_portfolio_manager.exceptions.TooManyRequestsException;
import com.engeto.crypto_portfolio_manager.model.Crypto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class CryptoManager {
    @Autowired
    private CryptoValidationService cryptoValidationService;

    @Autowired
    private CryptoPriceService cryptoPriceService;

    private final List<Crypto> cryptoPortfolio = new ArrayList<>();

    public void addCrypto(Crypto crypto) throws CryptoNotFoundException, TooManyRequestsException {
        log.info("Trying to add new cryptocurrency: {} (ID: {}, Symbol: {})", crypto.getName(), crypto.getId(), crypto.getSymbol());
        isValidCrypto(crypto);
        updatePriceFromApi(crypto);
        Crypto existingCrypto = findCryptoBySymbol(crypto.getSymbol());
        if (existingCrypto != null) {
            existingCrypto.setQuantity(existingCrypto.getQuantity().add(crypto.getQuantity()));
            log.info("Updated quantity of existing cryptocurrency. Current quantity: {}", existingCrypto.getQuantity());
        } else {
            cryptoPortfolio.add(crypto);
            log.info("Cryptocurrency added successfully. Current portfolio size: {}", cryptoPortfolio.size());
        }
    }

    private void isValidCrypto(Crypto crypto) throws CryptoNotFoundException {
        String symbol = cryptoValidationService.getSupportedCryptos().get(crypto.getName().toLowerCase());
        if (symbol == null) {
            throw new CryptoNotFoundException("Cryptocurrency name '" + crypto.getName() + "' is not valid.");
        }

        if (!symbol.equals(crypto.getSymbol().toLowerCase())) {
            throw new CryptoNotFoundException("Cryptocurrency symbol '" + crypto.getSymbol() + "' does not match name '" + crypto.getName() + "'.");
        }
    }

    public void updateCrypto(Crypto newCrypto, int id) throws CryptoNotFoundException, TooManyRequestsException {
        Crypto cryptoToUpdate = findCryptoById(id);
        log.info("Updating cryptocurrency with ID {}: {}", id, cryptoToUpdate);
        cryptoToUpdate.setName(newCrypto.getName());
        cryptoToUpdate.setSymbol(newCrypto.getSymbol());
        cryptoToUpdate.setQuantity(newCrypto.getQuantity());
        updatePriceFromApi(newCrypto);
        log.info("Cryptocurrency with ID {} updated successfully.", id);
    }

    public void updatePriceFromApi(Crypto crypto) throws TooManyRequestsException  {
        BigDecimal currentPrice = cryptoPriceService.getPriceInCzk(crypto.getName().toLowerCase());
        crypto.setPrice(currentPrice);
    }

    @Scheduled(fixedRate = 30000)  // price update every 30 seconds
    public void updatePricesPeriodically() throws TooManyRequestsException {
        if (!cryptoPortfolio.isEmpty()) {
            for (Crypto crypto : cryptoPortfolio) {
                updatePriceFromApi(crypto);
            }
            log.info("Crypto prices automatically updated.");
        }
    }

    public Crypto findCryptoBySymbol(String symbol) {
        return this.getCryptoPortfolio()
                .stream()
                .filter(crypto -> crypto.getSymbol().equalsIgnoreCase(symbol))
                .findFirst()
                .orElse(null);
    }


    public List<Crypto> getCryptoPortfolio() {
        return new ArrayList<>(cryptoPortfolio);
    }

    public BigDecimal getPortfolioValue() {
        log.info("Calculating the total value of the portfolio.");
        BigDecimal portfolioValue = BigDecimal.valueOf(0);
        for (Crypto crypto : cryptoPortfolio) {
            BigDecimal cryptoValue = crypto.getPrice().multiply(crypto.getQuantity());
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
