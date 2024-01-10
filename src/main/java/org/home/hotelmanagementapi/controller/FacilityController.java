package org.home.hotelmanagementapi.controller;

import org.home.hotelmanagementapi.model.dto.FacilityEntityDto;
import org.home.hotelmanagementapi.model.dto.ReserveFacilityDto;
import org.home.hotelmanagementapi.service.FacilityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping
    public ResponseEntity<List<FacilityEntityDto>> getAllFacilities() {
        return facilityService.getAllFacilities();
    }

    @GetMapping("{name}")
    public ResponseEntity<FacilityEntityDto> getFacilityByName(@PathVariable String name) {
        return facilityService.getFacilityByName(name);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFacility(@RequestBody FacilityEntityDto facilityEntityDto) {
        return facilityService.createFacility(facilityEntityDto);
    }

    @PutMapping("/reserve")
    public ResponseEntity<String> reserveFacilityForGuest(@RequestBody ReserveFacilityDto reserveFacilityDto) {
        return facilityService.reserve(reserveFacilityDto);
    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<String> deleteFacility(@PathVariable String name) {
        return facilityService.delete(name);
    }

}
