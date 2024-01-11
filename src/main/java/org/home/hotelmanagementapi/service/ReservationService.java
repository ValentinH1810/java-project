package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.ReservationEntityDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {
    ResponseEntity<String> createReservation(ReservationEntityDto reservationEntityDto);

    ResponseEntity<List<ReservationEntityDto>> getAllReservations();

    ResponseEntity<ReservationEntityDto> getReservationById(Long id);

    ResponseEntity<String> deleteReservationById(Long id);
}
