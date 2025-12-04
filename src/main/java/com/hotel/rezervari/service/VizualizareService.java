package com.hotel.rezervari.service;

import com.hotel.rezervari.model.Rezervare;
import com.hotel.rezervari.model.Client;
import com.hotel.rezervari.repository.RezervareRepository;
import com.hotel.rezervari.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VizualizareService {
    private final RezervareRepository rezervareRepository;
    private final ClientRepository clientRepository;

    public VizualizareService(RezervareRepository rezervareRepository, ClientRepository clientRepository) {
        this.rezervareRepository = rezervareRepository;
        this.clientRepository = clientRepository;
    }

    public List<Rezervare> getCamereOcupateAzi() {
        return rezervareRepository.findActiveReservationsToday();
    }

    public List<Rezervare> getRezervariByMonth(int an, int luna) {
        LocalDate dataStart = LocalDate.of(an, luna, 1);
        LocalDate dataEnd = dataStart.withDayOfMonth(dataStart.lengthOfMonth());

        return rezervareRepository.findReservationsByDateRange(dataStart, dataEnd);
    }

    public List<Client> getClientiLaData(LocalDate data) {
        List<Rezervare> rezervariActive = rezervareRepository.findActiveReservationsToday();

        // Simplitate: extrage doar clienții din rezervările active găsite
        return rezervariActive.stream()
                .map(Rezervare::getClient)
                .distinct()
                .collect(Collectors.toList());
    }
}
