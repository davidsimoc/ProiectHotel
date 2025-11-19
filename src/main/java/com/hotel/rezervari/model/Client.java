package com.hotel.rezervari.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLIENT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId; // Primary Key

    @Column(name = "nume", nullable = false, length = 50)
    private String nume;

    @Column(name = "prenume", nullable = false, length = 50)
    private String prenume;

    @Column(name = "cnp", unique = true, length = 13)
    private String cnp;

    @Column(name = "serie_buletin", unique = true, length = 10)
    private String serieBuletin;

    @Column(name = "adresa", length = 255)
    private String adresa;
}
