package com.learning.concurrenttaskmanager.service;

import com.learning.concurrenttaskmanager.entity.TaskStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TaskProcessingService {

    private final TaskStatusService taskStatusService;

    public TaskProcessingService(TaskStatusService taskStatusService) {
        this.taskStatusService = taskStatusService;
    }

    @Async("taskExecutor")
    public void processTask(Long taskId) {

        String threadName = Thread.currentThread().getName();

        System.out.println(
                "Started processing task " + taskId +
                        " on thread: " + threadName
        );

        try {
            // Simulate a time-consuming operation
            Thread.sleep(5000);
            taskStatusService.updateStatus(taskId, TaskStatus.COMPLETED);
            System.out.println("Completed processing task: " + taskId);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            taskStatusService.updateStatus(taskId, TaskStatus.FAILED);
            System.out.println("Task interrupted: " + taskId);
            return;
        } catch (Exception e) {

            taskStatusService.updateStatus(taskId, TaskStatus.FAILED);

            System.out.println("Task failed: " + taskId);
            e.printStackTrace();
        }

        System.out.println(
                "Completed processing task " + taskId +
                        " on thread: " + threadName
        );
    }
}