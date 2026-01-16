package com.hotel.rezervari.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "CAMERA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Camera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cameraId;

    @Column(name = "numar_camera", unique = true, nullable = false)
    private String numarCamera;

    @Column(name = "nr_paturi", nullable = false)
    private Integer nrPaturi;

    @ManyToOne // O cameră are UN singur tip, dar un tip poate avea Multe camere
    @JoinColumn(name = "tip_id", nullable = false)
    private TipCamera tipCamera;

    @Column(name = "este_ocupata", nullable = false)
    private Boolean esteOcupata = false;

    @Column(name = "data_ocuparii")
    private LocalDate dataOcuparii;

    @Column(name = "data_eliberarii")
    private LocalDate dataEliberarii;

    @Column(name = "data_urmatoarei_rezervari")
    private LocalDate dataUrmatoareiRezervari;

    @OneToMany(mappedBy = "camera", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<CameraInventar> inventar;

    @OneToMany(mappedBy = "camera", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<NecesarConsumabil> necesarConsumabile;
}
