package com.learning.concurrenttaskmanager.repository;

import com.learning.concurrenttaskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import com.learning.concurrenttaskmanager.entity.TaskStatus;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitle(String title);

    List<Task> findByTitleContaining(String keyword);

    List<Task> findByStatusOrderByCreatedAtDesc(TaskStatus status);

    boolean existsByTitle(String title);

    long countByStatus(TaskStatus status);

}