package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.TaskEntityDto;
import org.home.hotelmanagementapi.model.entity.TaskEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskService {
    TaskEntity getTaskEntity(String taskName);

    ResponseEntity<String> createTask(TaskEntityDto taskEntityDto);

    ResponseEntity<List<TaskEntityDto>> getAllTask();

    ResponseEntity<TaskEntityDto> getTaskById(Long id);

    ResponseEntity<String> delete(String taskName);
}
