package com.hotel.rezervari.controller;

import com.hotel.rezervari.model.Camera;
import com.hotel.rezervari.repository.CameraRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/inventar")
public class InventarController {
    private final CameraRepository cameraRepository;

    public InventarController(CameraRepository cameraRepository) {
        this.cameraRepository = cameraRepository;
    }

    @GetMapping
    public String listaGenerala(Model model) {
        model.addAttribute("camere", cameraRepository.findAll());
        return "inventar/lista_inventar";
    }

    @GetMapping("/detalii/{id}")
    public String detaliiCamera(@PathVariable Long id, Model model) {
        Camera camera = cameraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Camera nu exista"));
        model.addAttribute("camera", camera);
        return "inventar/detalii_camera";
    }
}
