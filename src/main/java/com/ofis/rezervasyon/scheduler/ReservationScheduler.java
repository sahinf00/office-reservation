package com.ofis.rezervasyon.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ofis.rezervasyon.service.ReservationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReservationScheduler {
    
    private final ReservationService reservationService;

    @Scheduled(cron = "${app.scheduling.reservation-completion-cron:0 0 0 * * ?}")
    public void processCompletedReservations() {
        log.info("Starting the process to update completed reservations...");
        int updatedCount = reservationService.updateCompletedReservations();
        log.info("Updated {} completed reservations accordingly.", updatedCount);
    }
}
