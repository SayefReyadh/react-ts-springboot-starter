package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Async and Scheduling Configuration
 * 
 * Enables Spring Boot's @Async and @Scheduled annotations for:
 * - Asynchronous method execution (alternative to manual thread creation)
 * - Background task processing
 * - Scheduled/cron jobs
 * 
 * This is the MODERN way to handle threading in Spring Boot!
 */
@Configuration
@EnableAsync
@EnableScheduling
public class AsyncConfig {
    
    /**
     * Configure custom thread pool for @Async methods
     * 
     * Benefits over manual thread creation:
     * - Thread reuse (better performance)
     * - Configurable pool size
     * - Queue management
     * - Graceful shutdown
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        // Core pool size: minimum number of threads
        executor.setCorePoolSize(5);
        
        // Max pool size: maximum number of threads
        executor.setMaxPoolSize(10);
        
        // Queue capacity: tasks waiting for a thread
        executor.setQueueCapacity(100);
        
        // Thread name prefix (helps with debugging)
        executor.setThreadNamePrefix("Async-");
        
        // Rejection policy when queue is full
        // CallerRunsPolicy: caller's thread executes the task
        executor.setRejectedExecutionHandler(
            new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy()
        );
        
        // Wait for tasks to complete on shutdown
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        
        executor.initialize();
        return executor;
    }
    
    /**
     * Optional: Separate executor for scheduled tasks
     */
    @Bean(name = "scheduledExecutor")
    public Executor scheduledExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("Scheduled-");
        executor.initialize();
        return executor;
    }
}
