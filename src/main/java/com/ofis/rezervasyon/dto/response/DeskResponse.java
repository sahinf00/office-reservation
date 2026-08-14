package com.ofis.rezervasyon.dto.response;

public record DeskResponse(
    Long id,
    String deskNumber,
    boolean isActive,
    Long floorId
) {
}
