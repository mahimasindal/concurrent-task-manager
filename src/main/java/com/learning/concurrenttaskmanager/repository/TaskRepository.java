package com.learning.concurrenttaskmanager.repository;

import com.learning.concurrenttaskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import com.learning.concurrenttaskmanager.entity.TaskStatus;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByTitle(String title);

    List<Task> findByTitleContaining(String keyword);

    List<Task> findByStatusOrderByCreatedAtDesc(TaskStatus status);

    boolean existsByTitle(String title);

    long countByStatus(TaskStatus status);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        UPDATE Task t
        SET t.status = :newStatus,
            t.updatedAt = CURRENT_TIMESTAMP
        WHERE t.id = :id
        AND t.status IN :allowedStatuses
        """)
    int updateStatusIfEligible(
            @Param("id") Long id,
            @Param("newStatus") TaskStatus newStatus,
            @Param("allowedStatuses") Collection<TaskStatus> allowedStatuses
    );

}