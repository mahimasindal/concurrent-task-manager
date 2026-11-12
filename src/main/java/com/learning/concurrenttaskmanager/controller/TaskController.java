
package com.learning.concurrenttaskmanager.controller;

import com.learning.concurrenttaskmanager.dto.CreateTaskRequest;
import com.learning.concurrenttaskmanager.dto.TaskExecutionResponse;
import com.learning.concurrenttaskmanager.dto.TaskResponse;
import com.learning.concurrenttaskmanager.entity.TaskExecution;
import com.learning.concurrenttaskmanager.entity.TaskStatus;
import com.learning.concurrenttaskmanager.service.TaskExecutionService;
import com.learning.concurrenttaskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskExecutionService taskExecutionService;

    public TaskController(TaskService taskService, TaskExecutionService taskExecutionService) {
        this.taskService = taskService;
        this.taskExecutionService = taskExecutionService;
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

    /**
     * HTTP status	Meaning
     * 200 OK	Request successfully processed
     * 201 Created	Resource created
     * 202 Accepted	Request accepted for asynchronous processing*/

    @PostMapping("/{id}/submit")
    public ResponseEntity<Map<String, Object>> submitTask(
            @PathVariable Long id) {

        taskService.getTaskById(id);
        taskService.submitTask(id);

        Map<String, Object> response = Map.of(
                "message", "Task submitted for processing",
                "taskId", id
        );

        return ResponseEntity.accepted().body(response);
    }

    @GetMapping("/{id}/executions")
    public ResponseEntity<List<TaskExecutionResponse>> getAllExecutions(@PathVariable Long id){
       return ResponseEntity.ok( taskExecutionService.getTaskExecutions(id));
    }
}
