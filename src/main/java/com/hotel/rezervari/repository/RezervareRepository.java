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

    @Query("SELECT MIN(r.dataCheckin) FROM Rezervare r WHERE r.camera.cameraId = :cameraId AND r.dataCheckin >= :dataCheckout")
    Optional<LocalDate> findNextReservationDate(@Param("cameraId") Long cameraId, @Param("dataCheckout") LocalDate dataCheckout);

    @Query("SELECT r FROM Rezervare r WHERE r.dataCheckin <= CURRENT_DATE() " +
            "AND r.dataCheckout > CURRENT_DATE() AND r.status <> 'CANCELED'")
    List<Rezervare> findActiveReservationsToday();

    @Query("SELECT r FROM Rezervare r WHERE " +
            "(r.dataCheckin BETWEEN :dataStart AND :dataEnd) OR " +
            "(r.dataCheckout BETWEEN :dataStart AND :dataEnd) " +
            "ORDER BY r.dataCheckin ASC")
    List<Rezervare> findReservationsByDateRange(@Param("dataStart") LocalDate dataStart,
                                                @Param("dataEnd") LocalDate dataEnd);

    @Query("SELECT r FROM Rezervare r WHERE r.dataCheckin <= :dataSolicitata AND r.dataCheckout > :dataSolicitata AND r.status <> 'CANCELED'")
    List<Rezervare> findActiveReservationsByDate(@Param("dataSolicitata") java.time.LocalDate dataSolicitata);

    @Query("SELECT r FROM Rezervare r WHERE YEAR(r.dataCheckin) = :an AND MONTH(r.dataCheckin) = :luna")
    List<Rezervare> findByMonthAndYearCheckin(@Param("an") int an, @Param("luna") int luna);
}
