package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.service.AsyncTaskService;

import java.util.*;
import java.util.concurrent.*;

/**
 * Async Operations Controller
 * 
 * Demonstrates modern Spring Boot async patterns as alternatives to manual threading.
 * Shows practical use cases for @Async annotation.
 */
@RestController
@RequestMapping("/api/async")
@CrossOrigin(origins = "*")
public class AsyncOperationsController {
    
    @Autowired
    private AsyncTaskService asyncTaskService;
    
    /**
     * Fire-and-forget async task
     * POST /api/async/fire-and-forget
     * 
     * Returns immediately while task runs in background
     */
    @PostMapping("/fire-and-forget")
    public Map<String, Object> fireAndForget(@RequestBody Map<String, String> payload) {
        String taskName = payload.getOrDefault("taskName", "DefaultTask");
        
        // This returns immediately - task runs in background
        asyncTaskService.processTaskAsync(taskName);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "submitted");
        response.put("message", "Task '" + taskName + "' submitted for async processing");
        response.put("note", "Task runs in background. Check server logs for completion");
        response.put("concept", "Fire-and-forget: API returns immediately, work continues in background");
        
        return response;
    }
    
    /**
     * Async task with result
     * POST /api/async/with-result
     * 
     * Waits for result but processing happens asynchronously
     */
    @PostMapping("/with-result")
    public Map<String, Object> asyncWithResult(@RequestBody Map<String, Object> payload) 
            throws ExecutionException, InterruptedException {
        
        String taskName = (String) payload.getOrDefault("taskName", "TaskWithResult");
        int duration = (int) payload.getOrDefault("durationSeconds", 2);
        
        long startTime = System.currentTimeMillis();
        
        // Get CompletableFuture from async method
        CompletableFuture<String> future = asyncTaskService.processWithResult(taskName, duration);
        
        // Wait for result (this blocks, but processing is async)
        String result = future.get();
        
        long endTime = System.currentTimeMillis();
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "completed");
        response.put("taskName", taskName);
        response.put("result", result);
        response.put("totalTimeMs", endTime - startTime);
        response.put("concept", "Async with result: Processing happens in separate thread");
        
        return response;
    }
    
    /**
     * Process data asynchronously
     * POST /api/async/process-data
     * 
     * Real-world example: Process uploaded file without blocking
     */
    @PostMapping("/process-data")
    public Map<String, Object> processData(@RequestBody List<String> data) 
            throws ExecutionException, InterruptedException {
        
        long startTime = System.currentTimeMillis();
        
        // Process data asynchronously
        CompletableFuture<AsyncTaskService.ProcessingResult> future = 
            asyncTaskService.processDataAsync(data);
        
        // Wait for result
        AsyncTaskService.ProcessingResult result = future.get();
        
        long endTime = System.currentTimeMillis();
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "completed");
        response.put("totalItems", result.getTotalItems());
        response.put("successCount", result.getSuccessCount());
        response.put("errorCount", result.getErrorCount());
        response.put("processingTimeMs", result.getDurationMs());
        response.put("totalTimeMs", endTime - startTime);
        response.put("threadUsed", result.getThreadName());
        response.put("processedData", result.getProcessedData());
        response.put("concept", "Async data processing: Heavy computation in background thread");
        
        return response;
    }
    
    /**
     * Parallel processing demo
     * POST /api/async/parallel?taskCount=5
     * 
     * Shows how @Async enables easy parallel processing
     */
    @PostMapping("/parallel")
    public Map<String, Object> parallelProcessing(
            @RequestParam(defaultValue = "5") int taskCount) 
            throws ExecutionException, InterruptedException {
        
        long startTime = System.currentTimeMillis();
        
        // Process multiple tasks in parallel
        CompletableFuture<List<String>> future = 
            asyncTaskService.processMultipleTasksParallel(taskCount);
        
        // Wait for all to complete
        List<String> results = future.get();
        
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "completed");
        response.put("taskCount", taskCount);
        response.put("results", results);
        response.put("totalTimeMs", totalTime);
        response.put("averageTimePerTask", totalTime / taskCount);
        response.put("concept", "Parallel execution: Multiple tasks run simultaneously");
        response.put("note", "If run sequentially: " + (taskCount * 2000) + "ms. " +
                            "Parallel: ~2000ms regardless of task count!");
        
        return response;
    }
    
    /**
     * Combine multiple async operations
     * POST /api/async/combine
     * 
     * Real-world: Fetch data from multiple sources simultaneously
     */
    @PostMapping("/combine")
    public Map<String, Object> combineAsyncOperations() 
            throws ExecutionException, InterruptedException {
        
        long startTime = System.currentTimeMillis();
        
        // Launch multiple different async operations
        CompletableFuture<String> task1 = asyncTaskService.processWithResult("DatabaseQuery", 1);
        CompletableFuture<String> task2 = asyncTaskService.processWithResult("APICall", 2);
        CompletableFuture<String> task3 = asyncTaskService.processWithResult("CacheUpdate", 1);
        
        // Combine results
        CompletableFuture<Map<String, String>> combined = 
            CompletableFuture.allOf(task1, task2, task3)
                .thenApply(v -> {
                    Map<String, String> results = new HashMap<>();
                    results.put("database", task1.join());
                    results.put("api", task2.join());
                    results.put("cache", task3.join());
                    return results;
                });
        
        Map<String, String> results = combined.get();
        long endTime = System.currentTimeMillis();
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "completed");
        response.put("results", results);
        response.put("totalTimeMs", endTime - startTime);
        response.put("concept", "CompletableFuture.allOf(): Wait for multiple async operations");
        response.put("note", "All 3 operations ran in parallel. Total time ≈ longest task time");
        
        return response;
    }
    
    /**
     * Async with timeout
     * POST /api/async/with-timeout?timeoutSeconds=3
     * 
     * Shows how to handle timeouts in async operations
     */
    @PostMapping("/with-timeout")
    public Map<String, Object> asyncWithTimeout(
            @RequestParam(defaultValue = "3") int timeoutSeconds) {
        
        CompletableFuture<String> future = 
            asyncTaskService.processWithResult("TimeoutTask", 5); // Takes 5 seconds
        
        try {
            // Wait for result with timeout
            String result = future.get(timeoutSeconds, TimeUnit.SECONDS);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "completed");
            response.put("result", result);
            return response;
            
        } catch (TimeoutException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "timeout");
            response.put("message", "Task did not complete within " + timeoutSeconds + " seconds");
            response.put("concept", "Timeout handling: Prevent indefinite waiting");
            return response;
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return response;
        }
    }
    
    /**
     * Exception handling in async methods
     * POST /api/async/with-error
     * 
     * Shows how to handle errors in async processing
     */
    @PostMapping("/with-error")
    public Map<String, Object> asyncWithError() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // Simulate error
            if (Math.random() > 0.5) {
                throw new RuntimeException("Simulated processing error");
            }
            return "Success!";
        });
        
        // Handle error gracefully
        future = future.exceptionally(ex -> {
            System.err.println("Async error: " + ex.getMessage());
            return "Error occurred: " + ex.getMessage();
        });
        
        try {
            String result = future.get();
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", result.startsWith("Error") ? "error" : "success");
            response.put("result", result);
            response.put("concept", "Exception handling: Use exceptionally() to handle errors");
            return response;
            
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return response;
        }
    }
    
    /**
     * Get async method benefits summary
     * GET /api/async/benefits
     */
    @GetMapping("/benefits")
    public Map<String, Object> getAsyncBenefits() {
        Map<String, Object> response = new HashMap<>();
        
        response.put("title", "Spring Boot @Async vs Manual Threading");
        
        Map<String, List<String>> comparison = new HashMap<>();
        
        comparison.put("manualThreading", Arrays.asList(
            "new Thread(() -> {...}).start()",
            "Manual thread lifecycle management",
            "No thread pool reuse",
            "Complex error handling",
            "Difficult to test",
            "No transaction support"
        ));
        
        comparison.put("springAsync", Arrays.asList(
            "Just add @Async annotation",
            "Automatic thread pool management",
            "Thread reuse for better performance",
            "Built-in exception handling",
            "Easy to test (can be disabled)",
            "Transaction and security context propagation"
        ));
        
        response.put("comparison", comparison);
        
        response.put("useCases", Arrays.asList(
            "File processing (uploads, conversions)",
            "Email sending",
            "Report generation",
            "External API calls",
            "Data synchronization",
            "Background calculations",
            "Cache warming",
            "Batch operations"
        ));
        
        return response;
    }
}
