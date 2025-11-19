package com.hotel.rezervari.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "REZERVARE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rezervare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rezervareId;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // --- Relația cu Camera (N:1) ---
    @ManyToOne
    @JoinColumn(name = "camera_id", nullable = false)
    private Camera camera;

    @Column(name = "data_checkin", nullable = false)
    private LocalDate dataCheckin;

    @Column(name = "data_checkout", nullable = false)
    private LocalDate dataCheckout;

    @Column(name = "nr_persoane", nullable = false)
    private Integer nrPersoane;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "PENDING";

    // --- Logica Financiară (Incasări) ---

    @Column(name = "suma_cazare_initiala", nullable = false, precision = 10, scale = 2)
    private BigDecimal sumaCazareInitiala;

    @Column(name = "avans_achitat", precision = 10, scale = 2)
    private BigDecimal avansAchitat = BigDecimal.ZERO;

    @Column(name = "total_plata_finala", precision = 10, scale = 2)
    private BigDecimal totalPlataFinala;

    @Column(name = "reducere_aplicata")
    private Boolean reducereAplicata = false;
}
