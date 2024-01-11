package org.home.hotelmanagementapi.service.impl;

import org.home.hotelmanagementapi.model.dto.AddTaskToStaffDto;
import org.home.hotelmanagementapi.model.dto.StaffEntityDto;
import org.home.hotelmanagementapi.model.entity.StaffEntity;
import org.home.hotelmanagementapi.model.entity.TaskEntity;
import org.home.hotelmanagementapi.repository.StaffRepository;
import org.home.hotelmanagementapi.service.StaffService;
import org.home.hotelmanagementapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final TaskService taskService;

    public StaffServiceImpl(StaffRepository staffRepository,
                            TaskService taskService) {
        this.staffRepository = staffRepository;
        this.taskService = taskService;
    }

    @Override
    public ResponseEntity<String> createStaff(StaffEntityDto staffEntityDto) {

        Optional<StaffEntity> optionalStaff = staffRepository.findByPhoneNumber(staffEntityDto.getPhoneNumber());

        if (optionalStaff.isEmpty()) {
            staffRepository.save(mapStaffDtoToEntity(staffEntityDto));

            return new ResponseEntity<>("New Staff created!", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Staff already exists!", HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<StaffEntityDto>> getAllStaff() {

        List<StaffEntityDto> dtoList = mapStaffEntityListToDtoList();

        return dtoList == null ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) : new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<StaffEntityDto> getStaffById(Long id) {

        Optional<StaffEntity> optionalStaff = staffRepository.findById(id);

        return optionalStaff
                .map(staffEntity -> new ResponseEntity<>(mapStaffEntityToDto(staffEntity), HttpStatus.OK))
                .orElse(null);

    }

    @Override
    public ResponseEntity<String> addTaskToStaff(AddTaskToStaffDto addTaskToStaffDto) {

        Optional<StaffEntity> optionalStaff = staffRepository.findById(addTaskToStaffDto.getStaffId());

        if (optionalStaff.isEmpty()) {
            return new ResponseEntity<>("Staff with id: " + addTaskToStaffDto.getStaffId() + " do not exist!", HttpStatus.BAD_REQUEST);
        }

        StaffEntity staff = optionalStaff.get();
        List<TaskEntity> tasksList = staff.getTasks();
        TaskEntity task = taskService.getTaskEntity(addTaskToStaffDto.getTaskName());

        if (task == null) {
            return new ResponseEntity<>("Task with name: " + addTaskToStaffDto.getTaskName() + " do not exist!", HttpStatus.BAD_REQUEST);
        }

        tasksList.add(task);
        staff.setTasks(tasksList);

        staffRepository.save(staff);

        return new ResponseEntity<>("Task added successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(Long id) {

        Optional<StaffEntity> optionalStaff = staffRepository.findById(id);

        if (optionalStaff.isEmpty()) {
            return new ResponseEntity<>("Staff with id: " + id + " do not exist!", HttpStatus.NOT_FOUND);
        }

        staffRepository.delete(optionalStaff.get());
        return new ResponseEntity<>("Staff deleted!", HttpStatus.OK);
    }

    private List<StaffEntityDto> mapStaffEntityListToDtoList() {

        List<StaffEntity> entities = staffRepository.findAll();
        List<StaffEntityDto> dtos = new ArrayList<>();

        if (entities.isEmpty()) {
            return null;
        }

        entities.forEach(entity -> dtos.add(mapStaffEntityToDto(entity)));

        return dtos;
    }

    private StaffEntityDto mapStaffEntityToDto(StaffEntity entity) {
        StaffEntityDto dto = new StaffEntityDto();

        dto.setName(entity.getName());
        dto.setPosition(entity.getPosition());
        dto.setPhoneNumber(entity.getPhoneNumber());

        return dto;
    }

    private StaffEntity mapStaffDtoToEntity(StaffEntityDto dto) {

        StaffEntity entity = new StaffEntity();
        List<TaskEntity> tasks = new ArrayList<>();

        entity.setName(dto.getName());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setPosition(dto.getPosition());
        entity.setTasks(tasks);

        return entity;
    }
}
