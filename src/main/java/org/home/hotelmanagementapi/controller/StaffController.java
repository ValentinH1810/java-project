package org.home.hotelmanagementapi.controller;

import org.home.hotelmanagementapi.model.dto.AddTaskToStaffDto;
import org.home.hotelmanagementapi.model.dto.StaffEntityDto;
import org.home.hotelmanagementapi.service.StaffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public ResponseEntity<List<StaffEntityDto>> getAll() {
        return staffService.getAllStaff();
    }

    @GetMapping("/{id}")
    private ResponseEntity<StaffEntityDto> getStaffById(@PathVariable Long id) {
        return staffService.getStaffById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createStaff(@RequestBody StaffEntityDto staffEntityDto) {
        return staffService.createStaff(staffEntityDto);
    }

    @PutMapping("/addTask")
    public ResponseEntity<String> addTaskToStaff(@RequestBody AddTaskToStaffDto addTaskToStaffDto) {
        return staffService.addTaskToStaff(addTaskToStaffDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStaff(@PathVariable Long id) {
        return staffService.delete(id);
    }
}
