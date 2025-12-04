package com.hotel.rezervari.controller;

import com.hotel.rezervari.model.Camera;
import com.hotel.rezervari.model.Client;
import com.hotel.rezervari.model.Rezervare;
import com.hotel.rezervari.repository.CameraRepository;
import com.hotel.rezervari.repository.ClientRepository;
import com.hotel.rezervari.repository.RezervareRepository;
import com.hotel.rezervari.service.RezervareService;
import com.hotel.rezervari.service.TarifService;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/rezervari") //
public class RezervareController {

    private final RezervareService rezervareService;
    private final CameraRepository cameraRepository;

    public RezervareController(RezervareService rezervareService,
                              CameraRepository cameraRepository) {
        this.rezervareService = rezervareService;
        this.cameraRepository = cameraRepository;
    }

    @GetMapping("/adauga")
    public String arataFormularRezervare(Model model) {
        model.addAttribute("rezervare", new Rezervare());
        model.addAttribute("client", new Client());
        List<Camera> camereDisponibile = cameraRepository.findAll();
        model.addAttribute("camere", camereDisponibile);

        return "rezervari/adaugaRezervare";
    }

    @PostMapping("/salveaza")
    public String salveazaRezervare(@ModelAttribute("rezervare") Rezervare rezervare,
                                    @ModelAttribute("client") Client client,
                                    @RequestParam("cameraId") Long cameraId) {
        try {
            rezervareService.salveazaRezervare(rezervare, client, cameraId);

            return "redirect:/rezervari/succes";

        } catch (IllegalArgumentException e) {
            System.err.println("Eroare la salvarea rezervarii: " + e.getMessage());
            return "redirect:/rezervari/eroare?motiv=" + e.getMessage();

        } catch (Exception e) {
            System.err.println("Eroare necunoscuta: " + e.getMessage());
            return "redirect:/rezervari/eroare?motiv=Eroare internă! Vă rugăm contactați administratorul.";
        }
    }
    @GetMapping("/succes")
    public String rezervareSucces() {
        return "rezervari/rezervareSucces";
    }

    @GetMapping("/eroare")
    public String rezervareEroare(@RequestParam(value = "motiv", required = false) String motiv, Model model) {
        model.addAttribute("motiv", motiv != null ? motiv : "Eroare necunoscută la procesarea rezervării.");
        return "rezervari/rezervareEroare";
    }
}