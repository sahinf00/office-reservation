package com.ofis.rezervasyon.repository;

import com.ofis.rezervasyon.model.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeskRepository extends JpaRepository<Desk, Long> {
    List<Desk> findByFloorId(Long id);

    List<Desk> findByIsActiveTrue();

    List<Desk> findByFloorIdAndIsActiveTrue(Long id);
}
