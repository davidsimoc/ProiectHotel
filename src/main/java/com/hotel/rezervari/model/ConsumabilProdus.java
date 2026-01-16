package com.hotel.rezervari.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "CONSUMABIL_PRODUS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsumabilProdus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long produsId;

    @Column(name = "nume_produs", nullable = false, unique = true)
    private String numeProdus;

    @Column(name = "unitate_masura")
    private String unitateMasura;

    @Column(name = "pret_unitar")
    private BigDecimal pretUnitar;
}
