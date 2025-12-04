package com.hotel.rezervari.service;

import com.hotel.rezervari.model.TarifPlan;
import com.hotel.rezervari.repository.TarifPlanRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class TarifService {
    private final TarifPlanRepository tarifPlanRepository;
    public TarifService(TarifPlanRepository tarifPlanRepository) {
        this.tarifPlanRepository = tarifPlanRepository;
    }

    /**
     * Calculează tariful de bază al cazării (fără discount), bazat pe planul tarifar.
     * @param tipCameraId ID-ul Tipului de Cameră
     * @param nrPersoane Numarul de ocupanti
     * @param checkIn Data inceperii sederii
     * @param checkOut Data incheierii sederii
     * @return Suma initiala (Pret Pe Noapte * Numar Zile)
     */
    public BigDecimal calculeazaTarifInitial(Long tipCameraId, int nrPersoane, LocalDate checkIn, LocalDate checkOut) {
        Optional<TarifPlan> tarifOpt = tarifPlanRepository.findByTipCamera_TipIdAndNrOccupanti(tipCameraId, nrPersoane);
        if (tarifOpt.isEmpty()) {
            throw new IllegalArgumentException("Nu s-a găsit plan tarifar pentru Tipul de cameră specificat și " + nrPersoane + " ocupanți.");
        }
        BigDecimal pretPeNoapte = tarifOpt.get().getPretPeNoapte();
        long durataZile = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (durataZile <= 0) {
            throw new IllegalArgumentException("Durata șederii trebuie să fie de minim o zi.");
        }
        return pretPeNoapte.multiply(BigDecimal.valueOf(durataZile));
    }

    /**
     * Aplică reducerea de 5% dacă clientul depășește 10 zile.
     * @param sumaInitiala Tariful fara discount
     * @param checkIn Data Check-in
     * @param checkOut Data Check-out
     * @return Totalul final după aplicarea reducerilor
     */
    public BigDecimal calculeazaTotalFinal(BigDecimal sumaInitiala, LocalDate checkIn, LocalDate checkOut) {
        long durataZile = ChronoUnit.DAYS.between(checkIn, checkOut);
        BigDecimal totalFinal = sumaInitiala;
        if (durataZile > 10) {
            BigDecimal reducere = sumaInitiala.multiply(new BigDecimal("0.05")); // 5%
            totalFinal = sumaInitiala.subtract(reducere);
        }

        return totalFinal;
    }

    public BigDecimal calculeazaAvans(BigDecimal sumaInitiala) {
        return sumaInitiala.multiply(new BigDecimal("0.10")); // 10%
    }
}
