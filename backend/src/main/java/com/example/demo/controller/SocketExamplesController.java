package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * WebSocket & SSE Guide Controller
 * 
 * Provides information about modern Spring Boot alternatives to raw TCP
 * sockets:
 * - WebSocket for bidirectional real-time communication
 * - Server-Sent Events (SSE) for server-to-client streaming
 * 
 * These are PRODUCTION-READY alternatives to raw socket programming!
 */
@RestController
@RequestMapping("/api/examples/sockets")
@CrossOrigin(origins = "*")
public class SocketExamplesController {

    /**
     * Get modern socket alternatives guide
     * GET /api/examples/sockets/modern
     */
    @GetMapping("/modern")
    public Map<String, Object> getModernSocketGuide() {
        Map<String, Object> response = new HashMap<>();
        response.put("title", "Modern Alternatives to Raw TCP Sockets");

        Map<String, Object> websocket = new HashMap<>();
        websocket.put("technology", "WebSocket");
        websocket.put("type", "Bidirectional, full-duplex communication");
        websocket.put("springBootSupport", "@EnableWebSocketMessageBroker + STOMP");
        websocket.put("useCases", Arrays.asList(
                "Real-time chat applications",
                "Live dashboards and monitoring",
                "Multiplayer games",
                "Collaborative editing",
                "Live notifications"));
        websocket.put("advantages", Arrays.asList(
                "Browser-native support",
                "Automatic reconnection",
                "JSON message formatting",
                "Built-in authentication",
                "Works through firewalls"));
        websocket.put("endpoints", Arrays.asList(
                "POST /api/websocket/broadcast - Broadcast to all clients",
                "GET /api/websocket/users/count - Get connected users"));
        websocket.put("clientDemo", "Open docs/websocket-demo.html in browser");

        Map<String, Object> sse = new HashMap<>();
        sse.put("technology", "Server-Sent Events (SSE)");
        sse.put("type", "Server-to-client, one-way streaming");
        sse.put("springBootSupport", "SseEmitter");
        sse.put("useCases", Arrays.asList(
                "Live progress tracking",
                "Real-time notifications",
                "Stock tickers / live scores",
                "Log streaming",
                "Live dashboards"));
        sse.put("advantages", Arrays.asList(
                "Simpler than WebSocket",
                "Built into browsers (EventSource API)",
                "Automatic reconnection",
                "Works over regular HTTP",
                "Perfect for one-way updates"));
        sse.put("endpoints", Arrays.asList(
                "GET /api/sse/stream - Connect to event stream",
                "GET /api/sse/progress/{taskId} - Track task progress",
                "GET /api/sse/counter - Live counter stream",
                "POST /api/sse/broadcast - Broadcast to all SSE clients"));
        sse.put("clientDemo", "Open docs/sse-demo.html in browser");

        response.put("webSocket", websocket);
        response.put("serverSentEvents", sse);

        return response;
    }

    /**
     * Compare raw sockets vs modern alternatives
     * GET /api/examples/sockets/comparison
     */
    @GetMapping("/comparison")
    public Map<String, Object> getComparison() {
        Map<String, Object> response = new HashMap<>();
        response.put("title", "Raw TCP Sockets vs Modern Spring Boot");

        List<Map<String, String>> comparisons = new ArrayList<>();

        comparisons.add(Map.of(
                "feature", "Server Setup",
                "rawSocket", "ServerSocket + manual thread per client",
                "webSocket", "@EnableWebSocketMessageBroker annotation",
                "sse", "Return SseEmitter from controller method"));

        comparisons.add(Map.of(
                "feature", "Client Support",
                "rawSocket", "Custom client code (Socket class)",
                "webSocket", "Native browser WebSocket API",
                "sse", "Native browser EventSource API"));

        comparisons.add(Map.of(
                "feature", "Message Format",
                "rawSocket", "Custom protocol (String, bytes)",
                "webSocket", "JSON with STOMP protocol",
                "sse", "JSON or text events"));

        comparisons.add(Map.of(
                "feature", "Reconnection",
                "rawSocket", "Manual implementation required",
                "webSocket", "Automatic with SockJS fallback",
                "sse", "Automatic browser reconnection"));

        comparisons.add(Map.of(
                "feature", "Authentication",
                "rawSocket", "Custom implementation",
                "webSocket", "Spring Security integration",
                "sse", "Spring Security integration"));

        comparisons.add(Map.of(
                "feature", "Production Ready",
                "rawSocket", "Requires significant work",
                "webSocket", "Yes, with load balancer support",
                "sse", "Yes"));

        response.put("comparisons", comparisons);

        Map<String, String> recommendation = new HashMap<>();
        recommendation.put("forChat", "Use WebSocket - bidirectional communication needed");
        recommendation.put("forNotifications", "Use SSE - simpler, one-way is sufficient");
        recommendation.put("forProgress", "Use SSE - perfect for progress updates");
        recommendation.put("forGaming", "Use WebSocket - low latency bidirectional needed");
        recommendation.put("forDashboards", "Use SSE or WebSocket depending on interaction");

        response.put("recommendations", recommendation);

        return response;
    }

    /**
     * Get WebSocket implementation details
     * GET /api/examples/sockets/websocket-details
     */
    @GetMapping("/websocket-details")
    public Map<String, Object> getWebSocketDetails() {
        Map<String, Object> response = new HashMap<>();
        response.put("technology", "WebSocket with STOMP");

        response.put("configuration", Map.of(
                "file", "WebSocketConfig.java",
                "annotation", "@EnableWebSocketMessageBroker",
                "endpoint", "/ws",
                "messageBroker", "/topic (broadcast), /queue (point-to-point)",
                "appPrefix", "/app (client to server)"));

        response.put("messagePatterns", Arrays.asList(
                Map.of(
                        "pattern", "@MessageMapping(\"/chat\") + @SendTo(\"/topic/messages\")",
                        "description", "Client sends to /app/chat, server broadcasts to /topic/messages",
                        "useCase", "Public chat messages"),
                Map.of(
                        "pattern", "@MessageMapping(\"/private\")",
                        "description", "Send to specific user via messagingTemplate.convertAndSendToUser()",
                        "useCase", "Private messages")));

        response.put("clientCode", Map.of(
                "connect", "const socket = new SockJS('/ws'); const client = Stomp.over(socket);",
                "subscribe", "client.subscribe('/topic/messages', callback);",
                "send", "client.send('/app/chat', {}, JSON.stringify(message));"));

        response.put("testing", Map.of(
                "htmlDemo", "docs/websocket-demo.html",
                "endpoints", Arrays.asList(
                        "POST /api/websocket/broadcast",
                        "GET /api/websocket/users/count")));

        return response;
    }

    /**
     * Get SSE implementation details
     * GET /api/examples/sockets/sse-details
     */
    @GetMapping("/sse-details")
    public Map<String, Object> getSseDetails() {
        Map<String, Object> response = new HashMap<>();
        response.put("technology", "Server-Sent Events (SSE)");

        response.put("springBootImplementation", Map.of(
                "controller", "ServerSentEventsController.java",
                "returnType", "SseEmitter",
                "produces", "MediaType.TEXT_EVENT_STREAM_VALUE"));

        response.put("patterns", Arrays.asList(
                Map.of(
                        "pattern", "Long-lived connection",
                        "code", "SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);",
                        "useCase", "Continuous streaming (notifications, monitoring)"),
                Map.of(
                        "pattern", "Task progress",
                        "code", "emitter.send(SseEmitter.event().name(\"progress\").data(data));",
                        "useCase", "Track long-running task progress"),
                Map.of(
                        "pattern", "Broadcast to all",
                        "code", "Store emitters in Map, iterate and send to all",
                        "useCase", "Server announcements, alerts")));

        response.put("clientCode", Map.of(
                "connect", "const eventSource = new EventSource('/api/sse/stream');",
                "listen", "eventSource.addEventListener('message', (e) => { ... });",
                "close", "eventSource.close();"));

        response.put("testing", Map.of(
                "htmlDemo", "docs/sse-demo.html",
                "curlTest", "curl -N http://localhost:8080/api/sse/counter?duration=10",
                "endpoints", Arrays.asList(
                        "GET /api/sse/stream",
                        "GET /api/sse/progress/{taskId}",
                        "GET /api/sse/counter",
                        "POST /api/sse/broadcast")));

        return response;
    }

    /**
     * Get quick start guide
     * GET /api/examples/sockets/quickstart
     */
    @GetMapping("/quickstart")
    public Map<String, Object> getQuickStart() {
        Map<String, Object> guide = new HashMap<>();
        guide.put("title", "Quick Start: WebSocket & SSE");

        List<Map<String, Object>> steps = new ArrayList<>();

        steps.add(Map.of(
                "step", 1,
                "title", "Test WebSocket",
                "action", "Open docs/websocket-demo.html in multiple browser tabs",
                "description", "Chat between tabs to see real-time communication"));

        steps.add(Map.of(
                "step", 2,
                "title", "Broadcast via REST",
                "action", "POST /api/websocket/broadcast with message",
                "description", "Send server announcements to all WebSocket clients",
                "curl",
                "curl -X POST http://localhost:8080/api/websocket/broadcast -H 'Content-Type: application/json' -d '{\"message\":\"Hello\"}'"));

        steps.add(Map.of(
                "step", 3,
                "title", "Test SSE",
                "action", "Open docs/sse-demo.html in browser",
                "description", "Try live counter and progress tracking"));

        steps.add(Map.of(
                "step", 4,
                "title", "Stream with curl",
                "action", "curl -N http://localhost:8080/api/sse/counter?duration=10",
                "description", "See server-sent events in terminal"));

        steps.add(Map.of(
                "step", 5,
                "title", "Track progress",
                "action", "GET /api/sse/progress/task123",
                "description", "Monitor a long-running task with progress updates"));

        guide.put("steps", steps);

        return guide;
    }

    /**
     * Get use case recommendations
     * GET /api/examples/sockets/use-cases
     */
    @GetMapping("/use-cases")
    public Map<String, Object> getUseCases() {
        Map<String, Object> response = new HashMap<>();

        response.put("webSocketUseCases", Arrays.asList(
                "Real-time chat applications",
                "Live collaborative editing (Google Docs style)",
                "Multiplayer game servers",
                "Live trading platforms",
                "Real-time dashboards with user interaction",
                "IoT device communication"));

        response.put("sseUseCases", Arrays.asList(
                "File upload progress tracking",
                "Live notification feed",
                "Server log streaming",
                "Stock price updates",
                "Sports score updates",
                "CI/CD build progress",
                "System monitoring dashboards"));

        response.put("whenToChooseWebSocket", Arrays.asList(
                "Need bidirectional communication",
                "Low latency required",
                "Client sends frequent updates",
                "Complex interaction patterns",
                "Gaming or real-time collaboration"));

        response.put("whenToChooseSse", Arrays.asList(
                "Only server sends updates to client",
                "Simpler implementation preferred",
                "Progress tracking or notifications",
                "One-way data streaming",
                "Don't need low latency (<1s is ok)"));

        return response;
    }
}
