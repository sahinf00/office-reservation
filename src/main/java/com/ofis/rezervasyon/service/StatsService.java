package com.ofis.rezervasyon.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ofis.rezervasyon.dto.response.FloorOccupancyResponse;
import com.ofis.rezervasyon.repository.DeskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatsService {
    
    private final DeskRepository deskRepository;

    public List<FloorOccupancyResponse> getFloorOccupancyStats(LocalDate date) {
        
        LocalDate targetDate = (date != null) ? date : LocalDate.now();

        List<FloorOccupancyResponse> rawStats = deskRepository.findOccupancyByFloorRaw(targetDate);
        // insert calculations for occupancy rate here
        return rawStats.stream()
                .map(this::calculateOccupancyRate)
                .toList();

    }

    // helper method to calculate occupancy rate for each floor
    private FloorOccupancyResponse calculateOccupancyRate(FloorOccupancyResponse raw) {
        long totalDesks = raw.totalDesks();
        long occupiedDesks = raw.occupiedDesks();
        // check for division by zero to avoid ArithmeticException
        double occupancyRate = (totalDesks > 0) ? ((double) occupiedDesks / totalDesks) * 100 : 0.0;
        // round the occupancy rate to two decimal places for better readability
        double roundedOccupancyRate = Math.round(occupancyRate * 100.0) / 100.0; 

        return new FloorOccupancyResponse(
            raw.floorId(),
            raw.floorNumber(),
            raw.floorName(),
            totalDesks,
            occupiedDesks,
            roundedOccupancyRate
        );
    }
}
