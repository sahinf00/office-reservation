package com.ofis.rezervasyon.dto.response;

import java.util.List;

public record FloorResponse(
    Long id,
    Integer floorNumber,
    String name,
    List <DeskResponse> desks
) {   
}
