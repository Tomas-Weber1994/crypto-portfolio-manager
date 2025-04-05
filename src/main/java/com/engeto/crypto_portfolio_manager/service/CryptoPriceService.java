package com.engeto.crypto_portfolio_manager.service;

import com.engeto.crypto_portfolio_manager.exceptions.TooManyRequestsException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class CryptoPriceService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String COINGECKO_API_URL = "https://api.coingecko.com/api/v3/simple/price";
    private static final Logger logger = Logger.getLogger(CryptoPriceService.class.getName());

    public BigDecimal getPriceInCzk(String cryptoName) throws TooManyRequestsException {
        String coinGeckoCryptoName = cryptoName.toLowerCase();
        String url = COINGECKO_API_URL + "?ids=" + coinGeckoCryptoName + "&vs_currencies=czk";

        try {
            Map<String, Map<String, Object>> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey(coinGeckoCryptoName)) {
                Map<String, Object> priceMap = response.get(coinGeckoCryptoName);
                Object czkPrice = priceMap.get("czk");
                if (czkPrice != null) {
                    return new BigDecimal(czkPrice.toString());
                }
            }

            throw new RuntimeException("Failed to fetch CZK price for: " + cryptoName);

        } catch (HttpClientErrorException.TooManyRequests e) {
            throw new TooManyRequestsException(
                    "Too many requests to CoinGecko API. Please consider using a premium plan or wait a minute."
            );
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Error fetching data from CoinGecko API: " + e.getMessage(), e);
        }
    }
}
