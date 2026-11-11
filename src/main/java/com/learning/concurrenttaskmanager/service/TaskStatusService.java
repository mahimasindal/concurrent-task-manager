package com.learning.concurrenttaskmanager.service;

import com.learning.concurrenttaskmanager.entity.Task;
import com.learning.concurrenttaskmanager.entity.TaskStatus;
import com.learning.concurrenttaskmanager.exception.TaskNotFoundException;
import com.learning.concurrenttaskmanager.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskStatusService {

    private final TaskRepository taskRepository;

    public TaskStatusService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void updateStatus(Long taskId, TaskStatus status) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        task.setStatus(status);
    }
}