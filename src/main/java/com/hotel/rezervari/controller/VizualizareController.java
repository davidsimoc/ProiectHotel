package com.hotel.rezervari.controller;

import com.hotel.rezervari.model.Rezervare;
import com.hotel.rezervari.model.Client;
import com.hotel.rezervari.service.VizualizareService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/camere")
public class VizualizareController {
    private final VizualizareService vizualizareService;

    public VizualizareController(VizualizareService vizualizareService) {
        this.vizualizareService = vizualizareService;
    }

    @GetMapping
    public String afiseazaStatusCazare(Model model) {
        List<Rezervare> ocupariAzi = vizualizareService.getCamereOcupateAzi();
        model.addAttribute("ocupariAzi", ocupariAzi);

        LocalDate today = LocalDate.now();

        Map<Integer, String> numeLuniMap = IntStream.rangeClosed(1, 12)
                .boxed()
                .collect(Collectors.toMap(
                        i -> i,
                        i -> Month.of(i).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ro"))
                ));
        model.addAttribute("numeLuniMap", numeLuniMap);

        String numeLuna = today.getMonth().getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ro"));
        model.addAttribute("numeLunaCurenta", numeLuna);
        model.addAttribute("lunaCurenta", today.getMonthValue());
        model.addAttribute("anCurent", today.getYear());

        model.addAttribute("rezervariLunare", vizualizareService.getRezervariByMonth(today.getYear(), today.getMonthValue()));

        return "vizualizare/status_cazare";
    }

    @GetMapping("/raport_lunar")
    @ResponseBody
    public List<Rezervare> afiseazaRaportLunar(@RequestParam("an") int an,
                                      @RequestParam("luna") int luna) {

        return vizualizareService.getRezervariByMonth(an, luna);
    }

    @GetMapping("/clienti_la_data")
    @ResponseBody
    public List<com.hotel.rezervari.model.Client> afiseazaClientiLaData(@RequestParam("data") java.time.LocalDate data) {
        return vizualizareService.getClientiLaData(data);
    }

}
