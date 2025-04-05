package com.engeto.crypto_portfolio_manager.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Crypto implements Comparable<Crypto> {

    @NotNull(message = "Crypto ID is required.")
    @Min(value = 1, message = "Crypto ID must be greater than 0.")
    private Integer id;

    @NotNull(message = "Crypto name is required.")
    private String name;

    @NotNull(message = "Crypto symbol is required.")
    private String symbol;

    // Price is being ignored even if is in user´s input -> should be always fetched from CoinGecko
    private BigDecimal price;

    @Min(value = 0, message = "Crypto quantity must be greater than or equal to 0.")
    private BigDecimal quantity;

    @Override
    public int compareTo(Crypto other) {
        return this.name.compareTo(other.name);
    }
}
