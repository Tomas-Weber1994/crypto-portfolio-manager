package com.engeto.crypto_portfolio_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Crypto implements Comparable<Crypto> {

    private int id;
    private String name;
    private String symbol;
    private BigDecimal price;
    private double quantity;

    @Override
    public int compareTo(Crypto other) {
        return this.name.compareTo(other.name);
    }
}
