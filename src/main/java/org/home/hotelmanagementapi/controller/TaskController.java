package org.home.hotelmanagementapi.controller;

import org.home.hotelmanagementapi.model.dto.TaskEntityDto;
import org.home.hotelmanagementapi.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskEntityDto>> getAllTask() {
        return taskService.getAllTask();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntityDto> getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTask(@RequestBody TaskEntityDto taskEntityDto) {
        return taskService.createTask(taskEntityDto);
    }

    @DeleteMapping("/delete/{taskName}")
    public ResponseEntity<String> deleteTask(@PathVariable String taskName) {
        return taskService.delete(taskName);
    }
}
