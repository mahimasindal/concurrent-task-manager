package com.learning.concurrenttaskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class TaskExecutorConfig {

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        /**
         * Create threads until the core pool size is reached.
         * Once all core threads are busy, queue incoming tasks.
         * When the queue is full, create additional threads up to the maximum.
         * If the queue and maximum pool are both exhausted, reject new tasks.
         * */

        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("task-worker-");

        return executor;
    }
}