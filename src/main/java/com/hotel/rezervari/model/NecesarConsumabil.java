package com.hotel.rezervari.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "NECESAR_CONSUMABIL")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NecesarConsumabil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long necesarId;

    // Cheie Externă 1: Camera
    @ManyToOne
    @JoinColumn(name = "camera_id", nullable = false)
    private Camera camera;

    // Cheie Externă 2: Produsul Consumabil
    @ManyToOne
    @JoinColumn(name = "produs_id", nullable = false)
    private ConsumabilProdus produs;

    // Cantitatea zilnică necesară
    @Column(name = "cantitate_zilnica", nullable = false)
    private Double cantitateZilnica;
}
