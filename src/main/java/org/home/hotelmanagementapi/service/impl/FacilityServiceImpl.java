package org.home.hotelmanagementapi.service.impl;

import org.home.hotelmanagementapi.model.dto.FacilityEntityDto;
import org.home.hotelmanagementapi.model.dto.ReserveFacilityDto;
import org.home.hotelmanagementapi.model.entity.FacilityEntity;
import org.home.hotelmanagementapi.model.entity.GuestEntity;
import org.home.hotelmanagementapi.repository.FacilityRepository;
import org.home.hotelmanagementapi.service.FacilityService;
import org.home.hotelmanagementapi.service.GuestService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;
    private final GuestService guestService;
    private final ModelMapper modelMapper;

    public FacilityServiceImpl(FacilityRepository facilityRepository,
                               GuestService guestService,
                               ModelMapper modelMapper) {
        this.facilityRepository = facilityRepository;
        this.guestService = guestService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<String> createFacility(FacilityEntityDto facilityEntityDto) {

        Optional<FacilityEntity> optionalFacilityEntity = facilityRepository.findByName(facilityEntityDto.getName());

        if (optionalFacilityEntity.isEmpty()){
            facilityRepository.save(modelMapper.map(facilityEntityDto, FacilityEntity.class));
            return new ResponseEntity<>("New facility created!", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Facility with name: " + facilityEntityDto.getName() + " already exists!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<FacilityEntityDto>> getAllFacilities() {

        List<FacilityEntityDto> dtoList = getDtoList();

        if (dtoList == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FacilityEntityDto> getFacilityByName(String name) {

        Optional<FacilityEntity> optionalFacility = facilityRepository.findByName(name);

        return optionalFacility
                .map(facilityEntity -> new ResponseEntity<>(modelMapper.map(facilityEntity, FacilityEntityDto.class), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @Override
    public ResponseEntity<String> reserve(ReserveFacilityDto reserveFacilityDto) {

        GuestEntity guest = guestService.findGuest(reserveFacilityDto.getGuestEmail());
        if (guest == null) {
            return new ResponseEntity<>("Guest with email: " + reserveFacilityDto.getGuestEmail() + " doesn't exists!", HttpStatus.BAD_REQUEST);
        }

        Optional<FacilityEntity> optionalFacility = facilityRepository.findByName(reserveFacilityDto.getFacilityName());

        if (optionalFacility.isEmpty()) {
            return new ResponseEntity<>("Facility with name: " + reserveFacilityDto.getFacilityName() + " doesn't exists!", HttpStatus.BAD_REQUEST);
        }

        FacilityEntity facility = optionalFacility.get();
        List<GuestEntity> guests = facility.getGuests();
        guests.add(guest);

        facility.setGuests(guests);
        facilityRepository.save(facility);

        return new ResponseEntity<>("Reservation for: " + facility.getName() + " added to " + guest.getEmail(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(String name) {

        Optional<FacilityEntity> optionalFacilityEntity = facilityRepository.findByName(name);

        if (optionalFacilityEntity.isEmpty()) {
            return new ResponseEntity<>("Facility with name: " + name + " was not found!", HttpStatus.NOT_FOUND);
        }

        FacilityEntity facility = optionalFacilityEntity.get();
        facility.setGuests(new ArrayList<>());

        facilityRepository.save(facility);
        facilityRepository.delete(facility);

        return new ResponseEntity<>("Facility was deleted!", HttpStatus.OK);
    }

    @Override
    public FacilityEntity findFacilityByName(String facilityName) {

        Optional<FacilityEntity> optionalFacility = facilityRepository.findByName(facilityName);

        return optionalFacility.orElse(null);

    }

    private List<FacilityEntityDto> getDtoList() {

        List<FacilityEntity> entityList = facilityRepository.findAll();
        List<FacilityEntityDto> dtoList = new ArrayList<>();

        if (entityList.isEmpty()) {
            return null;
        }

        for (FacilityEntity entity : entityList) {
            dtoList.add(modelMapper.map(entity, FacilityEntityDto.class));
        }

        return dtoList;
    }
}
