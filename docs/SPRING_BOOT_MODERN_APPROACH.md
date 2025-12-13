# Modern Spring Boot: Threading, Sockets & Real-Time Communication

## Overview
This guide shows how to apply multithreading and socket programming concepts using modern Spring Boot features instead of manual thread and socket management.

---

## Table of Contents
1. [WebSocket (Modern Alternative to TCP Sockets)](#websocket)
2. [Server-Sent Events (SSE)](#server-sent-events)
3. [@Async Annotation (Modern Threading)](#async-annotation)
4. [@Scheduled Tasks (Automatic Background Jobs)](#scheduled-tasks)
5. [Comparison: Old vs New Approaches](#comparison)
6. [Complete API Reference](#api-reference)

---

## WebSocket

### What is WebSocket?
WebSocket provides **full-duplex communication** over a single TCP connection. It's the modern, production-ready alternative to raw TCP sockets.

### Why WebSocket over Raw Sockets?
- ✅ Browser-native support
- ✅ Automatic reconnection
- ✅ Message formatting (JSON)
- ✅ Built-in authentication/authorization
- ✅ Load balancing support
- ✅ Works through firewalls (uses HTTP upgrade)

### Configuration
**File**: `WebSocketConfig.java`
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    // Configured to use /ws endpoint with STOMP protocol
}
```

### Use Cases
- Real-time chat applications
- Live dashboards
- Multiplayer games
- Collaborative editing
- Live notifications

### Testing WebSocket

#### Using JavaScript Client:
```html
<!DOCTYPE html>
<html>
<head>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
</head>
<body>
    <script>
        // Connect to WebSocket
        const socket = new SockJS('http://localhost:8080/ws');
        const stompClient = Stomp.over(socket);
        
        stompClient.connect({}, function(frame) {
            console.log('Connected: ' + frame);
            
            // Subscribe to public messages
            stompClient.subscribe('/topic/messages', function(message) {
                console.log('Received:', JSON.parse(message.body));
            });
            
            // Send message
            stompClient.send('/app/chat', {}, JSON.stringify({
                sender: 'User1',
                content: 'Hello WebSocket!'
            }));
        });
    </script>
</body>
</html>
```

### REST Endpoints
- **POST** `/api/websocket/broadcast` - Broadcast to all clients
- **GET** `/api/websocket/users/count` - Get connected users

---

## Server-Sent Events (SSE)

### What is SSE?
SSE provides **one-way communication** from server to client. Perfect for streaming updates without the complexity of WebSocket.

### Why SSE?
- ✅ Simpler than WebSocket (built into browsers)
- ✅ Automatic reconnection
- ✅ Perfect for server-to-client updates
- ✅ No special protocols needed
- ✅ Works over regular HTTP

### Use Cases
- Live progress tracking
- Real-time notifications
- Live dashboards
- Stock tickers
- Log streaming

### Testing SSE

#### Using Browser:
```javascript
// Connect to SSE endpoint
const eventSource = new EventSource('http://localhost:8080/api/sse/stream?clientId=user123');

// Listen for messages
eventSource.addEventListener('connected', (e) => {
    console.log('Connected:', e.data);
});

eventSource.addEventListener('broadcast', (e) => {
    const data = JSON.parse(e.data);
    console.log('Broadcast:', data.message);
});

eventSource.onerror = (error) => {
    console.error('SSE Error:', error);
};
```

#### Using curl:
```bash
curl -N http://localhost:8080/api/sse/counter?duration=10
```

### REST Endpoints
- **GET** `/api/sse/stream?clientId=user1` - Connect to SSE stream
- **GET** `/api/sse/progress/{taskId}` - Track task progress
- **GET** `/api/sse/counter?duration=30` - Live counter stream
- **POST** `/api/sse/broadcast` - Broadcast to all SSE clients
- **POST** `/api/sse/send/{clientId}` - Send to specific client
- **GET** `/api/sse/clients` - Get connected clients count

---

## @Async Annotation

### What is @Async?
The modern, Spring Boot way to run methods asynchronously **without manually creating threads**.

### Comparison

#### ❌ Old Way (Manual Threading):
```java
new Thread(() -> {
    // Do work
    processTask();
}).start();
```

#### ✅ New Way (@Async):
```java
@Async
public void processTask() {
    // Do work - Spring handles threading automatically
}
```

### Benefits
- ✅ Declarative (just add annotation)
- ✅ Managed thread pool
- ✅ Thread reuse (better performance)
- ✅ Built-in exception handling
- ✅ Easy testing
- ✅ Transaction support

### Configuration
**File**: `AsyncConfig.java`
- Configures thread pool with 5-10 threads
- Custom thread naming: "Async-"
- Queue capacity: 100 tasks

### Use Cases
- File uploads/processing
- Email sending
- Report generation
- External API calls
- Data synchronization
- Background calculations
- Cache warming

### REST Endpoints
- **POST** `/api/async/fire-and-forget` - Submit task, return immediately
- **POST** `/api/async/with-result` - Wait for async result
- **POST** `/api/async/process-data` - Process data asynchronously
- **POST** `/api/async/parallel?taskCount=5` - Parallel processing
- **POST** `/api/async/combine` - Combine multiple async operations
- **POST** `/api/async/with-timeout?timeoutSeconds=3` - Async with timeout
- **GET** `/api/async/benefits` - Get comparison guide

---

## @Scheduled Tasks

### What is @Scheduled?
Automatically run methods at fixed intervals or specific times **without manual thread scheduling**.

### Comparison

#### ❌ Old Way:
```java
Timer timer = new Timer();
timer.scheduleAtFixedRate(new TimerTask() {
    public void run() {
        doWork();
    }
}, 0, 60000);
```

#### ✅ New Way:
```java
@Scheduled(fixedRate = 60000)
public void doWork() {
    // Runs automatically every 60 seconds
}
```

### Scheduling Types

#### 1. Fixed Rate
```java
@Scheduled(fixedRate = 30000) // Every 30 seconds
public void task() { }
```

#### 2. Fixed Delay
```java
@Scheduled(fixedDelay = 60000, initialDelay = 5000)
// Wait 5s, then 60s after each completion
public void task() { }
```

#### 3. Cron Expression
```java
@Scheduled(cron = "0 0 2 * * *") // Every day at 2 AM
public void task() { }
```

### Cron Examples
```
0 */5 * * * *     - Every 5 minutes
0 0 * * * *       - Every hour
0 0 0 * * *       - Every day at midnight
0 0 9 * * MON-FRI - Weekdays at 9 AM
0 0 0 1 * *       - First day of month
```

### Use Cases
- Data cleanup jobs
- Report generation
- Health checks
- Cache refresh
- Database backups
- Log rotation
- Data synchronization

---

## Comparison

### TCP Sockets vs WebSocket

| Feature | Raw TCP Socket | WebSocket |
|---------|---------------|-----------|
| Setup Complexity | High | Low |
| Browser Support | No | Yes |
| Message Format | Custom | JSON/Text |
| Authentication | Manual | Built-in |
| Reconnection | Manual | Automatic |
| Production Ready | Requires work | Yes |
| Use in Spring Boot | Manual code | @EnableWebSocketMessageBroker |

### Manual Threading vs @Async

| Feature | Manual Thread | @Async |
|---------|--------------|--------|
| Code | `new Thread(() -> {}).start()` | `@Async` annotation |
| Thread Pool | Manual | Automatic |
| Thread Reuse | No | Yes |
| Testing | Difficult | Easy |
| Exception Handling | Manual | Built-in |
| Transaction Support | No | Yes |

---

## API Reference

### WebSocket Endpoints
```
POST /api/websocket/broadcast
Body: {"message": "Hello everyone"}

GET  /api/websocket/users/count
```

### Server-Sent Events
```
GET  /api/sse/stream?clientId=user1
GET  /api/sse/progress/{taskId}
GET  /api/sse/counter?duration=30
POST /api/sse/broadcast
     Body: {"message": "Update"}
POST /api/sse/send/{clientId}
     Body: {"message": "Private message"}
GET  /api/sse/clients
```

### Async Operations
```
POST /api/async/fire-and-forget
     Body: {"taskName": "MyTask"}
     
POST /api/async/with-result
     Body: {"taskName": "Task1", "durationSeconds": 2}
     
POST /api/async/process-data
     Body: ["item1", "item2", "item3"]
     
POST /api/async/parallel?taskCount=5

POST /api/async/combine

POST /api/async/with-timeout?timeoutSeconds=3

GET  /api/async/benefits
```

### Original Threading Examples
```
GET  /api/threading/async?taskName=MyTask&duration=3
GET  /api/threading/parallel?count=5
GET  /api/threading/status
GET  /api/threading/counter
```

---

## Quick Start

### 1. Start Spring Boot Application
```bash
cd backend
mvn spring-boot:run
```

### 2. Test WebSocket
```bash
# Broadcast message
curl -X POST http://localhost:8080/api/websocket/broadcast \
  -H "Content-Type: application/json" \
  -d '{"message": "Hello WebSocket!"}'
```

### 3. Test SSE
```bash
# Stream live counter
curl -N http://localhost:8080/api/sse/counter?duration=10

# Track progress
curl -N http://localhost:8080/api/sse/progress/task123
```

### 4. Test @Async
```bash
# Fire and forget
curl -X POST http://localhost:8080/api/async/fire-and-forget \
  -H "Content-Type: application/json" \
  -d '{"taskName": "BackgroundTask"}'

# Parallel processing
curl -X POST http://localhost:8080/api/async/parallel?taskCount=5
```

---

## Production Considerations

### WebSocket
- Use external message broker (RabbitMQ, Redis) for clustering
- Implement authentication/authorization
- Add heartbeat/keepalive
- Handle reconnection gracefully
- Monitor connection count

### SSE
- Set appropriate timeout values
- Implement client reconnection logic
- Consider load balancing sticky sessions
- Monitor active connections
- Clean up disconnected clients

### @Async
- Configure thread pool size based on workload
- Set queue capacity appropriately
- Implement proper error handling
- Use @Transactional carefully with @Async
- Monitor thread pool metrics

### @Scheduled
- Use cron expressions for complex schedules
- Implement distributed locking for clustered apps
- Add execution monitoring
- Handle long-running tasks appropriately
- Consider using Quartz for advanced scheduling

---

## Summary

| Concept | Old Approach | Modern Spring Boot |
|---------|-------------|-------------------|
| **TCP Communication** | ServerSocket, Socket | WebSocket with @EnableWebSocketMessageBroker |
| **Server Push** | Polling, Long-polling | Server-Sent Events (SSE) |
| **Threading** | new Thread(), Runnable | @Async annotation |
| **Background Jobs** | Timer, TimerTask | @Scheduled annotation |
| **Thread Pools** | ExecutorService manual setup | ThreadPoolTaskExecutor with Spring |

**Key Takeaway**: Spring Boot provides production-ready alternatives to manual thread and socket management, with better performance, easier testing, and built-in best practices!
