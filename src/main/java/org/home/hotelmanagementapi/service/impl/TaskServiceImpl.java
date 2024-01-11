package org.home.hotelmanagementapi.service.impl;

import org.home.hotelmanagementapi.model.dto.TaskEntityDto;
import org.home.hotelmanagementapi.model.entity.TaskEntity;
import org.home.hotelmanagementapi.repository.TaskRepository;
import org.home.hotelmanagementapi.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskEntity getTaskEntity(String taskName) {

        Optional<TaskEntity> optionalTask = taskRepository.findByTaskName(taskName);

        return optionalTask.orElse(null);

    }

    @Override
    public ResponseEntity<String> createTask(TaskEntityDto taskEntityDto) {

        Optional<TaskEntity> optionalTask = taskRepository.findByTaskName(taskEntityDto.getTaskName());

        if (optionalTask.isPresent()) {
            return new ResponseEntity<>("Task " + taskEntityDto.getTaskName() + " exists!", HttpStatus.BAD_REQUEST);
        }

        taskRepository.save(mapTaskDtoToEntity(taskEntityDto));

        return new ResponseEntity<>("Task created!", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TaskEntityDto>> getAllTask() {

        List<TaskEntityDto> dtoList = getTaskDtoList();

        if (dtoList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TaskEntityDto> getTaskById(Long id) {

        List<TaskEntity> entities = taskRepository.findAll();

        for (TaskEntity entity : entities) {
            if (entity.getId().equals(id)) {
                return new ResponseEntity<>(mapTaskEntityToDto(entity), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<String> delete(String taskName) {

        Optional<TaskEntity> optionalTask = taskRepository.findByTaskName(taskName);

        if (optionalTask.isEmpty()) {
            return new ResponseEntity<>("Task with name: " + taskName + " do not exist!", HttpStatus.NOT_FOUND);
        }

        taskRepository.delete(optionalTask.get());

        return new ResponseEntity<>("Task deleted!", HttpStatus.OK);
    }

    private List<TaskEntityDto> getTaskDtoList() {

        List<TaskEntity> entities = taskRepository.findAll();
        List<TaskEntityDto> dtos = new ArrayList<>();

        if (entities.isEmpty()) {
            return dtos;
        }

        entities.forEach(entity -> dtos.add(mapTaskEntityToDto(entity)));

        return dtos;
    }

    private TaskEntityDto mapTaskEntityToDto(TaskEntity entity) {

        TaskEntityDto dto = new TaskEntityDto();

        dto.setTaskName(entity.getTaskName());
        dto.setDeadline(entity.getDeadline());
        dto.setDescription(entity.getDescription());


        return dto;
    }

    private TaskEntity mapTaskDtoToEntity(TaskEntityDto dto) {

        TaskEntity entity = new TaskEntity();

        entity.setTaskName(dto.getTaskName());
        entity.setDeadline(dto.getDeadline());
        entity.setDescription(dto.getDescription());

        return entity;
    }
}
