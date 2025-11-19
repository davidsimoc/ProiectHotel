package com.hotel.rezervari.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CAMERA_INVENTAR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CameraInventar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Cheie Primară

    // Cheie Externă 1: Camera
    @ManyToOne
    @JoinColumn(name = "camera_id", nullable = false)
    private Camera camera;

    // Cheie Externă 2: Articolul de Inventar
    @ManyToOne
    @JoinColumn(name = "mijloc_id", nullable = false)
    private InventarMijlocFix mijlocFix;

    // Detalii specifice inventarului camerei
    @Column(name = "cantitate", nullable = false)
    private Integer cantitate;
}
