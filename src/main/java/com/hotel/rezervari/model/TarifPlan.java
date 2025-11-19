package com.hotel.rezervari.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TARIF_PLAN")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tarifPlanId; // Primary Key

    @ManyToOne
    @JoinColumn(name = "tip_id", nullable = false)
    private TipCamera tipCamera;

    @Column(name = "nr_ocupanti", nullable = false)
    private Integer nrOccupanti;

    @Column(name = "pret_pe_noapte", nullable = false, precision = 10, scale = 2)
    private BigDecimal pretPeNoapte;

    @Column(name = "valabil_de_la")
    private LocalDate valabilDeLa;
}
