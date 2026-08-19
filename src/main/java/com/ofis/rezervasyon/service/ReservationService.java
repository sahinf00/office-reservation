package com.ofis.rezervasyon.service;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ofis.rezervasyon.dto.request.CreateReservationRequest;
import com.ofis.rezervasyon.dto.response.ReservationResponse;
import com.ofis.rezervasyon.enums.ReservationStatus;
import com.ofis.rezervasyon.model.Desk;
import com.ofis.rezervasyon.model.Reservation;
import com.ofis.rezervasyon.model.User;
import com.ofis.rezervasyon.repository.DeskRepository;
import com.ofis.rezervasyon.repository.ReservationRepository;
import com.ofis.rezervasyon.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {
    
    private final ReservationRepository reservationRepository;
    private final DeskRepository deskRepository;
    private final UserRepository userRepository;

    // helper method for mapping Reservation entity to ReservationResponse DTO
    private ReservationResponse mapToReservationResponse(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getDesk().getId(),
            reservation.getDesk().getDeskNumber(),
            reservation.getDesk().getFloor().getFloorNumber(),
            reservation.getUser().getId(),
            reservation.getUser().getFullName(),
            reservation.getReservationDate(),
            reservation.getStatus()
        );
    }

    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest request) {
        Desk desk = deskRepository.findByIdWithLock(request.deskId())
            .orElseThrow(() -> new EntityNotFoundException("Desk with ID " + request.deskId() + " does not exist."));

        // checks for desk activity status before creating a reservation
        if (!desk.isActive()) {
            throw new IllegalStateException("Cannot create a reservation for an inactive desk.");
        }

        // retrieves the current authenticated user's email from the security context
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new EntityNotFoundException("User with email " + currentUserEmail + " does not exist."));

        // checks if the desk is already reserved for the selected date to prevent conflicts
        if (reservationRepository.existsByDeskIdAndReservationDateAndStatus(
            desk.getId(), 
            request.reservationDate(), 
            ReservationStatus.CONFIRMED)) {
            throw new IllegalStateException("This desk is already reserved for the selected date.");
        }

        // checks if the user already has a reservation for the same date to prevent double booking
        if (reservationRepository.existsByUserIdAndReservationDateAndStatus(
            currentUser.getId(), 
            request.reservationDate(), 
            ReservationStatus.CONFIRMED)) {
            throw new IllegalStateException("You already have an active reservation for this desk on the selected date.");
        }

        Reservation reservation = new Reservation();
        reservation.setDesk(desk);
        reservation.setUser(currentUser);
        reservation.setReservationDate(request.reservationDate());
        reservation.setStatus(ReservationStatus.CONFIRMED);
        Reservation savedReservation = reservationRepository.save(reservation);

        return mapToReservationResponse(savedReservation);
    }

    public List <ReservationResponse> getReservationsForCurrentUser() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(currentUserEmail)
            .orElseThrow(() -> new EntityNotFoundException("User with email " + currentUserEmail + " does not exist."));

        List<Reservation> reservations = reservationRepository.findByUserId(currentUser.getId());
        return reservations.stream()
                .map(this::mapToReservationResponse)
                .toList();
    }

}
