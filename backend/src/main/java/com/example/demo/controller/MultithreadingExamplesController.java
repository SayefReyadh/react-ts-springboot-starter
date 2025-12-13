package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.AsyncTaskService;

import java.util.*;
import java.util.concurrent.*;

/**
 * Spring Boot Async Examples Controller
 * 
 * Demonstrates MODERN Spring Boot approaches to multithreading:
 * - @Async annotation (replaces manual thread creation)
 * - @Scheduled tasks (replaces Timer/TimerTask)
 * - CompletableFuture (for async results)
 * - ThreadPoolTaskExecutor (managed thread pools)
 * 
 * This is the PRODUCTION-READY way to handle concurrency in Spring Boot!
 */
@RestController
@RequestMapping("/api/examples/threading")
@CrossOrigin(origins = "*")
public class MultithreadingExamplesController {
    
    @Autowired
    private AsyncTaskService asyncTaskService;
    
    /**
     * Example 1: Fire-and-Forget Async Task
     * POST /api/examples/threading/fire-and-forget
     * 
     * Spring Boot Way: Use @Async annotation
     * Replaces: new Thread(() -> {...}).start()
     */
    @PostMapping("/fire-and-forget")
    public Map<String, Object> fireAndForget(@RequestBody Map<String, String> payload) {
        String taskName = payload.getOrDefault("taskName", "AsyncTask");
        
        // Task runs in background thread managed by Spring
        asyncTaskService.processTaskAsync(taskName);
        
        Map<String, Object> response = new HashMap<>();
        response.put("example", "Fire-and-Forget @Async");
        response.put("taskName", taskName);
        response.put("status", "submitted");
        response.put("concept", "Spring @Async replaces manual thread creation");
        response.put("modernApproach", "@Async annotation with managed thread pool");
        response.put("oldApproach", "new Thread(() -> {...}).start()");
        
        return response;
    }
    
    /**
     * Example 2: Async with Result using CompletableFuture
     * POST /api/examples/threading/with-result
     * 
     * Spring Boot Way: @Async method returns CompletableFuture
     */
    @PostMapping("/with-result")
    public Map<String, Object> asyncWithResult(@RequestBody Map<String, Object> payload) 
            throws ExecutionException, InterruptedException {
        
        String taskName = (String) payload.getOrDefault("taskName", "TaskWithResult");
        int duration = (int) payload.getOrDefault("durationSeconds", 2);
        
        long startTime = System.currentTimeMillis();
        
        // Get CompletableFuture from @Async method
        CompletableFuture<String> future = asyncTaskService.processWithResult(taskName, duration);
        String result = future.get();
        
        long totalTime = System.currentTimeMillis() - startTime;
        
        Map<String, Object> response = new HashMap<>();
        response.put("example", "@Async with CompletableFuture");
        response.put("result", result);
        response.put("totalTimeMs", totalTime);
        response.put("concept", "Async processing with return value");
        response.put("modernApproach", "@Async with CompletableFuture<T>");
        
        return response;
    }
    
    /**
     * Example 3: Parallel Processing
     * POST /api/examples/threading/parallel?taskCount=5
     * 
     * Spring Boot Way: Multiple @Async calls with CompletableFuture.allOf()
     */
    @PostMapping("/parallel")
    public Map<String, Object> parallelProcessing(
            @RequestParam(defaultValue = "5") int taskCount) 
            throws ExecutionException, InterruptedException {
        
        long startTime = System.currentTimeMillis();
        
        // Launch multiple async tasks in parallel
        CompletableFuture<List<String>> future = 
            asyncTaskService.processMultipleTasksParallel(taskCount);
        
        List<String> results = future.get();
        long totalTime = System.currentTimeMillis() - startTime;
        
        Map<String, Object> response = new HashMap<>();
        response.put("example", "Parallel Execution with @Async");
        response.put("taskCount", taskCount);
        response.put("results", results);
        response.put("totalTimeMs", totalTime);
        response.put("sequentialTimeWouldBe", taskCount * 2000 + "ms");
        response.put("concept", "Multiple async tasks run in parallel");
        response.put("modernApproach", "CompletableFuture.allOf() with @Async methods");
        
        return response;
    }
    
    /**
     * Example 4: Data Processing with Progress
     * POST /api/examples/threading/process-data
     * 
     * Real-world use case: Process file uploads, batch data, etc.
     */
    @PostMapping("/process-data")
    public Map<String, Object> processData(@RequestBody List<String> data) 
            throws ExecutionException, InterruptedException {
        
        long startTime = System.currentTimeMillis();
        
        // Process data asynchronously
        CompletableFuture<AsyncTaskService.ProcessingResult> future = 
            asyncTaskService.processDataAsync(data);
        
        AsyncTaskService.ProcessingResult result = future.get();
        
        Map<String, Object> response = new HashMap<>();
        response.put("example", "Async Data Processing");
        response.put("totalItems", result.getTotalItems());
        response.put("successCount", result.getSuccessCount());
        response.put("processingTimeMs", result.getDurationMs());
        response.put("threadUsed", result.getThreadName());
        response.put("concept", "Heavy processing in background thread");
        response.put("useCase", "File uploads, batch processing, data transformation");
        
        return response;
    }
    
    /**
     * Example 5: Scheduled Tasks Information
     * GET /api/examples/threading/scheduled-info
     * 
     * Shows information about @Scheduled tasks running in the application
     */
    @GetMapping("/scheduled-info")
    public Map<String, Object> getScheduledInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("example", "Scheduled Tasks with @Scheduled");
        
        List<Map<String, String>> tasks = new ArrayList<>();
        
        tasks.add(Map.of(
            "name", "Fixed Rate Task",
            "schedule", "@Scheduled(fixedRate = 30000)",
            "description", "Runs every 30 seconds",
            "useCase", "Health checks, metrics collection"
        ));
        
        tasks.add(Map.of(
            "name", "Fixed Delay Task",
            "schedule", "@Scheduled(fixedDelay = 60000)",
            "description", "Waits 60s after completion before next run",
            "useCase", "Data cleanup, log rotation"
        ));
        
        tasks.add(Map.of(
            "name", "Cron Task",
            "schedule", "@Scheduled(cron = \"0 */5 * * * *\")",
            "description", "Runs every 5 minutes using cron expression",
            "useCase", "Report generation, cache refresh"
        ));
        
        response.put("activeTasks", tasks);
        response.put("concept", "@Scheduled replaces Timer/TimerTask");
        response.put("modernApproach", "Declarative scheduling with annotations");
        response.put("oldApproach", "Timer, TimerTask, manual scheduling");
        
        return response;
    }
    
    /**
     * Example 6: Thread Pool Configuration Info
     * GET /api/examples/threading/thread-pool-info
     * 
     * Shows thread pool configuration (from AsyncConfig)
     */
    @GetMapping("/thread-pool-info")
    public Map<String, Object> getThreadPoolInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("example", "Spring ThreadPoolTaskExecutor");
        
        Map<String, Object> config = new HashMap<>();
        config.put("corePoolSize", 5);
        config.put("maxPoolSize", 10);
        config.put("queueCapacity", 100);
        config.put("threadNamePrefix", "Async-");
        config.put("configuredIn", "AsyncConfig.java");
        
        response.put("configuration", config);
        response.put("concept", "Managed thread pool vs manual ExecutorService");
        response.put("benefits", Arrays.asList(
            "Spring manages lifecycle",
            "Thread reuse for better performance",
            "Configurable queue and rejection policy",
            "Graceful shutdown on app stop",
            "Thread naming for debugging"
        ));
        
        return response;
    }
    
    /**
     * Example 7: Compare Old vs New Approaches
     * GET /api/examples/threading/comparison
     */
    @GetMapping("/comparison")
    public Map<String, Object> getComparison() {
        Map<String, Object> response = new HashMap<>();
        response.put("title", "Old Way vs Spring Boot Way");
        
        List<Map<String, String>> comparisons = new ArrayList<>();
        
        comparisons.add(Map.of(
            "task", "Run task in background",
            "oldWay", "new Thread(() -> doWork()).start()",
            "springBootWay", "@Async public void doWork()",
            "benefit", "Spring manages threads, better resource usage"
        ));
        
        comparisons.add(Map.of(
            "task", "Schedule recurring task",
            "oldWay", "Timer timer = new Timer(); timer.schedule(...)",
            "springBootWay", "@Scheduled(fixedRate = 60000) public void task()",
            "benefit", "Declarative, no manual timer management"
        ));
        
        comparisons.add(Map.of(
            "task", "Async with result",
            "oldWay", "ExecutorService + Future + manual cleanup",
            "springBootWay", "@Async public CompletableFuture<T> process()",
            "benefit", "Built-in CompletableFuture support, Spring manages executor"
        ));
        
        comparisons.add(Map.of(
            "task", "Parallel processing",
            "oldWay", "Manual thread creation + CountDownLatch",
            "springBootWay", "Multiple @Async calls + CompletableFuture.allOf()",
            "benefit", "Cleaner code, automatic thread pool management"
        ));
        
        response.put("comparisons", comparisons);
        
        return response;
    }
    
    /**
     * Get all Spring Boot threading patterns
     * GET /api/examples/threading/patterns
     */
    @GetMapping("/patterns")
    public Map<String, Object> getSpringBootPatterns() {
        Map<String, Object> response = new HashMap<>();
        response.put("title", "Spring Boot Threading Patterns");
        
        Map<String, Object> patterns = new HashMap<>();
        
        patterns.put("fireAndForget", Map.of(
            "pattern", "@Async void method()",
            "useCase", "Send emails, log events, update cache",
            "code", "@Async public void sendEmail(String to) { ... }"
        ));
        
        patterns.put("asyncWithResult", Map.of(
            "pattern", "@Async CompletableFuture<T> method()",
            "useCase", "API calls, database queries, file processing",
            "code", "@Async public CompletableFuture<Data> fetchData() { ... }"
        ));
        
        patterns.put("scheduledFixedRate", Map.of(
            "pattern", "@Scheduled(fixedRate = X) void method()",
            "useCase", "Periodic health checks, metrics collection",
            "code", "@Scheduled(fixedRate = 30000) public void healthCheck() { ... }"
        ));
        
        patterns.put("scheduledCron", Map.of(
            "pattern", "@Scheduled(cron = \"...\") void method()",
            "useCase", "Daily reports, scheduled cleanup",
            "code", "@Scheduled(cron = \"0 0 2 * * *\") public void dailyReport() { ... }"
        ));
        
        patterns.put("parallelProcessing", Map.of(
            "pattern", "Multiple @Async + CompletableFuture.allOf()",
            "useCase", "Aggregate data from multiple sources",
            "code", "CompletableFuture.allOf(future1, future2, future3).join()"
        ));
        
        response.put("patterns", patterns);
        
        return response;
    }
    
}
