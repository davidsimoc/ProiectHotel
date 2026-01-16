package com.hotel.rezervari.repository;

import com.hotel.rezervari.model.Camera;
import com.hotel.rezervari.model.NecesarConsumabil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NecesarConsumabilRepository extends JpaRepository<NecesarConsumabil, Long> {
    List<NecesarConsumabil> findByCamera(Camera camera);
}
