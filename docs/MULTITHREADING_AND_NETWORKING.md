# Java Multithreading and TCP/IP Socket Programming Examples

## Overview
This document provides a guide to the teaching examples for multithreading and network programming in Java.

## Table of Contents

### Part 1: Multithreading Examples

#### Example 1: Creating Threads using Thread Class
**File**: `Example1_ThreadClass.java`

**Concepts Covered**:
- Extending Thread class
- Overriding run() method
- Starting threads with start()
- Thread.sleep() for pausing execution

**Run Command**:
```bash
javac backend/src/main/java/com/example/demo/threading/Example1_ThreadClass.java
java -cp backend/src/main/java com.example.demo.threading.Example1_ThreadClass
```

---

#### Example 2: Creating Threads using Runnable Interface
**File**: `Example2_RunnableInterface.java`

**Concepts Covered**:
- Implementing Runnable interface
- Passing Runnable to Thread constructor
- Lambda expressions for Runnable (Java 8+)
- Thread naming

**Run Command**:
```bash
javac backend/src/main/java/com/example/demo/threading/Example2_RunnableInterface.java
java -cp backend/src/main/java com.example.demo.threading.Example2_RunnableInterface
```

---

#### Example 3: Thread Lifecycle Methods
**File**: `Example3_ThreadLifecycleMethods.java`

**Concepts Covered**:
- Thread.sleep() - pauses thread execution
- Thread.yield() - hints scheduler to yield CPU
- Thread.join() - waits for thread completion

**Run Command**:
```bash
javac backend/src/main/java/com/example/demo/threading/Example3_ThreadLifecycleMethods.java
java -cp backend/src/main/java com.example.demo.threading.Example3_ThreadLifecycleMethods
```

---

#### Example 4: wait() and notify() Methods
**File**: `Example4_WaitNotify.java`

**Concepts Covered**:
- Inter-thread communication
- Producer-Consumer pattern
- wait() - releases lock and waits
- notify() - wakes up waiting thread
- Must be called within synchronized context

**Run Command**:
```bash
javac backend/src/main/java/com/example/demo/threading/Example4_WaitNotify.java
java -cp backend/src/main/java com.example.demo.threading.Example4_WaitNotify
```

---

#### Example 5: Race Condition and Solution
**File**: `Example5_RaceCondition.java`

**Concepts Covered**:
- Race condition problem
- Lost updates due to concurrent access
- Fixing race conditions with synchronization
- synchronized keyword for thread safety

**Run Command**:
```bash
javac backend/src/main/java/com/example/demo/threading/Example5_RaceCondition.java
java -cp backend/src/main/java com.example.demo.threading.Example5_RaceCondition
```

**Expected Output**:
- WITHOUT synchronization: Count < 5000 (lost updates)
- WITH synchronization: Count = 5000 (correct)

---

#### Example 6: Synchronized Method vs Block
**File**: `Example6_SynchronizationTypes.java`

**Concepts Covered**:
- synchronized method - locks entire method
- synchronized block - locks specific code section
- Custom lock objects
- Granular locking control

**Run Command**:
```bash
javac backend/src/main/java/com/example/demo/threading/Example6_SynchronizationTypes.java
java -cp backend/src/main/java com.example.demo.threading.Example6_SynchronizationTypes
```

---

### Part 2: TCP/IP Socket Programming

#### Example 7: Simple TCP Server
**File**: `Example7_SimpleTCPServer.java`

**Concepts Covered**:
- ServerSocket for listening
- Accepting client connections
- BufferedReader for reading text
- PrintWriter for sending text
- Socket communication basics

**Run Command** (Run FIRST):
```bash
javac backend/src/main/java/com/example/demo/networking/Example7_SimpleTCPServer.java
java -cp backend/src/main/java com.example.demo.networking.Example7_SimpleTCPServer
```

---

#### Example 8: Simple TCP Client
**File**: `Example8_SimpleTCPClient.java`

**Concepts Covered**:
- Socket for connecting to server
- Sending messages to server
- Receiving responses
- Console-based I/O
- Graceful connection closing

**Run Command** (Run AFTER server):
```bash
javac backend/src/main/java/com/example/demo/networking/Example8_SimpleTCPClient.java
java -cp backend/src/main/java com.example.demo.networking.Example8_SimpleTCPClient
```

**Testing**:
1. Start Example7_SimpleTCPServer
2. Start Example8_SimpleTCPClient
3. Type messages in client console
4. Type "bye" to disconnect

---

#### Example 9: Multithreaded TCP Server
**File**: `Example9_MultithreadedServer.java`

**Concepts Covered**:
- Handling multiple clients simultaneously
- Creating thread per client
- Runnable for client handlers
- AtomicInteger for thread-safe counting
- Concurrent connection management

**Run Command** (Run FIRST):
```bash
javac backend/src/main/java/com/example/demo/networking/Example9_MultithreadedServer.java
java -cp backend/src/main/java com.example.demo.networking.Example9_MultithreadedServer
```

**Special Features**:
- Accepts unlimited clients
- Each client gets dedicated thread
- Commands: 'time', 'info', 'bye'

---

#### Example 10: Client for Multithreaded Server
**File**: `Example10_ClientForMultithreadedServer.java`

**Concepts Covered**:
- Connecting to multithreaded server
- Special command processing
- Multiple client instances

**Run Command** (Run AFTER Example 9):
```bash
javac backend/src/main/java/com/example/demo/networking/Example10_ClientForMultithreadedServer.java
java -cp backend/src/main/java com.example.demo.networking.Example10_ClientForMultithreadedServer
```

**Testing Multiple Clients**:
1. Start Example9_MultithreadedServer
2. Open multiple terminals
3. Run Example10 in each terminal
4. Observe concurrent handling

---

### Part 3: Spring Boot Integration

#### Example 11: Spring Boot Threading Integration
**File**: `Example11_SpringBootThreading.java`

**Concepts Covered**:
- ExecutorService for thread pools
- CompletableFuture for async operations
- REST endpoints with threading
- Thread pool management
- Synchronized access in Spring

**Test Endpoints** (After starting Spring Boot app):

1. Async Task:
   ```
   http://localhost:8080/api/threading/async?taskName=MyTask&duration=3
   ```

2. Parallel Execution:
   ```
   http://localhost:8080/api/threading/parallel?count=5
   ```

3. Thread Pool Status:
   ```
   http://localhost:8080/api/threading/status
   ```

4. Synchronized Counter:
   ```
   http://localhost:8080/api/threading/counter
   ```

---

## Running Examples in Spring Boot Project

### Option 1: Run Individual Examples
Each example has a main() method and can run standalone:

```bash
# From project root
cd backend/src/main/java
javac com/example/demo/threading/Example1_ThreadClass.java
java com.example.demo.threading.Example1_ThreadClass
```

### Option 2: Run Spring Boot Application
For Example 11 (Spring Boot integration):

```bash
cd backend
mvn spring-boot:run
```

Then access endpoints via browser or curl.

---

## Teaching Tips

### For Multithreading:
1. Start with Example 1 & 2 (Thread creation basics)
2. Show Example 3 (lifecycle methods)
3. Demonstrate Example 5 (race condition) - run multiple times
4. Explain Example 6 (synchronization approaches)
5. Show Example 4 (wait/notify) for advanced students

### For Socket Programming:
1. Explain TCP/IP basics first
2. Demo Example 7 & 8 together (simple client-server)
3. Show Example 9 & 10 (multithreaded server)
4. Let students run multiple clients simultaneously

### For Spring Boot:
1. Show Example 11 endpoints
2. Monitor console for thread execution
3. Use parallel endpoint to show concurrent processing

---

## Common Issues and Solutions

### Issue: Port Already in Use
**Solution**: Change port in server code or kill existing process

```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

### Issue: Client Cannot Connect
**Solution**: 
- Ensure server is running first
- Check firewall settings
- Verify port number matches

### Issue: Race Condition Not Showing
**Solution**: Increase iteration count in Example 5

---

## Additional Resources

- Java Concurrency Tutorial: [Oracle Docs](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- Socket Programming: [Oracle Socket Tutorial](https://docs.oracle.com/javase/tutorial/networking/sockets/)
- Spring Async: [Spring Documentation](https://spring.io/guides/gs/async-method/)

---

## Spring Boot Integration

All examples are now integrated into the Spring Boot application via REST endpoints!

### Starting the Application

```bash
cd backend
mvn spring-boot:run
```

Once started, access examples at: `http://localhost:8080/api/examples`

### Available REST Endpoints

#### Main Guide
- **GET** `/api/examples` - List all available examples
- **GET** `/api/examples/quickstart` - Quick start guide
- **GET** `/api/examples/concepts` - Concepts summary

#### Multithreading Examples
- **GET** `/api/examples/threading/guide` - Threading examples guide
- **GET** `/api/examples/threading/thread-class` - Thread class demo
- **GET** `/api/examples/threading/runnable` - Runnable interface demo
- **GET** `/api/examples/threading/sleep` - Thread.sleep() demo
- **GET** `/api/examples/threading/wait-notify` - Producer-Consumer demo
- **GET** `/api/examples/threading/race-condition?synchronized=false` - Race condition demo
- **GET** `/api/examples/threading/race-condition?synchronized=true` - Fixed race condition
- **GET** `/api/examples/threading/synchronization-types` - Sync methods vs blocks
- **GET** `/api/examples/threading/async?taskName=MyTask&durationMs=2000` - Async execution

#### Socket Programming Examples
- **GET** `/api/examples/sockets/guide` - Socket examples guide
- **POST** `/api/examples/sockets/simple-server/start?port=8081` - Start simple server
- **POST** `/api/examples/sockets/simple-server/stop` - Stop simple server
- **POST** `/api/examples/sockets/multi-server/start?port=8082` - Start multithreaded server
- **POST** `/api/examples/sockets/multi-server/stop` - Stop multithreaded server
- **GET** `/api/examples/sockets/status` - Check server status
- **GET** `/api/examples/sockets/connect-instructions` - Connection guide

#### Spring Boot Integration Examples
- **GET** `/api/threading/async?taskName=TestTask&duration=3` - Async task
- **GET** `/api/threading/parallel?count=5` - Parallel execution
- **GET** `/api/threading/status` - Thread pool status
- **GET** `/api/threading/counter` - Synchronized counter

### Quick Test in Browser

1. Start the Spring Boot app: `mvn spring-boot:run`
2. Open browser and navigate to:
   - `http://localhost:8080/api/examples` - See all examples
   - `http://localhost:8080/api/examples/threading/thread-class` - Run threading demo
   - `http://localhost:8080/api/examples/threading/race-condition?synchronized=false` - See race condition

### Testing Socket Servers

1. Start multithreaded server:
   ```bash
   curl -X POST http://localhost:8080/api/examples/sockets/multi-server/start?port=8082
   ```

2. Connect with telnet:
   ```bash
   telnet localhost 8082
   ```

3. Type commands:
   - `time` - Get server time
   - `info` - Get client info
   - `bye` - Disconnect

4. Check status:
   ```bash
   curl http://localhost:8080/api/examples/sockets/status
   ```

### Using Postman or curl

**GET request:**
```bash
curl http://localhost:8080/api/examples/threading/thread-class
```

**POST request:**
```bash
curl -X POST http://localhost:8080/api/examples/sockets/multi-server/start?port=8082
```

---

## Summary

**Total Examples**: 11
- **Multithreading**: 6 examples
- **Socket Programming**: 4 examples  
- **Spring Boot Integration**: 1 example

**Total REST Controllers**: 3
- **MultithreadingExamplesController**: Interactive threading demos
- **SocketExamplesController**: Socket server management
- **ExamplesGuideController**: Central guide and documentation

All examples are:
- Fully runnable
- Well-commented
- Beginner-friendly
- Teaching-ready
- **Now accessible via REST APIs**
