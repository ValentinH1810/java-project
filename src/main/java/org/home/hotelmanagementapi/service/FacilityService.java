package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.FacilityEntityDto;
import org.home.hotelmanagementapi.model.dto.ReserveFacilityDto;
import org.home.hotelmanagementapi.model.entity.FacilityEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FacilityService {

    ResponseEntity<String> createFacility(FacilityEntityDto facilityEntityDto);

    ResponseEntity<List<FacilityEntityDto>> getAllFacilities();

    ResponseEntity<FacilityEntityDto> getFacilityByName(String name);

    ResponseEntity<String> reserve(ReserveFacilityDto reserveFacilityDto);

    ResponseEntity<String> delete(String name);

    FacilityEntity findFacilityByName(String facilityName);
}
