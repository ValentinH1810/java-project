package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.GuestEntityDto;
import org.home.hotelmanagementapi.model.entity.GuestEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GuestService {
    ResponseEntity<String> create(GuestEntityDto guestEntityDto);

    ResponseEntity<GuestEntityDto> getGuest(Long id);

    ResponseEntity<List<GuestEntityDto>> getAllGuests();

    ResponseEntity<String> updateGuestPhone(Long id, String phoneNumber);

    ResponseEntity<String> deleteGuest(Long id);

    GuestEntity findGuest(String email);
}