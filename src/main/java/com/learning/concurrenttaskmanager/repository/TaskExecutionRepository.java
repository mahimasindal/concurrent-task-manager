package com.learning.concurrenttaskmanager.repository;

import com.learning.concurrenttaskmanager.entity.TaskExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskExecutionRepository
        extends JpaRepository<TaskExecution, Long> {

    List<TaskExecution> findByTaskIdOrderByAttemptNumberAsc(Long taskId);

}