package com.learning.concurrenttaskmanager.service;

import com.learning.concurrenttaskmanager.entity.Task;
import com.learning.concurrenttaskmanager.entity.TaskStatus;
import com.learning.concurrenttaskmanager.exception.TaskNotFoundException;
import com.learning.concurrenttaskmanager.exception.TaskSubmissionNotAllowedException;
import com.learning.concurrenttaskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.learning.concurrenttaskmanager.dto.TaskResponse;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskProcessingService taskProcessingService;

    public TaskServiceImpl(TaskRepository taskRepository,TaskProcessingService taskProcessingService) {
        this.taskRepository = taskRepository;
        this.taskProcessingService = taskProcessingService;
    }

    @Transactional
    @Override
    public TaskResponse createTask(String title, String description) {
        Task task = new Task(title, description);
        Task savedTask = taskRepository.save(task);
        return toResponse(savedTask);
    }

    @Transactional(readOnly = true)
    @Override
    public TaskResponse getTaskById(Long id) {
        Task task= taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return toResponse(task);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TaskResponse> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status).stream().map(this::toResponse).toList();
    }

    @Override
    public void submitTask(Long id) {
        int isUpdated = taskRepository.updateStatusIfEligible(id,TaskStatus.PROCESSING,List.of(TaskStatus.PENDING, TaskStatus.FAILED));

        if (isUpdated==0) {
            Task task = taskRepository.findById(id)
                    .orElseThrow(() -> new TaskNotFoundException(id));

            throw new TaskSubmissionNotAllowedException(
                    "Task cannot be submitted in status: " + task.getStatus()
            );
        }

        taskProcessingService.processTask(id);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}