package org.home.hotelmanagementapi.service.impl;

import org.home.hotelmanagementapi.model.dto.ReservationEntityDto;
import org.home.hotelmanagementapi.model.entity.GuestEntity;
import org.home.hotelmanagementapi.model.entity.ReservationEntity;
import org.home.hotelmanagementapi.model.entity.RoomEntity;
import org.home.hotelmanagementapi.repository.ReservationRepository;
import org.home.hotelmanagementapi.service.GuestService;
import org.home.hotelmanagementapi.service.ReservationService;
import org.home.hotelmanagementapi.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final GuestService guestService;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  RoomService roomService,
                                  GuestService guestService) {
        this.reservationRepository = reservationRepository;
        this.roomService = roomService;
        this.guestService = guestService;
    }

    @Override
    public ResponseEntity<String> createReservation(ReservationEntityDto reservationEntityDto) {

        Optional<ReservationEntity> optionalReservation = reservationRepository.findById(reservationEntityDto.getId());

        if (optionalReservation.isEmpty()) {
            reservationRepository.save(Objects.requireNonNull(mapReservationFromDtoToEntity(reservationEntityDto)));

            return new ResponseEntity<>("Reservation created!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Reservation already exists!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<ReservationEntityDto>> getAllReservations() {

        List<ReservationEntityDto> dtoList = mapReservationListFromEntityToDto();

        if (dtoList == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ReservationEntityDto> getReservationById(Long id) {

        Optional<ReservationEntity> optionalReservation = reservationRepository.findById(id);

        return optionalReservation
                .map(reservationEntity -> new ResponseEntity<>(mapReservationFromEntityToDto(reservationEntity), HttpStatus.OK))
                .orElse(null);

    }

    @Override
    public ResponseEntity<String> deleteReservationById(Long id) {

        Optional<ReservationEntity> optionalReservation = reservationRepository.findById(id);

        if (optionalReservation.isPresent()) {

            reservationRepository.delete(optionalReservation.get());
            return new ResponseEntity<>("Reservation with id: " + id + " deleted!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Reservation with id: " + id + " not found!", HttpStatus.NOT_FOUND);
    }

    private List<ReservationEntityDto> mapReservationListFromEntityToDto() {

        List<ReservationEntity> entityList = reservationRepository.findAll();
        List<ReservationEntityDto> dtoList = new ArrayList<>();

        if (entityList.isEmpty()) {
            return null;
        }

        entityList.forEach(entity -> dtoList.add(mapReservationFromEntityToDto(entity)));

        return dtoList;
    }

    private ReservationEntityDto mapReservationFromEntityToDto(ReservationEntity entity) {

        ReservationEntityDto dto = new ReservationEntityDto();

        dto.setId(entity.getId());
        dto.setRoomId(entity.getId());
        dto.setCheckingDay(entity.getCheckingDay());
        dto.setStayingDuration(entity.getStayingDuration());
        dto.setGuestEmail(entity.getGuest().getEmail());

        return dto;
    }

    private ReservationEntity mapReservationFromDtoToEntity(ReservationEntityDto dto) {

        ReservationEntity entity = new ReservationEntity();
        RoomEntity room = roomService.findRoom(dto.getRoomId());
        GuestEntity guest = guestService.findGuest(dto.getGuestEmail());

        if (room == null || guest == null) {
            return null;
        }

        entity.setId(dto.getId());
        entity.setRoom(room);
        entity.setGuest(guest);
        entity.setCheckingDay(dto.getCheckingDay());
        entity.setStayingDuration(dto.getStayingDuration());

        return entity;
    }
}
