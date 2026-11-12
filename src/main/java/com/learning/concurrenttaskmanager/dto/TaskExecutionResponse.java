package com.learning.concurrenttaskmanager.dto;

import com.learning.concurrenttaskmanager.entity.TaskExecutionStatus;

import java.time.LocalDateTime;

public class TaskExecutionResponse {

    private Long id;
    private Integer attemptNumber;
    private TaskExecutionStatus status;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String errorMessage;

    public TaskExecutionResponse(  Long id, Integer attemptNumber,
     TaskExecutionStatus status,
     LocalDateTime startedAt,
     LocalDateTime completedAt,
     String errorMessage){
        this.id=id;
        this.status=status;
        this.attemptNumber=attemptNumber;
        this.startedAt=startedAt;
        this.completedAt=completedAt;
        this.errorMessage=errorMessage;
    }

    public Long getId() {
        return id;
    }

    public TaskExecutionStatus getStatus() {
        return status;
    }

    public Integer getAttemptNumber() {
        return attemptNumber;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}