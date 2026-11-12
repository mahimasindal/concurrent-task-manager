package com.learning.concurrenttaskmanager.service;

import com.learning.concurrenttaskmanager.dto.TaskExecutionResponse;
import com.learning.concurrenttaskmanager.dto.TaskResponse;
import com.learning.concurrenttaskmanager.entity.Task;
import com.learning.concurrenttaskmanager.entity.TaskExecution;
import com.learning.concurrenttaskmanager.entity.TaskExecutionStatus;
import com.learning.concurrenttaskmanager.entity.TaskStatus;
import com.learning.concurrenttaskmanager.exception.TaskNotFoundException;
import com.learning.concurrenttaskmanager.repository.TaskExecutionRepository;
import com.learning.concurrenttaskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskExecutionService {

    private final TaskExecutionRepository taskExecutionRepository;
    private final TaskRepository taskRepository;

    public TaskExecutionService(
            TaskExecutionRepository taskExecutionRepository,
            TaskRepository taskRepository) {
        this.taskExecutionRepository = taskExecutionRepository;
        this.taskRepository = taskRepository;
    }

    @Transactional
    public TaskExecution startExecution(Long taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        List<TaskExecution> executions =
                taskExecutionRepository.findByTaskIdOrderByAttemptNumberAsc(taskId);

        int attemptNumber = executions.size() + 1;

        TaskExecution execution = new TaskExecution(
                attemptNumber,
                TaskExecutionStatus.RUNNING,
                LocalDateTime.now()
        );

        task.addExecution(execution);

        return taskExecutionRepository.save(execution);
    }

    @Transactional
    public void completeExecution(Long executionId) {
       TaskExecution taskExecution =  taskExecutionRepository.findById(executionId)
               .orElseThrow(()->new IllegalArgumentException(   "Execution not found with id: " + executionId));
       taskExecution.setStatus(TaskExecutionStatus.COMPLETED);
       taskExecution.setCompletedAt(LocalDateTime.now());
       Task task = taskExecution.getTask();
       task.setStatus(TaskStatus.COMPLETED);
    }

    @Transactional
    public void failExecution(Long executionId, String errorMessage) {
        TaskExecution taskExecution = taskExecutionRepository.findById(executionId)
                .orElseThrow(()->new IllegalArgumentException("Execution not found with id: " + executionId));
        taskExecution.setStatus(TaskExecutionStatus.FAILED);
        taskExecution.setCompletedAt(LocalDateTime.now());
        taskExecution.setErrorMessage(errorMessage);
        Task task = taskExecution.getTask();
        task.setStatus(TaskStatus.FAILED);
    }

    public List<TaskExecutionResponse> getTaskExecutions(Long taskId){
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException(taskId);
        }
        List<TaskExecution> taskExecution= taskExecutionRepository.findByTaskIdOrderByAttemptNumberAsc(taskId);
        return taskExecution.stream().map(this::toResponse).toList();
    }


    private TaskExecutionResponse toResponse(TaskExecution taskExecution) {
        return new TaskExecutionResponse(
                taskExecution.getId(),
                taskExecution.getAttemptNumber(),
                taskExecution.getStatus(),
                taskExecution.getStartedAt(),
                taskExecution.getCompletedAt(),
                taskExecution.getErrorMessage()
        );
    }
}
