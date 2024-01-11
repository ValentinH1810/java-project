package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.AddTaskToStaffDto;
import org.home.hotelmanagementapi.model.dto.StaffEntityDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StaffService {
    ResponseEntity<String> createStaff(StaffEntityDto staffEntityDto);

    ResponseEntity<List<StaffEntityDto>> getAllStaff();

    ResponseEntity<StaffEntityDto> getStaffById(Long id);

    ResponseEntity<String> addTaskToStaff(AddTaskToStaffDto addTaskToStaffDto);

    ResponseEntity<String> delete(Long id);
}
