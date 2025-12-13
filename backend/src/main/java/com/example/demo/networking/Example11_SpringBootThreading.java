package com.example.demo.networking;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Example 11: Spring Boot Integration with Multithreading
 * 
 * Description:
 * This example demonstrates how Spring Boot can integrate with multithreading concepts.
 * Shows a conceptual approach for:
 * - Async processing using CompletableFuture
 * - Background task execution
 * - Thread pool management
 * 
 * Note: This is a teaching/mock example showing integration patterns.
 * In production, use @Async annotation with proper thread pool configuration.
 */

// ============================================
// Service Layer with Threading
// ============================================
@Service
class ThreadingDemoService {
    
    // Thread pool for executing background tasks
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);
    
    /**
     * Simulates a long-running task executed in a separate thread
     */
    public CompletableFuture<String> executeAsyncTask(String taskName, int durationSeconds) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("[Thread: " + Thread.currentThread().getName() + 
                                  "] Starting task: " + taskName);
                
                // Simulate processing
                Thread.sleep(durationSeconds * 1000L);
                
                System.out.println("[Thread: " + Thread.currentThread().getName() + 
                                  "] Completed task: " + taskName);
                
                return "Task '" + taskName + "' completed successfully in " + 
                       durationSeconds + " seconds";
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return "Task '" + taskName + "' was interrupted";
            }
        }, executorService);
    }
    
    /**
     * Executes multiple tasks in parallel and waits for all to complete
     */
    public List<String> executeParallelTasks(int taskCount) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        
        // Submit multiple tasks
        for (int i = 1; i <= taskCount; i++) {
            String taskName = "Task-" + i;
            CompletableFuture<String> future = executeAsyncTask(taskName, 2);
            futures.add(future);
        }
        
        // Wait for all tasks to complete
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(
            futures.toArray(new CompletableFuture[0])
        );
        
        // Block until all complete
        allTasks.join();
        
        // Collect results
        List<String> results = new ArrayList<>();
        for (CompletableFuture<String> future : futures) {
            results.add(future.join());
        }
        
        return results;
    }
    
    /**
     * Get current thread pool status
     */
    public Map<String, Object> getThreadPoolStatus() {
        Map<String, Object> status = new HashMap<>();
        
        if (executorService instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) executorService;
            status.put("poolSize", tpe.getPoolSize());
            status.put("activeThreads", tpe.getActiveCount());
            status.put("completedTasks", tpe.getCompletedTaskCount());
            status.put("queueSize", tpe.getQueue().size());
        }
        
        status.put("currentThread", Thread.currentThread().getName());
        
        return status;
    }
    
    /**
     * Cleanup method - should be called on application shutdown
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}

// ============================================
// REST Controller for Threading Demos
// ============================================
@RestController
@RequestMapping("/api/threading")
class ThreadingDemoController {
    
    @Autowired
    private ThreadingDemoService threadingService;
    
    /**
     * Endpoint to start an async task
     * GET /api/threading/async?taskName=MyTask&duration=3
     */
    @GetMapping("/async")
    public Map<String, String> startAsyncTask(
            @RequestParam(defaultValue = "DefaultTask") String taskName,
            @RequestParam(defaultValue = "2") int duration) {
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Task submitted for background execution");
        response.put("taskName", taskName);
        response.put("estimatedDuration", duration + " seconds");
        
        // Execute task asynchronously - doesn't block the response
        threadingService.executeAsyncTask(taskName, duration)
            .thenAccept(result -> System.out.println("Result: " + result));
        
        return response;
    }
    
    /**
     * Endpoint to execute parallel tasks
     * GET /api/threading/parallel?count=5
     */
    @GetMapping("/parallel")
    public Map<String, Object> executeParallelTasks(
            @RequestParam(defaultValue = "3") int count) {
        
        long startTime = System.currentTimeMillis();
        
        List<String> results = threadingService.executeParallelTasks(count);
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        Map<String, Object> response = new HashMap<>();
        response.put("tasksExecuted", count);
        response.put("results", results);
        response.put("totalTimeMs", totalTime);
        response.put("message", count + " tasks executed in parallel");
        
        return response;
    }
    
    /**
     * Endpoint to get thread pool status
     * GET /api/threading/status
     */
    @GetMapping("/status")
    public Map<String, Object> getThreadPoolStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("threadPoolStatus", threadingService.getThreadPoolStatus());
        response.put("availableProcessors", Runtime.getRuntime().availableProcessors());
        
        return response;
    }
    
    /**
     * Endpoint demonstrating synchronized access
     * GET /api/threading/counter
     */
    @GetMapping("/counter")
    public Map<String, Object> demonstrateCounter() throws InterruptedException {
        Counter counter = new Counter();
        
        // Create 10 threads that increment counter
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    counter.increment();
                }
            });
            threads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("expectedCount", 1000);
        response.put("actualCount", counter.getCount());
        response.put("synchronized", true);
        response.put("message", "10 threads × 100 increments each = 1000");
        
        return response;
    }
    
    // Simple synchronized counter
    private static class Counter {
        private int count = 0;
        
        public synchronized void increment() {
            count++;
        }
        
        public int getCount() {
            return count;
        }
    }
}

/**
 * USAGE INSTRUCTIONS FOR STUDENTS:
 * ================================
 * 
 * 1. Start the Spring Boot application (DemoApplication.java)
 * 
 * 2. Test endpoints using browser or curl:
 * 
 *    a) Start async task:
 *       http://localhost:8080/api/threading/async?taskName=TestTask&duration=3
 * 
 *    b) Execute parallel tasks:
 *       http://localhost:8080/api/threading/parallel?count=5
 * 
 *    c) Get thread pool status:
 *       http://localhost:8080/api/threading/status
 * 
 *    d) Test synchronized counter:
 *       http://localhost:8080/api/threading/counter
 * 
 * 3. Observe console output to see threading in action
 * 
 * KEY CONCEPTS DEMONSTRATED:
 * - ExecutorService for thread pool management
 * - CompletableFuture for async operations
 * - Parallel task execution
 * - Thread safety with synchronized methods
 * - Integration of threading concepts in REST APIs
 */
