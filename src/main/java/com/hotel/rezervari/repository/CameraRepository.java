package com.hotel.rezervari.repository;

import com.hotel.rezervari.model.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Long> {

    @Query("SELECT c FROM Camera c WHERE c.cameraId NOT IN (" +
            "  SELECT r.camera.cameraId FROM Rezervare r WHERE " +
            "  r.dataCheckin < :dataCheckout AND r.dataCheckout > :dataCheckin " +
            "  AND r.status <> 'CANCELED'" +
            ")")
    List<Camera> findAvailableCamere(@Param("dataCheckin") LocalDate dataCheckin,
                                     @Param("dataCheckout") LocalDate dataCheckout);

}