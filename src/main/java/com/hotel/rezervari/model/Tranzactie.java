package com.hotel.rezervari.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "TRANZACTIE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tranzactie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tranzactieId;

    @ManyToOne
    @JoinColumn(name = "rezervare_id", nullable = false)
    private Rezervare rezervare;

    @Column(name = "tip_tranzactie", nullable = false, length = 20)
    private String tipTranzactie;

    @Column(name = "suma", nullable = false, precision = 10, scale = 2)
    private BigDecimal suma;

    @Column(name = "data_tranzactie", nullable = false)
    private LocalDateTime dataTranzactie = LocalDateTime.now();

    @Column(name = "chitanta_emisa")
    private Boolean chitantaEmisa = false;

    @Column(name = "descriere")
    private String descriere;
}
