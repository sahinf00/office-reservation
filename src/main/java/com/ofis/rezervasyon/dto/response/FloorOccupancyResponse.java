package com.ofis.rezervasyon.dto.response;

public record FloorOccupancyResponse(
    Long floorId,
    Integer floorNumber,
    String floorName,
    long totalDesks,
    long occupiedDesks,
    double occupancyRate
) {
    
}
