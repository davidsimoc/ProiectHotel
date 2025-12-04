package com.hotel.rezervari.repository;

import com.hotel.rezervari.model.Tranzactie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranzactieRepository extends JpaRepository<Tranzactie, Long> {
    // Folosit pentru cerința: listarea incasărilor lunare
    // Metodele specifice de filtrare pe dată vor fi adăugate în Service.
}