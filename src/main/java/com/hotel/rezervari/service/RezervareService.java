package com.hotel.rezervari.service;

import com.hotel.rezervari.model.Camera;
import com.hotel.rezervari.model.Client;
import com.hotel.rezervari.model.Rezervare;
import com.hotel.rezervari.model.Tranzactie;
import com.hotel.rezervari.repository.CameraRepository;
import com.hotel.rezervari.repository.ClientRepository;
import com.hotel.rezervari.repository.RezervareRepository;
import com.hotel.rezervari.repository.TranzactieRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RezervareService {
    private final TarifService tarifService;
    private final CameraRepository cameraRepository;
    private final ClientRepository clientRepository;
    private final RezervareRepository rezervareRepository;
    private final TranzactieRepository  tranzactieRepository;

    public RezervareService(TarifService tarifService,
                            CameraRepository cameraRepository,
                            ClientRepository clientRepository,
                            RezervareRepository rezervareRepository,
                            TranzactieRepository tranzactieRepository) {
        this.tarifService = tarifService;
        this.cameraRepository = cameraRepository;
        this.clientRepository = clientRepository;
        this.rezervareRepository = rezervareRepository;
        this.tranzactieRepository = tranzactieRepository;
    }

    @Transactional
    public Rezervare salveazaRezervare(Rezervare rezervare, Client clientNou, Long cameraId) {
        Camera camera = cameraRepository.findById(cameraId)
                .orElseThrow(() -> new IllegalArgumentException("Eroare: Camera nu a fost găsită."));

        if (rezervare.getDataCheckin().isAfter(rezervare.getDataCheckout())) {
            throw new IllegalArgumentException("Eroare: Data de check-out nu poate fi înainte de data de check-in.");
        }
        List<Rezervare> suprapuneri = rezervareRepository.findOverlappingReservations(
                cameraId,
                rezervare.getDataCheckin(),
                rezervare.getDataCheckout()
        );
        if (!suprapuneri.isEmpty()) {
            throw new IllegalArgumentException("Eroare: Camera " + camera.getNumarCamera() +
                    " este deja rezervată în perioada solicitată. Vă rugăm alegeți alte date sau altă cameră.");
        }
        Optional<Client> clientExistent = clientRepository.findByCnp(clientNou.getCnp());
        Client clientFinal = clientExistent.orElseGet(() -> {
            return clientRepository.save(clientNou);
        });
        rezervare.setClient(clientFinal);
        rezervare.setCamera(camera);
        Long tipCameraId = camera.getTipCamera().getTipId();
        BigDecimal sumaInitiala = tarifService.calculeazaTarifInitial(
                tipCameraId, rezervare.getNrPersoane(), rezervare.getDataCheckin(), rezervare.getDataCheckout()
        );
        rezervare.setSumaCazareInitiala(sumaInitiala);

        BigDecimal totalFinal = tarifService.calculeazaTotalFinal(
                sumaInitiala, rezervare.getDataCheckin(), rezervare.getDataCheckout()
        );
        rezervare.setTotalPlataFinala(totalFinal);

        BigDecimal avans = tarifService.calculeazaAvans(sumaInitiala);
        rezervare.setAvansAchitat(avans);
        rezervare.setStatus("CONFIRMED");
        rezervare.setReducereAplicata(ChronoUnit.DAYS.between(rezervare.getDataCheckin(), rezervare.getDataCheckout()) > 10);

        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.LocalDate checkInNou = rezervare.getDataCheckin();
        java.time.LocalDate checkOutNou = rezervare.getDataCheckout();
        camera.setDataOcuparii(checkInNou);
        camera.setDataEliberarii(checkOutNou);
        if ((checkInNou.isEqual(today) || checkInNou.isBefore(today)) && checkOutNou.isAfter(today)) {
            camera.setEsteOcupata(true);
        } else {
            camera.setEsteOcupata(false);
        }
        rezervareRepository.findNextReservationDate(cameraId, checkOutNou)
                .ifPresentOrElse(
                        camera::setDataUrmatoareiRezervari,
                        () -> camera.setDataUrmatoareiRezervari(null)
                );
        cameraRepository.save(camera);

        Rezervare rezervareSalvata = rezervareRepository.save(rezervare);

        Tranzactie tranzactie = new Tranzactie();
        tranzactie.setRezervare(rezervareSalvata);
        tranzactie.setSuma(rezervareSalvata.getAvansAchitat());
        tranzactie.setDataTranzactie(LocalDateTime.now());
        tranzactie.setTipTranzactie("AVANS_REZERVARE");

        tranzactieRepository.save(tranzactie);
        return rezervareSalvata;
    }

}
