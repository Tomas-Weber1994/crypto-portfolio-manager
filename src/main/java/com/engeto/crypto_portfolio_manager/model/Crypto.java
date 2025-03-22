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

    @NotNull(message = "ID kryptoměny je povinné.")
    @Min(value = 1, message = "ID kryptoměny musí být větší než 0.")
    private Integer id;

    @NotNull(message = "Název kryptoměny je povinný.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Název kryptoměny musí obsahovat pouze písmena.")
    private String name;

    @NotNull(message = "Symbol kryptoměny je povinný.")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Symbol kryptoměny musí obsahovat pouze písmena.")
    private String symbol;

    @NotNull(message = "Cena kryptoměny je povinná.")
    @Min(value = 0, message = "Cena kryptoměny musí být větší nebo rovna 0.")
    private BigDecimal price;

    @Min(value = 0, message = "Množství kryptoměny musí být větší nebo rovno 0.")
    private Double quantity;

    @Override
    public int compareTo(Crypto other) {
        return this.name.compareTo(other.name);
    }
}
