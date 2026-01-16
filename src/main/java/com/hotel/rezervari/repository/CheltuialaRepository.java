package com.hotel.rezervari.repository;

import com.hotel.rezervari.model.Cheltuiala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CheltuialaRepository extends JpaRepository<Cheltuiala, Long> {
    List<Cheltuiala> findByDataCheltuialaBetween(LocalDate start, LocalDate end);
}