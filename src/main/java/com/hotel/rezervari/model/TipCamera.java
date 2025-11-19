package com.hotel.rezervari.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TIP_CAMERA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipCamera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tipId;

    @Column(name = "nume_tip", nullable = false, length = 50, unique = true)
    private String numeTip;
}
