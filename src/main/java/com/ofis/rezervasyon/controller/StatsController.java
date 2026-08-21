package com.ofis.rezervasyon.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ofis.rezervasyon.service.StatsService;
import com.ofis.rezervasyon.dto.response.DashboardSummaryResponse;
import com.ofis.rezervasyon.dto.response.FloorOccupancyResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {
    private final StatsService statsService;
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/occupancy-by-floor")
    public ResponseEntity<List<FloorOccupancyResponse>> getFloorOccupancyStats(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<FloorOccupancyResponse> response = statsService.getFloorOccupancyStats(date);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getDashboardSummaryStats(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        DashboardSummaryResponse response = statsService.getDashboardSummaryStats(date);
        return ResponseEntity.ok(response);
    }
}
