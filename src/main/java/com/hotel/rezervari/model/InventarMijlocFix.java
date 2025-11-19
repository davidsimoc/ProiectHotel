package com.hotel.rezervari.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVENTAR_MIJLOC_FIX")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarMijlocFix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mijlocId;

    @Column(name = "nume_articol", nullable = false, unique = true)
    private String numeArticol;
}
