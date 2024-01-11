package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.enumeration.StaffPositionEnum;
import org.home.hotelmanagementapi.model.dto.StaffEntityDto;
import org.home.hotelmanagementapi.model.entity.StaffEntity;
import org.home.hotelmanagementapi.repository.StaffRepository;
import org.home.hotelmanagementapi.service.impl.StaffServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StaffServiceImplTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private StaffServiceImpl staffService;


    @Test
    void testCreateStaffSuccess() {

        StaffEntityDto staffEntityDto = new StaffEntityDto("John Doe", "1234567890", StaffPositionEnum.HOTEL_MANAGER);
        when(staffRepository.findByPhoneNumber(staffEntityDto.getPhoneNumber())).thenReturn(Optional.empty());

        ResponseEntity<String> response = staffService.createStaff(staffEntityDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New Staff created!", response.getBody());
    }

    @Test
    void testCreateStaffFailure() {

        StaffEntityDto staffEntityDto = new StaffEntityDto("John Doe", "1234567890", StaffPositionEnum.HOTEL_MANAGER);
        when(staffRepository.findByPhoneNumber(staffEntityDto.getPhoneNumber())).thenReturn(Optional.of(new StaffEntity()));

        ResponseEntity<String> response = staffService.createStaff(staffEntityDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Staff already exists!", response.getBody());
    }

    @Test
    void testGetAllStaffSuccess() {

        List<StaffEntity> staffEntities = new ArrayList<>();
        staffEntities.add(new StaffEntity("John Doe", "1234567890", StaffPositionEnum.HOTEL_MANAGER, new ArrayList<>()));
        when(staffRepository.findAll()).thenReturn(staffEntities);


        ResponseEntity<List<StaffEntityDto>> response = staffService.getAllStaff();


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }
}