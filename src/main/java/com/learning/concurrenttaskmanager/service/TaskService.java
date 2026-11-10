package com.learning.concurrenttaskmanager.service;

import com.learning.concurrenttaskmanager.dto.TaskResponse;
import com.learning.concurrenttaskmanager.entity.Task;
import com.learning.concurrenttaskmanager.entity.TaskStatus;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    TaskResponse createTask(String title, String description);

    TaskResponse getTaskById(Long id);

    List<TaskResponse> getAllTasks();

    List<TaskResponse> getTasksByStatus(TaskStatus status);
}