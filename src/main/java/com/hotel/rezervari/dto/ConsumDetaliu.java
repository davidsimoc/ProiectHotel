package com.hotel.rezervari.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class ConsumDetaliu {
    @Getter
    private String numeProdus;
    @Getter
    @Setter
    private double cantitateTotala;
    @Getter
    @Setter
    private BigDecimal costTotal;
    @Getter
    @Setter
    private String unitate;

    public ConsumDetaliu(String numeProdus, double cantitateTotala, BigDecimal costTotal, String unitate) {
        this.numeProdus = numeProdus;
        this.cantitateTotala = cantitateTotala;
        this.costTotal = costTotal;
        this.unitate = unitate;
    }
}
