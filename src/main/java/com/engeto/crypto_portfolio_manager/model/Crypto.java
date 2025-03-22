package com.engeto.crypto_portfolio_manager.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[A-Za-z]+$", message = "Crypto name must contain only letters.")
    private String name;

    @NotNull(message = "Crypto symbol is required.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Crypto symbol must contain only letters.")
    private String symbol;

    @NotNull(message = "Crypto price is required.")
    @Min(value = 0, message = "Crypto price must be greater than or equal to 0.")
    private BigDecimal price;

    @Min(value = 0, message = "Crypto quantity must be greater than or equal to 0.")
    private Double quantity;

    @Override
    public int compareTo(Crypto other) {
        return this.name.compareTo(other.name);
    }
}
