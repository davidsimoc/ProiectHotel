package com.hotel.rezervari.controller;

import com.hotel.rezervari.model.Camera;
import com.hotel.rezervari.repository.CameraRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/camere")
public class CameraController {
    private final CameraRepository cameraRepository;

    public CameraController(CameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
    }

    @GetMapping("/disponibile")
    public List<Camera> getCamereDisponibile(
            @RequestParam("checkIn") LocalDate checkIn,
            @RequestParam("checkOut") LocalDate checkOut) {

        if (checkIn == null || checkOut == null || checkIn.isAfter(checkOut) || checkIn.isEqual(checkOut)) {
            return List.of();
        }

        return cameraRepository.findAvailableCamere(checkIn, checkOut);
    }
}
