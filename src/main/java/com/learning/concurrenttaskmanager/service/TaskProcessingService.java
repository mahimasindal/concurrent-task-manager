package com.learning.concurrenttaskmanager.service;

import com.learning.concurrenttaskmanager.entity.TaskExecution;
import com.learning.concurrenttaskmanager.entity.TaskStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TaskProcessingService {

    private final TaskStatusService taskStatusService;
    private final TaskExecutionService taskExecutionService;

    public TaskProcessingService(TaskStatusService taskStatusService, TaskExecutionService taskExecutionService) {
        this.taskStatusService = taskStatusService;
        this.taskExecutionService = taskExecutionService;
    }

    @Async("taskExecutor")
    public void processTask(Long taskId) {

        String threadName = Thread.currentThread().getName();

        System.out.println(
                "Started processing task " + taskId +
                        " on thread: " + threadName
        );
        TaskExecution taskExecution = null;
        try {
            taskExecution = taskExecutionService.startExecution(taskId);
            // Simulate a time-consuming operation
            Thread.sleep(5000);
            taskExecutionService.completeExecution(taskExecution.getId());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            taskExecutionService.failExecution(taskExecution.getId(), e.getMessage());
            System.out.println("Task interrupted: " + taskId);
            return;
        } catch (Exception e) {
            taskExecutionService.failExecution(taskExecution.getId(), e.getMessage());
            System.out.println("Task failed: " + taskId);
            e.printStackTrace();
        }

        System.out.println(
                "Completed processing task " + taskId +
                        " on thread: " + threadName
        );
    }
}