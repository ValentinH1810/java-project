package org.home.hotelmanagementapi.service.impl;

import jakarta.transaction.Transactional;
import org.home.hotelmanagementapi.model.dto.GuestEntityDto;
import org.home.hotelmanagementapi.model.entity.GuestEntity;
import org.home.hotelmanagementapi.repository.GuestRepository;
import org.home.hotelmanagementapi.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;

    public GuestServiceImpl(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    @Override
    public ResponseEntity<String> create(GuestEntityDto guestEntityDto) {

        Optional<GuestEntity> optionalGuest = guestRepository.findByEmail(guestEntityDto.getEmail());

        if (optionalGuest.isEmpty()) {
            guestRepository.save(mapGuestFromDtoToEntity(guestEntityDto));
            return new ResponseEntity<>("New guest created!", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Guest already exist", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<GuestEntityDto> getGuest(Long id) {

        Optional<GuestEntity> optionalGuest = guestRepository.findById(id);

        return optionalGuest
                .map(guestEntity -> new ResponseEntity<>(mapGuestFromEntityToDto(guestEntity), HttpStatus.FOUND))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<List<GuestEntityDto>> getAllGuests() {

        List<GuestEntity> entityList = guestRepository.findAll();
        List<GuestEntityDto> dtoList = new ArrayList<>();

        entityList.forEach(guest -> dtoList.add(mapGuestFromEntityToDto(guest)));

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<String> updateGuestPhone(Long id, String newNumber) {

        Optional<GuestEntity> optionalGuest = guestRepository.findById(id);

        if (optionalGuest.isPresent() && isValidPhoneNumber(newNumber)) {
            guestRepository.updatePhone(id, newNumber);

            return new ResponseEntity<>("Guest with id: " + id + " is successfully updated!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Guest with id: " + id + " not found, or the new phone number is incorrect!", HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> deleteGuest(Long id) {

        Optional<GuestEntity> optionalGuest = guestRepository.findById(id);

        if (optionalGuest.isPresent()) {
            guestRepository.delete(optionalGuest.get());
            return new ResponseEntity<>("Guest with id: " + id + " deleted successfully", HttpStatus.OK);
        }

        return new ResponseEntity<>("Guest with id: " + id + " is not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public GuestEntity findGuest(String email) {

        Optional<GuestEntity> optionalGuest = guestRepository.findByEmail(email);

        return optionalGuest.orElse(null);
    }

    private GuestEntityDto mapGuestFromEntityToDto(GuestEntity entity) {
        GuestEntityDto dto = new GuestEntityDto();

        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setAge(entity.getAge());

        return dto;
    }

    private GuestEntity mapGuestFromDtoToEntity(GuestEntityDto entity) {
        GuestEntity dto = new GuestEntity();

        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setEmail(entity.getEmail());
        dto.setAge(entity.getAge());

        return dto;
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");
    }

}
