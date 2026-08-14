package com.ofis.rezervasyon.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDeskRequest(
    
    @NotBlank(message = "Desk number is required")
    String deskNumber,
    
    @NotNull(message = "Floor ID is required")
    Long floorId
) {
}
