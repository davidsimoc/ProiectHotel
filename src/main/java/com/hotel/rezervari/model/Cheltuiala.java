package com.hotel.rezervari.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CHELTUIALA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cheltuiala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cheltuialaId; // Primary Key

    @Column(name = "data_cheltuiala", nullable = false)
    private LocalDate dataCheltuiala;

    @Column(name = "tip_cheltuiala", nullable = false, length = 50)
    private String tipCheltuiala; // Ex: APROVIZIONARE_CONSUMABILE, SALARII, UTILITATI

    @Column(name = "suma", nullable = false, precision = 10, scale = 2)
    private BigDecimal suma;

    @Column(name = "descriere", length = 255)
    private String descriere;
}
