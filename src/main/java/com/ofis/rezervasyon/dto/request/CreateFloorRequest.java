package com.ofis.rezervasyon.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateFloorRequest(
    
    @NotNull(message = "Floor number is required")
    Integer floorNumber,
    
    @NotNull(message = "Floor name is required")
    @Size(min = 2, max = 50, message = "Floor name must be between 2 and 50 characters")
    String name
) {
}