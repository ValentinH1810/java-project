package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.controller.GuestController;
import org.home.hotelmanagementapi.model.dto.GuestEntityDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GuestServiceImplTest {

    @Mock
    private GuestService guestService;

    @InjectMocks
    private GuestController guestController;

    @Test
    void testGetAllGuests() {
        List<GuestEntityDto> guestDtoList = new ArrayList<>();
        when(guestService.getAllGuests()).thenReturn(new ResponseEntity<>(guestDtoList, HttpStatus.OK));

        ResponseEntity<List<GuestEntityDto>> response = guestController.getAllGuests();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(guestDtoList, response.getBody());
    }

    @Test
    void testGetGuest() {
        long guestId = 1L;
        GuestEntityDto guestDto = new GuestEntityDto();
        when(guestService.getGuest(guestId)).thenReturn(new ResponseEntity<>(guestDto, HttpStatus.FOUND));

        ResponseEntity<GuestEntityDto> response = guestController.getGuest(guestId);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(guestDto, response.getBody());
    }

    @Test
    void testCreateGuest() {
        GuestEntityDto guestDto = new GuestEntityDto();
        when(guestService.create(guestDto)).thenReturn(new ResponseEntity<>("New guest created!", HttpStatus.CREATED));

        ResponseEntity<String> response = guestController.createGuest(guestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New guest created!", response.getBody());
    }

    @Test
    void testUpdateGuestPhoneNumber() {
        long guestId = 1L;
        String newPhoneNumber = "1234567890";
        when(guestService.updateGuestPhone(guestId, newPhoneNumber))
                .thenReturn(new ResponseEntity<>("Guest with id: 1 is successfully updated!", HttpStatus.OK));

        ResponseEntity<String> response = guestController.updateGuestPhoneNumber(guestId, newPhoneNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Guest with id: 1 is successfully updated!", response.getBody());
    }

    @Test
    void testDeleteGuest() {
        long guestId = 1L;
        when(guestService.deleteGuest(guestId))
                .thenReturn(new ResponseEntity<>("Guest with id: 1 deleted successfully", HttpStatus.OK));

        ResponseEntity<String> response = guestController.deleteGuest(guestId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Guest with id: 1 deleted successfully", response.getBody());
    }
}