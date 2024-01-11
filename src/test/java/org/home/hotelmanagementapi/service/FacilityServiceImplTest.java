package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.enumeration.FacilityTypeEnum;
import org.home.hotelmanagementapi.model.dto.FacilityEntityDto;
import org.home.hotelmanagementapi.model.entity.FacilityEntity;
import org.home.hotelmanagementapi.repository.FacilityRepository;
import org.home.hotelmanagementapi.service.impl.FacilityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacilityServiceImplTest {

    @Mock
    private FacilityRepository facilityRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FacilityServiceImpl facilityService;

    @Test
    void testCreateFacilitySuccess() {
        FacilityEntityDto facilityDto = new FacilityEntityDto();
        facilityDto.setName("NewFacility");
        facilityDto.setType(FacilityTypeEnum.FITNESS);
        facilityDto.setPrice(BigDecimal.valueOf(100.00));

        when(facilityRepository.findByName("NewFacility")).thenReturn(Optional.empty());
        when(modelMapper.map(facilityDto, FacilityEntity.class)).thenReturn(new FacilityEntity());

        ResponseEntity<String> response = facilityService.createFacility(facilityDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("New facility created!", response.getBody());

        verify(facilityRepository, times(1)).findByName("NewFacility");
        verify(facilityRepository, times(1)).save(any());
        verify(modelMapper, times(1)).map(facilityDto, FacilityEntity.class);
    }

    @Test
    void testCreateFacilityFailureFacilityExists() {
        FacilityEntityDto facilityDto = new FacilityEntityDto();
        facilityDto.setName("ExistingFacility");
        facilityDto.setType(FacilityTypeEnum.FITNESS);
        facilityDto.setPrice(BigDecimal.valueOf(100.00));

        when(facilityRepository.findByName("ExistingFacility")).thenReturn(Optional.of(new FacilityEntity()));

        ResponseEntity<String> response = facilityService.createFacility(facilityDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Facility with name: ExistingFacility already exists!", response.getBody());

        verify(facilityRepository, times(1)).findByName("ExistingFacility");
        verify(facilityRepository, never()).save(any());
        verify(modelMapper, never()).map(any(), eq(FacilityEntity.class));
    }

    @Test
    void testGetAllFacilitiesSuccess() {
        List<FacilityEntity> facilityEntities = new ArrayList<>();
        facilityEntities.add(new FacilityEntity("Facility1", FacilityTypeEnum.FITNESS, BigDecimal.valueOf(50.00), new ArrayList<>()));
        facilityEntities.add(new FacilityEntity("Facility2", FacilityTypeEnum.FITNESS, BigDecimal.valueOf(70.00), new ArrayList<>()));

        when(facilityRepository.findAll()).thenReturn(facilityEntities);
        when(modelMapper.map(any(), eq(FacilityEntityDto.class))).thenAnswer(invocation -> {
            FacilityEntity source = invocation.getArgument(0);
            FacilityEntityDto target = new FacilityEntityDto();
            target.setName(source.getName());
            target.setType(source.getType());
            target.setPrice(source.getPrice());
            return target;
        });

        ResponseEntity<List<FacilityEntityDto>> response = facilityService.getAllFacilities();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(facilityRepository, times(1)).findAll();
        verify(modelMapper, times(2)).map(any(), eq(FacilityEntityDto.class));
    }

    @Test
    void testGetAllFacilitiesEmptyList() {
        when(facilityRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseEntity<List<FacilityEntityDto>> response = facilityService.getAllFacilities();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(facilityRepository, times(1)).findAll();
        verify(modelMapper, never()).map(any(), eq(FacilityEntityDto.class));
    }

}