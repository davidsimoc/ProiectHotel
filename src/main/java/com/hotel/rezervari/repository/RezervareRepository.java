package com.hotel.rezervari.repository;

import com.hotel.rezervari.model.Rezervare;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RezervareRepository extends JpaRepository<Rezervare, Long> {
    @Query("SELECT r FROM Rezervare r WHERE r.camera.cameraId = :cameraId " +
            "AND r.dataCheckin < :dataCheckout AND r.dataCheckout > :dataCheckin " +
            "AND r.status <> 'CANCELED'")
    List<Rezervare> findOverlappingReservations(@Param("cameraId") Long cameraId,
                                                @Param("dataCheckin") LocalDate dataCheckin,
                                                @Param("dataCheckout") LocalDate dataCheckout);

    @Query(value = "SELECT r.dataCheckin FROM Rezervare r WHERE r.camera.cameraId = :cameraId " +
            "AND r.dataCheckin > CURRENT_DATE() AND r.status <> 'CANCELED' " +
            "ORDER BY r.dataCheckin ASC LIMIT 1")
    Optional<LocalDate> findNextReservationDate(@Param("cameraId") Long cameraId);

    @Query("SELECT r FROM Rezervare r WHERE r.dataCheckin <= CURRENT_DATE() " +
            "AND r.dataCheckout > CURRENT_DATE() AND r.status <> 'CANCELED'")
    List<Rezervare> findActiveReservationsToday();

    @Query("SELECT r FROM Rezervare r WHERE " +
            "(r.dataCheckin BETWEEN :dataStart AND :dataEnd) OR " +
            "(r.dataCheckout BETWEEN :dataStart AND :dataEnd) " +
            "ORDER BY r.dataCheckin ASC")
    List<Rezervare> findReservationsByDateRange(@Param("dataStart") LocalDate dataStart,
                                                @Param("dataEnd") LocalDate dataEnd);
}
