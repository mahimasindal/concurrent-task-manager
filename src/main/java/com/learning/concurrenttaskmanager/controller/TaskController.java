
package com.learning.concurrenttaskmanager.controller;

import com.learning.concurrenttaskmanager.dto.CreateTaskRequest;
import com.learning.concurrenttaskmanager.dto.TaskResponse;
import com.learning.concurrenttaskmanager.entity.TaskStatus;
import com.learning.concurrenttaskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody CreateTaskRequest request) {

        TaskResponse response = taskService.createTask(
                request.getTitle(),
                request.getDescription()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping(params = "status")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(
            @RequestParam TaskStatus status) {

        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }
}
