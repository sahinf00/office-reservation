package com.ofis.rezervasyon.repository;

import com.ofis.rezervasyon.model.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeskRepository extends JpaRepository<Desk, Long> {

    List<Desk> findByIsActiveTrue();
    List<Desk> findByFloorId(Long floorId);
    List<Desk> findByFloorIdAndIsActiveTrue(Long floorId);
    boolean existsByDeskNumberAndFloorId(String deskNumber, Long floorId);
    boolean existsByDeskNumberAndFloorIdAndIsActiveTrue(String deskNumber, Long floorId);
}
