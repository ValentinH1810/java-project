package org.home.hotelmanagementapi.controller;

import org.home.hotelmanagementapi.model.dto.GuestEntityDto;
import org.home.hotelmanagementapi.service.GuestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/guests")
public class GuestController {

    private final GuestService guestService;


    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }

    @GetMapping
    public ResponseEntity<List<GuestEntityDto>> getAllGuests() {
        return guestService.getAllGuests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestEntityDto> getGuest(@PathVariable Long id) {
        return guestService.getGuest(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createGuest(@RequestBody GuestEntityDto guestEntityDto) {
        return guestService.create(guestEntityDto);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateGuestPhoneNumber(@PathVariable Long id,  @RequestBody String newNumber) {
        return guestService.updateGuestPhone(id, newNumber);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGuest(@PathVariable Long id) {
        return guestService.deleteGuest(id);
    }

}