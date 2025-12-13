package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.demo.service.AsyncTaskService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

/**
 * Server-Sent Events (SSE) Controller
 * 
 * SSE is a one-way communication channel from server to client.
 * Perfect for: real-time updates, notifications, progress tracking
 * 
 * Comparison to alternatives:
 * - WebSocket: Bidirectional, more complex, better for chat
 * - SSE: Server-to-client only, simpler, built into browsers
 * - Polling: Inefficient, delays, high server load
 * 
 * Use cases:
 * - Live dashboards
 * - Progress bars for long-running tasks
 * - Notifications
 * - Stock prices/sports scores
 */
@RestController
@RequestMapping("/api/sse")
@CrossOrigin(origins = "*")
public class ServerSentEventsController {
    
    @Autowired
    private AsyncTaskService asyncTaskService;
    
    // Store active SSE connections
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    
    /**
     * Stream real-time updates
     * GET /api/sse/stream
     * 
     * Client connects and receives updates as they happen
     */
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamEvents(@RequestParam(defaultValue = "client") String clientId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE); // No timeout
        emitters.put(clientId, emitter);
        
        System.out.println("SSE client connected: " + clientId);
        
        // Handle completion and errors
        emitter.onCompletion(() -> {
            emitters.remove(clientId);
            System.out.println("SSE client disconnected: " + clientId);
        });
        
        emitter.onTimeout(() -> {
            emitters.remove(clientId);
            System.out.println("SSE client timeout: " + clientId);
        });
        
        emitter.onError((ex) -> {
            emitters.remove(clientId);
            System.err.println("SSE error for client " + clientId + ": " + ex.getMessage());
        });
        
        // Send initial connection message
        try {
            emitter.send(SseEmitter.event()
                .name("connected")
                .data("Connected to SSE stream. ClientId: " + clientId));
        } catch (IOException e) {
            emitter.completeWithError(e);
        }
        
        return emitter;
    }
    
    /**
     * Broadcast message to all connected clients
     * POST /api/sse/broadcast
     */
    @PostMapping("/broadcast")
    public Map<String, Object> broadcast(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        int successCount = 0;
        int failCount = 0;
        
        for (Map.Entry<String, SseEmitter> entry : emitters.entrySet()) {
            try {
                entry.getValue().send(SseEmitter.event()
                    .name("broadcast")
                    .data(Map.of(
                        "message", message,
                        "timestamp", LocalDateTime.now().toString(),
                        "type", "broadcast"
                    )));
                successCount++;
            } catch (IOException e) {
                failCount++;
                emitters.remove(entry.getKey());
            }
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", message);
        response.put("sentTo", successCount);
        response.put("failed", failCount);
        response.put("totalClients", emitters.size());
        
        return response;
    }
    
    /**
     * Send message to specific client
     * POST /api/sse/send/{clientId}
     */
    @PostMapping("/send/{clientId}")
    public Map<String, Object> sendToClient(
            @PathVariable String clientId,
            @RequestBody Map<String, String> payload) {
        
        Map<String, Object> response = new HashMap<>();
        SseEmitter emitter = emitters.get(clientId);
        
        if (emitter == null) {
            response.put("status", "error");
            response.put("message", "Client not connected: " + clientId);
            return response;
        }
        
        try {
            emitter.send(SseEmitter.event()
                .name("message")
                .data(Map.of(
                    "message", payload.get("message"),
                    "timestamp", LocalDateTime.now().toString(),
                    "type", "direct"
                )));
            
            response.put("status", "success");
            response.put("message", "Sent to client: " + clientId);
        } catch (IOException e) {
            emitters.remove(clientId);
            response.put("status", "error");
            response.put("message", "Failed to send: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Stream progress updates for a long-running task
     * GET /api/sse/progress/{taskId}
     * 
     * Perfect example of combining SSE with async processing
     */
    @GetMapping(value = "/progress/{taskId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter trackProgress(@PathVariable String taskId) {
        SseEmitter emitter = new SseEmitter(300000L); // 5 minute timeout
        
        // Simulate long-running task with progress updates
        executorService.execute(() -> {
            try {
                int totalSteps = 10;
                
                for (int i = 1; i <= totalSteps; i++) {
                    // Simulate work
                    Thread.sleep(1000);
                    
                    // Send progress update
                    int progress = (i * 100) / totalSteps;
                    emitter.send(SseEmitter.event()
                        .name("progress")
                        .data(Map.of(
                            "taskId", taskId,
                            "step", i,
                            "totalSteps", totalSteps,
                            "progress", progress,
                            "message", "Processing step " + i + " of " + totalSteps,
                            "timestamp", LocalDateTime.now().toString()
                        )));
                }
                
                // Send completion
                emitter.send(SseEmitter.event()
                    .name("complete")
                    .data(Map.of(
                        "taskId", taskId,
                        "status", "completed",
                        "message", "Task completed successfully!",
                        "timestamp", LocalDateTime.now().toString()
                    )));
                
                emitter.complete();
                
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        
        return emitter;
    }
    
    /**
     * Stream live counter (demonstrates continuous updates)
     * GET /api/sse/counter?duration=30
     */
    @GetMapping(value = "/counter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamCounter(@RequestParam(defaultValue = "30") int duration) {
        SseEmitter emitter = new SseEmitter((duration + 5) * 1000L);
        
        executorService.execute(() -> {
            try {
                for (int i = 1; i <= duration; i++) {
                    Thread.sleep(1000);
                    
                    emitter.send(SseEmitter.event()
                        .name("count")
                        .data(Map.of(
                            "count", i,
                            "total", duration,
                            "percentage", (i * 100) / duration,
                            "timestamp", LocalDateTime.now().toString()
                        )));
                }
                
                emitter.send(SseEmitter.event()
                    .name("finished")
                    .data("Counter finished!"));
                
                emitter.complete();
                
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        
        return emitter;
    }
    
    /**
     * Stream live data processing results
     * POST /api/sse/process-data
     */
    @PostMapping(value = "/process-data", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter processDataWithUpdates(@RequestBody List<String> data) {
        SseEmitter emitter = new SseEmitter(300000L);
        
        // Process data asynchronously and stream updates
        CompletableFuture<AsyncTaskService.ProcessingResult> future = 
            asyncTaskService.processDataAsync(data);
        
        // Send initial message
        try {
            emitter.send(SseEmitter.event()
                .name("started")
                .data(Map.of(
                    "message", "Processing started",
                    "totalItems", data.size()
                )));
        } catch (IOException e) {
            emitter.completeWithError(e);
            return emitter;
        }
        
        // When processing completes, send result
        future.thenAccept(result -> {
            try {
                emitter.send(SseEmitter.event()
                    .name("completed")
                    .data(result));
                emitter.complete();
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });
        
        return emitter;
    }
    
    /**
     * Get connected clients count
     * GET /api/sse/clients
     */
    @GetMapping("/clients")
    public Map<String, Object> getConnectedClients() {
        Map<String, Object> response = new HashMap<>();
        response.put("connectedClients", emitters.size());
        response.put("clientIds", new ArrayList<>(emitters.keySet()));
        return response;
    }
}
