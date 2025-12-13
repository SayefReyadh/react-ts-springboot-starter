package com.example.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Async Service - Modern Threading in Spring Boot
 * 
 * Demonstrates @Async annotation - the Spring Boot way to handle threads.
 * 
 * Benefits over manual thread creation:
 * - Declarative (just add @Async annotation)
 * - Managed thread pool
 * - Exception handling
 * - Transaction management
 * - Easy testing
 */
@Service
public class AsyncTaskService {
    
    /**
     * Simple async method
     * 
     * Comparison:
     * - OLD WAY: new Thread(() -> doWork()).start()
     * - NEW WAY: Just call asyncMethod() - Spring handles threading
     */
    @Async("taskExecutor")
    public void processTaskAsync(String taskName) {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] Starting async task: " + taskName);
        
        try {
            // Simulate long-running task
            Thread.sleep(3000);
            
            System.out.println("[" + threadName + "] Completed task: " + taskName);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Task interrupted: " + taskName);
        }
    }
    
    /**
     * Async method with return value using CompletableFuture
     * 
     * Similar to our Example 11 but with Spring's @Async
     */
    @Async("taskExecutor")
    public CompletableFuture<String> processWithResult(String taskName, int durationSeconds) {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] Processing: " + taskName);
        
        try {
            Thread.sleep(durationSeconds * 1000L);
            String result = String.format("Task '%s' completed in %d seconds by %s", 
                                         taskName, durationSeconds, threadName);
            return CompletableFuture.completedFuture(result);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture("Task interrupted: " + taskName);
        }
    }
    
    /**
     * Simulate data processing (like file reading/writing)
     * 
     * Use case: Process uploaded files without blocking REST response
     */
    @Async("taskExecutor")
    public CompletableFuture<ProcessingResult> processDataAsync(List<String> data) {
        String threadName = Thread.currentThread().getName();
        long startTime = System.currentTimeMillis();
        
        System.out.println("[" + threadName + "] Processing " + data.size() + " items");
        
        List<String> processedData = new ArrayList<>();
        int successCount = 0;
        int errorCount = 0;
        
        for (String item : data) {
            try {
                // Simulate processing time
                Thread.sleep(100);
                
                // Simulate processing logic
                String processed = item.toUpperCase() + "_PROCESSED";
                processedData.add(processed);
                successCount++;
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                errorCount++;
            }
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        ProcessingResult result = new ProcessingResult();
        result.setTotalItems(data.size());
        result.setSuccessCount(successCount);
        result.setErrorCount(errorCount);
        result.setProcessedData(processedData);
        result.setDurationMs(duration);
        result.setThreadName(threadName);
        
        System.out.println("[" + threadName + "] Completed processing in " + duration + "ms");
        
        return CompletableFuture.completedFuture(result);
    }
    
    /**
     * Parallel processing example
     * 
     * Similar to Example 11's parallel execution but with @Async
     */
    public CompletableFuture<List<String>> processMultipleTasksParallel(int taskCount) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        
        // Launch all tasks in parallel
        for (int i = 1; i <= taskCount; i++) {
            CompletableFuture<String> future = processWithResult("ParallelTask-" + i, 2);
            futures.add(future);
        }
        
        // Wait for all to complete
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        );
        
        // Collect results
        return allTasks.thenApply(v -> 
            futures.stream()
                   .map(CompletableFuture::join)
                   .toList()
        );
    }
    
    /**
     * Scheduled task - runs automatically at fixed intervals
     * 
     * Comparison to manual threading:
     * - OLD WAY: Timer, TimerTask, or manual thread scheduling
     * - NEW WAY: Just add @Scheduled annotation
     * 
     * Use cases:
     * - Data cleanup jobs
     * - Report generation
     * - Health checks
     * - Cache refresh
     */
    @Scheduled(fixedRate = 30000) // Every 30 seconds
    public void scheduledTaskFixedRate() {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] Scheduled task (fixed rate) executed at " + 
                          LocalDateTime.now());
    }
    
    /**
     * Scheduled task with fixed delay
     * Waits for previous execution to complete before starting next
     */
    @Scheduled(fixedDelay = 60000, initialDelay = 5000) // Wait 5s, then every 60s after completion
    public void scheduledTaskFixedDelay() {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] Scheduled task (fixed delay) started at " + 
                          LocalDateTime.now());
        
        try {
            // Simulate work
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("[" + threadName + "] Scheduled task (fixed delay) completed");
    }
    
    /**
     * Cron-based scheduling
     * More flexible than fixed rate/delay
     */
    @Scheduled(cron = "0 */5 * * * *") // Every 5 minutes
    public void scheduledTaskCron() {
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] Cron-based task executed at " + 
                          LocalDateTime.now());
        
        // Example: Generate reports, clean old data, etc.
    }
    
    /**
     * Conditional scheduled task
     * Only runs if property is enabled
     */
    @Scheduled(fixedRate = 120000) // Every 2 minutes
    public void conditionalScheduledTask() {
        // In application.properties: scheduling.enabled=true
        String threadName = Thread.currentThread().getName();
        System.out.println("[" + threadName + "] Conditional scheduled task at " + 
                          LocalDateTime.now());
    }
    
    // ========== Result Model ==========
    
    public static class ProcessingResult {
        private int totalItems;
        private int successCount;
        private int errorCount;
        private List<String> processedData;
        private long durationMs;
        private String threadName;
        
        // Getters and setters
        public int getTotalItems() { return totalItems; }
        public void setTotalItems(int totalItems) { this.totalItems = totalItems; }
        public int getSuccessCount() { return successCount; }
        public void setSuccessCount(int successCount) { this.successCount = successCount; }
        public int getErrorCount() { return errorCount; }
        public void setErrorCount(int errorCount) { this.errorCount = errorCount; }
        public List<String> getProcessedData() { return processedData; }
        public void setProcessedData(List<String> processedData) { this.processedData = processedData; }
        public long getDurationMs() { return durationMs; }
        public void setDurationMs(long durationMs) { this.durationMs = durationMs; }
        public String getThreadName() { return threadName; }
        public void setThreadName(String threadName) { this.threadName = threadName; }
    }
}
