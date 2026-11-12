package com.learning.concurrenttaskmanager.exception;

public class TaskSubmissionNotAllowedException extends RuntimeException{

    public TaskSubmissionNotAllowedException(String message){
        super(message);
    }
}
