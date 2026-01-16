package com.hotel.rezervari.controller;

import com.hotel.rezervari.dto.ConsumDetaliu;
import com.hotel.rezervari.model.Cheltuiala;
import com.hotel.rezervari.model.Rezervare;
import com.hotel.rezervari.service.FinanteService;
import lombok.AccessLevel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/finante")
public class FinanteController {
    private final FinanteService finanteService;

    public FinanteController(FinanteService finanteService) {
        this.finanteService = finanteService;
    }

    @GetMapping("/rapoarte")
    public String afiseazaRapoarteFinanciare(
            @RequestParam(name = "an", required = false) Integer an,
            @RequestParam(name = "luna", required = false) Integer luna,
            Model model) {

        LocalDate azi = LocalDate.now();

        int anCautat = (an == null) ? azi.getYear() : an;
        int lunaCautata =  (luna == null) ? azi.getMonthValue() : luna;

        List<Rezervare> incasari = finanteService.getIncasariRezervariLuna(anCautat, lunaCautata);
        BigDecimal total = finanteService.calculeazaTotalIncasari(incasari);

        List<ConsumDetaliu> detaliiConsum = finanteService.getRaportConsumabile(anCautat, lunaCautata);

        BigDecimal totalConsum = detaliiConsum.stream()
                .map(ConsumDetaliu::getCostTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<Integer, String> numeLuniMap = IntStream.rangeClosed(1, 12)
            .boxed()
            .collect(Collectors.toMap(
                    i -> i,
                    i -> Month.of(i).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ro"))
            ));

        BigDecimal totalCheltuieliManuale = finanteService.calculeazaTotalCheltuieliManuale(anCautat, lunaCautata);
        BigDecimal profitNetFinal = total.subtract(totalConsum).subtract(totalConsum).subtract(totalCheltuieliManuale);

        model.addAttribute("numeLuniMap", numeLuniMap);
        model.addAttribute("incasari", incasari);
        model.addAttribute("totalIncasari", total);
        model.addAttribute("anSelectat", anCautat);
        model.addAttribute("lunaSelectata", lunaCautata);
        model.addAttribute("detaliiConsum", detaliiConsum);
        model.addAttribute("totalConsum", totalConsum);
        model.addAttribute("totalCheltuieliManuale", totalCheltuieliManuale);
        model.addAttribute("profitNetFinal", profitNetFinal);

        return "finante/rapoarte_venituri";
    }

    @GetMapping("/cheltuieli/nou")
    public String afiseazaFormularCheltuiala(Model model) {
        Cheltuiala cheltuialaNoua = new Cheltuiala();
        cheltuialaNoua.setDataCheltuiala(LocalDate.now());

        model.addAttribute("cheltuiala", cheltuialaNoua);
        return "finante/form_cheltuiala";
    }

    @PostMapping("/cheltuieli/salveaza")
    public String salveazaCheltuiala(@ModelAttribute("cheltuiala") Cheltuiala cheltuiala) {
        finanteService.salveazaCheltuiala(cheltuiala);
        return "redirect:/finante/rapoarte";
    }
}
