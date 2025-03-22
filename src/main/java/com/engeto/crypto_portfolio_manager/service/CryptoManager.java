package com.engeto.crypto_portfolio_manager.service;

import com.engeto.crypto_portfolio_manager.model.Crypto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CryptoManager {
    private final List<Crypto> cryptoPortfolio = new ArrayList<>();

    public void addCrypto(Crypto crypto) {
        cryptoPortfolio.add(crypto);
    }

    public List<Crypto> getCryptoPortfolio() {
        return new ArrayList<>(cryptoPortfolio);
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
}
