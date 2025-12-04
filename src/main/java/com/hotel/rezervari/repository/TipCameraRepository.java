package com.hotel.rezervari.repository;

import com.hotel.rezervari.model.TipCamera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipCameraRepository extends JpaRepository<TipCamera, Long> {
}
