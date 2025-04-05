package com.engeto.crypto_portfolio_manager.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CryptoValidationService {

    private static final String COINGECKO_API_URL = "https://api.coingecko.com/api/v3/coins/list";
    private final RestTemplate restTemplate = new RestTemplate();
    @Getter
    private final Map<String, String> supportedCryptos = new HashMap<>(); // Mapování názvu na symbol

    @PostConstruct
    public void loadSupportedCryptocurrencies() {
        try {
            List<Map<String, Object>> response = restTemplate.getForObject(COINGECKO_API_URL, List.class);

            if (response != null) {
                for (Map<String, Object> coin : response) {
                    String name = (String) coin.get("id");
                    String symbol = (String) coin.get("symbol");
                    if (name != null && symbol != null) {
                        supportedCryptos.put(name.toLowerCase(), symbol.toLowerCase());
                    }
                }
                log.info("Successfully loaded {} supported cryptocurrencies.", supportedCryptos.size());
            } else {
                log.warn("No response from the CoinGecko API.");
            }
        } catch (Exception e) {
            log.error("Error loading supported cryptocurrencies: {}", e.getMessage());
        }
    }
}
