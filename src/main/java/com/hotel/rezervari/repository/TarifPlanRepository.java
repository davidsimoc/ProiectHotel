package com.hotel.rezervari.repository;

import com.hotel.rezervari.model.TarifPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TarifPlanRepository extends JpaRepository<TarifPlan, Long> {

    Optional<TarifPlan> findByTipCamera_TipIdAndNrOccupanti(Long tipId, Integer nrOccupanti);
}