package com.hotel.rezervari.service;

import com.hotel.rezervari.dto.ConsumDetaliu;
import com.hotel.rezervari.model.Cheltuiala;
import com.hotel.rezervari.model.NecesarConsumabil;
import com.hotel.rezervari.model.Rezervare;
import com.hotel.rezervari.repository.CheltuialaRepository;
import com.hotel.rezervari.repository.NecesarConsumabilRepository;
import com.hotel.rezervari.repository.RezervareRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinanteService {
    private final RezervareRepository rezervareRepository;
    private final NecesarConsumabilRepository  necesarConsumabilRepository;
    private final CheltuialaRepository cheltuialaRepository;

    public FinanteService(RezervareRepository rezervareRepository,  NecesarConsumabilRepository necesarConsumabilRepository,  CheltuialaRepository cheltuialaRepository) {
        this.rezervareRepository = rezervareRepository;
        this.necesarConsumabilRepository = necesarConsumabilRepository;
        this.cheltuialaRepository = cheltuialaRepository;
    }

    public List<Rezervare> getIncasariRezervariLuna(int an, int luna) {
        return rezervareRepository.findByMonthAndYearCheckin(an, luna);
    }

    public BigDecimal calculeazaTotalIncasari(List<Rezervare> rezervari) {
        return rezervari.stream()
                .map(Rezervare::getTotalPlataFinala)
                .reduce(BigDecimal.ZERO,  BigDecimal::add);
    }

    public List<ConsumDetaliu> getRaportConsumabile(int an, int luna) {
        List<Rezervare> rezervari = getIncasariRezervariLuna(an, luna);
        Map<String, ConsumDetaliu> hartaConsum = new HashMap<>();

        for(Rezervare rez : rezervari) {
            long nopti = ChronoUnit.DAYS.between(rez.getDataCheckin(), rez.getDataCheckout());
            List<NecesarConsumabil> necesarCam = necesarConsumabilRepository.findByCamera(rez.getCamera());

            for(NecesarConsumabil nc : necesarCam) {
                String nume = nc.getProdus().getNumeProdus();
                String unitateMasura = nc.getProdus().getUnitateMasura();
                double totalCant = nc.getCantitateZilnica() * nopti;
                BigDecimal pret = nc.getProdus().getPretUnitar();
                if(pret == null) pret = BigDecimal.ZERO;
                BigDecimal totalCost = pret.multiply(BigDecimal.valueOf(totalCant));

                if (hartaConsum.containsKey(nume)) {
                    ConsumDetaliu existent =  hartaConsum.get(nume);
                    existent.setCantitateTotala(existent.getCantitateTotala() + totalCant);
                    existent.setCostTotal(existent.getCostTotal().add(totalCost));
                } else {
                    hartaConsum.put(nume, new ConsumDetaliu(nume,totalCant,totalCost, unitateMasura));
                }
            }
        }
        return new ArrayList<>(hartaConsum.values());
    }

    public BigDecimal calculeazaTotalCheltuieliManuale(int an, int luna) {
        LocalDate start = LocalDate.of(an, luna, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Cheltuiala> cheltuieli = cheltuialaRepository.findByDataCheltuialaBetween(start, end);

        return cheltuieli.stream()
                .map(Cheltuiala::getSuma)
                .reduce(BigDecimal.ZERO,  BigDecimal::add);
    }

    public void salveazaCheltuiala(Cheltuiala cheltuiala) {
        cheltuialaRepository.save(cheltuiala);
    }

}
