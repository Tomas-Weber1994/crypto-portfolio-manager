package com.engeto.crypto_portfolio_manager;

import com.engeto.crypto_portfolio_manager.model.Crypto;
import com.engeto.crypto_portfolio_manager.service.CryptoManager;

import java.math.BigDecimal;

public class MainApp {

    public static void main(String[] args) {
        Crypto crypto1 = new Crypto(1, "bitcoin", "BTC", new BigDecimal("1500"), 4.45);
        Crypto crypto2 = new Crypto(2, "ethereum", "ETH", new BigDecimal("400"), 13.4);
        CryptoManager cryptoManager = new CryptoManager();
        cryptoManager.addCrypto(crypto1);
        cryptoManager.addCrypto(crypto2);
        System.out.println(cryptoManager);
        System.out.println(crypto1.compareTo(crypto2));
        cryptoManager.sortByName();
        System.out.println(cryptoManager.getCryptoPortfolio());
    }
}
