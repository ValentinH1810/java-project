package org.home.hotelmanagementapi.service;

import org.home.hotelmanagementapi.model.dto.TaskEntityDto;
import org.home.hotelmanagementapi.model.entity.TaskEntity;
import org.home.hotelmanagementapi.repository.TaskRepository;
import org.home.hotelmanagementapi.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testCreateTaskSuccess() {
        TaskEntityDto taskDto = new TaskEntityDto();
        taskDto.setTaskName("TestTask");
        taskDto.setDescription("Test Description");
        taskDto.setDeadline(LocalDate.now().plusDays(7));

        when(taskRepository.findByTaskName("TestTask")).thenReturn(Optional.empty());
        when(taskRepository.save(any())).thenReturn(new TaskEntity());

        ResponseEntity<String> response = taskService.createTask(taskDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task created!", response.getBody());

        verify(taskRepository, times(1)).findByTaskName("TestTask");
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void testCreateTaskFailureTaskExists() {
        TaskEntityDto taskDto = new TaskEntityDto();
        taskDto.setTaskName("ExistingTask");
        taskDto.setDescription("Test Description");
        taskDto.setDeadline(LocalDate.now().plusDays(7));

        when(taskRepository.findByTaskName("ExistingTask")).thenReturn(Optional.of(new TaskEntity()));

        ResponseEntity<String> response = taskService.createTask(taskDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Task ExistingTask exists!", response.getBody());

        verify(taskRepository, times(1)).findByTaskName("ExistingTask");
        verify(taskRepository, never()).save(any());
    }

    @Test
    void testGetAllTaskSuccess() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(
                new TaskEntity("Task1", "Description1", LocalDate.now().plusDays(5)),
                new TaskEntity("Task2", "Description2", LocalDate.now().plusDays(8))
        ));

        ResponseEntity<List<TaskEntityDto>> response = taskService.getAllTask();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetAllTaskEmptyList() {
        when(taskRepository.findAll()).thenReturn(List.of());

        ResponseEntity<List<TaskEntityDto>> response = taskService.getAllTask();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskByIdSuccess() {
        Long taskId = 1L;
        TaskEntity taskEntity = new TaskEntity("Task1", "Description1", LocalDate.now().plusDays(5));
        taskEntity.setId(taskId);

        when(taskRepository.findAll()).thenReturn(List.of(taskEntity));

        ResponseEntity<TaskEntityDto> response = taskService.getTaskById(taskId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Task1", response.getBody().getTaskName());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testGetTaskByIdNotFound() {
        Long taskId = 1L;

        when(taskRepository.findAll()).thenReturn(List.of());

        ResponseEntity<TaskEntityDto> response = taskService.getTaskById(taskId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testDeleteTaskSuccess() {
        String taskName = "TaskToDelete";
        TaskEntity taskEntity = new TaskEntity(taskName, "Description", LocalDate.now().plusDays(5));

        when(taskRepository.findByTaskName(taskName)).thenReturn(Optional.of(taskEntity));

        ResponseEntity<String> response = taskService.delete(taskName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task deleted!", response.getBody());

        verify(taskRepository, times(1)).findByTaskName(taskName);
        verify(taskRepository, times(1)).delete(taskEntity);
    }

    @Test
    void testDeleteTaskNotFound() {
        String taskName = "NonExistingTask";

        when(taskRepository.findByTaskName(taskName)).thenReturn(Optional.empty());

        ResponseEntity<String> response = taskService.delete(taskName);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Task with name: NonExistingTask do not exist!", response.getBody());

        verify(taskRepository, times(1)).findByTaskName(taskName);
        verify(taskRepository, never()).delete(any());
    }

    @Test
    void testGetTaskEntitySuccess() {
        String taskName = "TestTask";
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskName(taskName);

        when(taskRepository.findByTaskName(taskName)).thenReturn(Optional.of(taskEntity));

        TaskEntity result = taskService.getTaskEntity(taskName);

        assertNotNull(result);
        assertEquals(taskName, result.getTaskName());

        verify(taskRepository, times(1)).findByTaskName(taskName);
    }

    @Test
    void testGetTaskEntityNotFound() {
        String taskName = "NonExistingTask";

        when(taskRepository.findByTaskName(taskName)).thenReturn(Optional.empty());

        TaskEntity result = taskService.getTaskEntity(taskName);

        assertNull(result);

        verify(taskRepository, times(1)).findByTaskName(taskName);
    }

    @Test
    void testCreateTaskException() {
        TaskEntityDto taskDto = new TaskEntityDto();
        taskDto.setTaskName("TestTask");
        taskDto.setDescription("Test Description");
        taskDto.setDeadline(LocalDate.now().plusDays(7));

        when(taskRepository.findByTaskName("TestTask")).thenReturn(Optional.empty());
        when(taskRepository.save(any())).thenThrow(new RuntimeException("Simulating a database error."));

        assertThrows(RuntimeException.class, () -> taskService.createTask(taskDto));

        verify(taskRepository, times(1)).findByTaskName("TestTask");
        verify(taskRepository, times(1)).save(any());
    }

    @Test
    void testDeleteTaskException() {
        String taskName = "TaskToDelete";

        when(taskRepository.findByTaskName(taskName)).thenReturn(Optional.of(new TaskEntity()));
        doThrow(new RuntimeException("Simulating a database error.")).when(taskRepository).delete(any());

        assertThrows(RuntimeException.class, () -> taskService.delete(taskName));

        verify(taskRepository, times(1)).findByTaskName(taskName);
        verify(taskRepository, times(1)).delete(any());
    }

}
