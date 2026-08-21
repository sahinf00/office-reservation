package com.ofis.rezervasyon.dto.response;

public record DashboardSummaryResponse(
    long totalActiveDesks,
    long totalReservationCountForToday,
    double occupancyRate
) {

}

