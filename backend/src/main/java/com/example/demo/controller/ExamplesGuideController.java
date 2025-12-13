package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Examples Guide Controller
 * 
 * Provides a central dashboard/guide for all threading and networking examples
 * available in the Spring Boot application.
 */
@RestController
@RequestMapping("/api/examples")
@CrossOrigin(origins = "*")
public class ExamplesGuideController {
    
    /**
     * Get all available examples
     * GET /api/examples
     */
    @GetMapping
    public Map<String, Object> getAllExamples() {
        Map<String, Object> response = new HashMap<>();
        
        response.put("title", "Java Multithreading & Networking Examples");
        response.put("description", "Interactive demonstrations of threading and socket programming concepts");
        response.put("categories", Arrays.asList("Multithreading", "Socket Programming", "Integration"));
        
        Map<String, Object> examples = new HashMap<>();
        examples.put("multithreading", getMultithreadingExamples());
        examples.put("sockets", getSocketExamples());
        examples.put("integration", getIntegrationExamples());
        
        response.put("examples", examples);
        response.put("baseUrl", "http://localhost:8080/api/examples");
        
        return response;
    }
    
    /**
     * Get multithreading examples
     * GET /api/examples/threading/guide
     */
    @GetMapping("/threading/guide")
    public Map<String, Object> getMultithreadingExamples() {
        Map<String, Object> category = new HashMap<>();
        category.put("category", "Multithreading");
        category.put("description", "Examples demonstrating Java threading concepts");
        
        List<Map<String, String>> examples = new ArrayList<>();
        
        examples.add(createExample(
            "1", "Thread Class",
            "Creating threads by extending Thread class",
            "GET", "/api/examples/threading/thread-class",
            "Thread creation, start(), join()"
        ));
        
        examples.add(createExample(
            "2", "Runnable Interface",
            "Creating threads using Runnable interface",
            "GET", "/api/examples/threading/runnable",
            "Runnable, lambda expressions"
        ));
        
        examples.add(createExample(
            "3", "Thread Sleep",
            "Demonstrating Thread.sleep()",
            "GET", "/api/examples/threading/sleep",
            "sleep(), pausing execution"
        ));
        
        examples.add(createExample(
            "4", "Wait & Notify",
            "Producer-Consumer pattern with wait/notify",
            "GET", "/api/examples/threading/wait-notify",
            "wait(), notify(), inter-thread communication"
        ));
        
        examples.add(createExample(
            "5", "Race Condition",
            "Demonstrating race conditions and synchronization",
            "GET", "/api/examples/threading/race-condition?synchronized=false",
            "Race conditions, synchronized keyword"
        ));
        
        examples.add(createExample(
            "5b", "Race Condition (Fixed)",
            "Same example with synchronization enabled",
            "GET", "/api/examples/threading/race-condition?synchronized=true",
            "Thread safety, synchronization"
        ));
        
        examples.add(createExample(
            "6", "Synchronization Types",
            "Synchronized method vs synchronized block",
            "GET", "/api/examples/threading/synchronization-types",
            "synchronized method, synchronized block"
        ));
        
        examples.add(createExample(
            "7", "Async Execution",
            "CompletableFuture for async operations",
            "GET", "/api/examples/threading/async?taskName=MyTask&durationMs=2000",
            "CompletableFuture, async processing"
        ));
        
        category.put("examples", examples);
        return category;
    }
    
    /**
     * Get socket programming examples
     * GET /api/examples/sockets/guide
     */
    @GetMapping("/sockets/guide")
    public Map<String, Object> getSocketExamples() {
        Map<String, Object> category = new HashMap<>();
        category.put("category", "Socket Programming");
        category.put("description", "TCP/IP socket server examples");
        
        List<Map<String, String>> examples = new ArrayList<>();
        
        examples.add(createExample(
            "7", "Simple TCP Server",
            "Start a single-client TCP server",
            "POST", "/api/examples/sockets/simple-server/start?port=8081",
            "ServerSocket, single client handling"
        ));
        
        examples.add(createExample(
            "7b", "Stop Simple Server",
            "Stop the simple TCP server",
            "POST", "/api/examples/sockets/simple-server/stop",
            "Resource cleanup"
        ));
        
        examples.add(createExample(
            "8", "Multithreaded TCP Server",
            "Start a server that handles multiple clients",
            "POST", "/api/examples/sockets/multi-server/start?port=8082",
            "Multithreading, concurrent connections"
        ));
        
        examples.add(createExample(
            "8b", "Stop Multithreaded Server",
            "Stop the multithreaded TCP server",
            "POST", "/api/examples/sockets/multi-server/stop",
            "Server shutdown"
        ));
        
        examples.add(createExample(
            "9", "Server Status",
            "Check status of all socket servers",
            "GET", "/api/examples/sockets/status",
            "Server monitoring"
        ));
        
        examples.add(createExample(
            "10", "Connection Instructions",
            "How to connect to socket servers",
            "GET", "/api/examples/sockets/connect-instructions",
            "Client connection guide"
        ));
        
        category.put("examples", examples);
        category.put("note", "After starting a server, connect using telnet, netcat, or the Java client examples");
        
        return category;
    }
    
    /**
     * Get integration examples
     * GET /api/examples/integration/guide
     */
    @GetMapping("/integration/guide")
    public Map<String, Object> getIntegrationExamples() {
        Map<String, Object> category = new HashMap<>();
        category.put("category", "Spring Boot Integration");
        category.put("description", "Threading concepts integrated with Spring Boot");
        
        List<Map<String, String>> examples = new ArrayList<>();
        
        examples.add(createExample(
            "11", "Async Task",
            "Execute task asynchronously",
            "GET", "/api/threading/async?taskName=TestTask&duration=3",
            "ExecutorService, background processing"
        ));
        
        examples.add(createExample(
            "11b", "Parallel Execution",
            "Execute multiple tasks in parallel",
            "GET", "/api/threading/parallel?count=5",
            "Parallel processing, CompletableFuture.allOf()"
        ));
        
        examples.add(createExample(
            "11c", "Thread Pool Status",
            "Check thread pool status",
            "GET", "/api/threading/status",
            "Thread pool monitoring"
        ));
        
        examples.add(createExample(
            "11d", "Counter Demo",
            "Synchronized counter in REST endpoint",
            "GET", "/api/threading/counter",
            "Thread safety in REST APIs"
        ));
        
        category.put("examples", examples);
        category.put("note", "These examples show production-ready patterns for Spring Boot");
        
        return category;
    }
    
    /**
     * Get quick start guide
     * GET /api/examples/quickstart
     */
    @GetMapping("/quickstart")
    public Map<String, Object> getQuickStart() {
        Map<String, Object> guide = new HashMap<>();
        
        guide.put("title", "Quick Start Guide");
        
        List<Map<String, Object>> steps = new ArrayList<>();
        
        steps.add(Map.of(
            "step", 1,
            "action", "View all examples",
            "endpoint", "GET /api/examples",
            "description", "Get a complete list of all available examples"
        ));
        
        steps.add(Map.of(
            "step", 2,
            "action", "Try a simple threading example",
            "endpoint", "GET /api/examples/threading/thread-class",
            "description", "See threads in action with Thread class"
        ));
        
        steps.add(Map.of(
            "step", 3,
            "action", "Test race condition",
            "endpoint", "GET /api/examples/threading/race-condition?synchronized=false",
            "description", "See what happens without synchronization"
        ));
        
        steps.add(Map.of(
            "step", 4,
            "action", "Fix race condition",
            "endpoint", "GET /api/examples/threading/race-condition?synchronized=true",
            "description", "See the fix with synchronization"
        ));
        
        steps.add(Map.of(
            "step", 5,
            "action", "Start a socket server",
            "endpoint", "POST /api/examples/sockets/multi-server/start?port=8082",
            "description", "Start a multithreaded TCP server"
        ));
        
        steps.add(Map.of(
            "step", 6,
            "action", "Connect to server",
            "command", "telnet localhost 8082",
            "description", "Connect using telnet and type messages"
        ));
        
        steps.add(Map.of(
            "step", 7,
            "action", "Check server status",
            "endpoint", "GET /api/examples/sockets/status",
            "description", "View active servers and connections"
        ));
        
        guide.put("steps", steps);
        
        Map<String, String> tips = new HashMap<>();
        tips.put("browser", "Use browser for GET requests");
        tips.put("postman", "Use Postman or curl for POST requests");
        tips.put("telnet", "Use telnet/netcat for socket connections");
        tips.put("console", "Check server console for detailed logs");
        
        guide.put("tips", tips);
        
        return guide;
    }
    
    /**
     * Get concepts summary
     * GET /api/examples/concepts
     */
    @GetMapping("/concepts")
    public Map<String, Object> getConceptsSummary() {
        Map<String, Object> concepts = new HashMap<>();
        
        concepts.put("multithreading", Arrays.asList(
            "Thread class vs Runnable interface",
            "Thread lifecycle: start(), run(), sleep(), join()",
            "Inter-thread communication: wait(), notify()",
            "Synchronization: synchronized keyword",
            "Race conditions and thread safety",
            "Async execution with CompletableFuture"
        ));
        
        concepts.put("socketProgramming", Arrays.asList(
            "ServerSocket for listening on port",
            "Socket for client connections",
            "BufferedReader/PrintWriter for text communication",
            "Single client vs multiple clients",
            "Thread-per-client model",
            "Connection lifecycle management"
        ));
        
        concepts.put("integration", Arrays.asList(
            "ExecutorService for thread pools",
            "REST endpoints with async processing",
            "Background task execution",
            "Thread safety in Spring Boot",
            "Monitoring and status endpoints"
        ));
        
        return concepts;
    }
    
    // Helper method to create example descriptor
    private Map<String, String> createExample(String id, String name, String description, 
                                               String method, String endpoint, String concepts) {
        Map<String, String> example = new HashMap<>();
        example.put("id", id);
        example.put("name", name);
        example.put("description", description);
        example.put("method", method);
        example.put("endpoint", endpoint);
        example.put("concepts", concepts);
        return example;
    }
}
