package org.home.hotelmanagementapi.controller;

import org.home.hotelmanagementapi.model.dto.ReservationEntityDto;
import org.home.hotelmanagementapi.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationEntityDto>> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationEntityDto> getReservation(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createReservation(@RequestBody ReservationEntityDto reservationEntityDto) {
        return reservationService.createReservation(reservationEntityDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        return reservationService.deleteReservationById(id);
    }
}
