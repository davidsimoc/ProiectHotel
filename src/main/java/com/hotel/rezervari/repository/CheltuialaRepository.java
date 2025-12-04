package com.hotel.rezervari.repository;

import com.hotel.rezervari.model.Cheltuiala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheltuialaRepository extends JpaRepository<Cheltuiala, Long> {
    // Folosit pentru cerința: listarea cheltuielilor lunare
    // Metodele de filtrare pe dată vor fi adăugate în Service.
}