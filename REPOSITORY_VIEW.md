# 🎓 React + TypeScript + Spring Boot Starter - Complete Repository Guide
## Advanced Object-Oriented Programming Laboratory - Week 2

> **Course:** CSE 2118 - Advanced OOP Lab
> **Instructor:** Sayef Reyadh
> **Institution:** United International University
> **Semester:** Fall 2025

---

## 📚 Table of Contents

1. [Project Overview](#project-overview)
2. [Technology Stack](#technology-stack)
3. [Repository Structure](#repository-structure)
4. [Backend Deep Dive](#backend-deep-dive)
5. [Frontend Deep Dive](#frontend-deep-dive)
6. [GitHub Actions CI/CD](#github-actions-cicd)
7. [Full-Stack Integration](#full-stack-integration)
8. [Development Workflow](#development-workflow)
9. [Deployment Architecture](#deployment-architecture)
10. [Learning Objectives](#learning-objectives)

---

## 🎯 Project Overview

### What is This Project?

This is a **full-stack starter template** demonstrating modern web application development using:
- **Backend**: Java Spring Boot (REST API)
- **Frontend**: React with TypeScript (User Interface)
- **CI/CD**: GitHub Actions (Automated Deployment)
- **Hosting**: Render (Backend) + Vercel (Frontend)

### Real-World Application Pattern

```
┌─────────────────────────────────────────────────────────────┐
│                    USER'S BROWSER                            │
│          https://react-ts-springboot.vercel.app             │
└─────────────────────────────────────────────────────────────┘
                          ↓ ↑
                   HTTP Requests/Responses
                          ↓ ↑
┌─────────────────────────────────────────────────────────────┐
│              REACT FRONTEND (Port 3000 - Dev)                │
│                                                              │
│  Components:                                                 │
│  - App.tsx (Main UI)                                        │
│  - Fetch data from API                                      │
│  - Display to user                                          │
│  - Handle user interactions                                  │
└─────────────────────────────────────────────────────────────┘
                          ↓ ↑
                    API Calls (fetch)
                          ↓ ↑
┌─────────────────────────────────────────────────────────────┐
│         SPRING BOOT BACKEND (Port 8080 - Dev)                │
│                                                              │
│  Layers:                                                     │
│  - Controller Layer (Handle HTTP requests)                   │
│  - Service Layer (Business logic)                           │
│  - Repository Layer (Data access)                           │
│  - Model Layer (Data structures)                            │
└─────────────────────────────────────────────────────────────┘
                          ↓ ↑
                   (Future: Database)
                          ↓ ↑
┌─────────────────────────────────────────────────────────────┐
│              DATABASE (MySQL/PostgreSQL)                    │                     
└─────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Technology Stack

### Backend Technologies

| Technology | Version | Purpose | Spring Boot Concept |
|-----------|---------|---------|-------------------|
| **Java** | 17 (LTS) | Programming language | N/A |
| **Spring Boot** | 3.1.4 | Web framework | Auto-configuration |
| **Spring Web** | Included | REST API support | Embedded Tomcat |
| **Maven** | 3.x | Build tool & dependency management | N/A |
| **Jackson** | Auto-configured | JSON serialization/deserialization | Auto-configuration |

**Key Spring Boot Concepts Demonstrated:**
- ✅ **Auto-Configuration**: Tomcat, Jackson, Spring MVC configured automatically
- ✅ **Component Scanning**: Automatic bean discovery
- ✅ **Dependency Injection**: @Autowired for loose coupling
- ✅ **Layered Architecture**: Controller-Service-Repository pattern
- ✅ **Embedded Server**: No separate Tomcat installation needed

### Frontend Technologies

| Technology | Version | Purpose |
|-----------|---------|---------|
| **React** | 18.2.0 | UI library (components, state management) |
| **TypeScript** | 5.3.3 | Type-safe JavaScript |
| **Vite** | 5.0.8 | Build tool (fast, modern) |
| **npm** | Latest | Package manager |

### DevOps & CI/CD

| Tool | Purpose |
|------|---------|
| **GitHub Actions** | Automated testing and deployment |
| **Render** | Backend hosting (Java runtime) |
| **Vercel** | Frontend hosting (CDN, serverless) |
| **Git** | Version control |

---

## 📁 Repository Structure

```
react-ts-springboot-starter/
│
├── .github/                              # GitHub Actions workflows
│   └── workflows/
│       ├── backend-deploy.yml           # Backend CI/CD pipeline
│       └── frontend-deploy.yml          # Frontend CI/CD pipeline
│
├── backend/                             # Spring Boot Application
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/demo/
│   │   │   │   ├── DemoApplication.java           # 🚀 Entry point
│   │   │   │   ├── controller/
│   │   │   │   │   └── HelloController.java       # 🎮 REST API endpoints
│   │   │   │   ├── service/                       # 💼 Business logic (future)
│   │   │   │   ├── repository/                    # 💾 Data access (future)
│   │   │   │   ├── model/                         # 📦 Data models (future)
│   │   │   │   └── config/
│   │   │   │       └── CorsConfig.java            # 🔧 CORS configuration
│   │   │   └── resources/
│   │   │       └── application.properties         # ⚙️ Application config
│   │   └── test/                                  # 🧪 Test code
│   ├── target/                                    # 📦 Compiled code (auto-generated)
│   ├── pom.xml                                    # 📋 Maven dependencies
│   ├── mvnw, mvnw.cmd                            # 🔨 Maven wrapper scripts
│   └── Dockerfile                                 # 🐳 Docker container config
│
├── frontend/                            # React Application
│   ├── src/
│   │   ├── App.tsx                      # 🎨 Main component
│   │   ├── main.tsx                     # 🚀 Entry point
│   │   └── App.css                      # 🎨 Styles
│   ├── public/                          # Static assets
│   ├── package.json                     # 📋 npm dependencies
│   ├── tsconfig.json                    # TypeScript config
│   ├── vite.config.ts                   # Vite build config
│   └── .env                             # Environment variables
│
├── render.yaml                          # Render deployment config
├── BACKEND_DEPLOYMENT_SETUP.md          # Backend deployment guide
├── FRONTEND_DEPLOYMENT_SETUP.md         # Frontend deployment guide
└── README.md                            # Project overview
```

---

## 🔧 Backend Deep Dive

### Spring Boot Core Architecture

```
┌─────────────────────────────────────────────────────────┐
│              @SpringBootApplication                     │
│                DemoApplication.java                     │
│                                                         │
│  What it does:                                          │
│  1. Enables Auto-Configuration                          │
│  2. Enables Component Scanning                          │
│  3. Marks as Configuration class                        │
│  4. Starts embedded Tomcat server                       │
└─────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│          Spring IoC Container (Application Context)     │
│                                                         │
│  Responsibilities:                                      │
│  - Creates and manages all @Component, @Service, etc.   │
│  - Performs dependency injection                        │
│  - Manages bean lifecycle                               │
│  - Provides beans when requested                        │
└─────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│              Component Scanning                         │
│                                                         │
│  Spring scans: com.example.demo (and sub-packages)      │
│  Finds:                                                 │
│  - @RestController → HelloController                    │
│  - @Configuration → CorsConfig                          │
│  - @Service → (none yet)                                │
│  - @Repository → (none yet)                             │
└─────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│              Beans Created and Ready                    │
│                                                         │
│  HelloController bean → Handles /api/hello              │
│  CorsConfig bean → Configures CORS settings             │
└─────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│              Embedded Tomcat Starts                     │
│              Listening on port 8080                     │
│              Application ready to serve requests!       │
└─────────────────────────────────────────────────────────┘
```

### 1. DemoApplication.java - The Entry Point

**Location:** `backend/src/main/java/com/example/demo/DemoApplication.java`

**Purpose:** Application's main entry point - where everything starts

**Spring Boot Concepts:**
- **@SpringBootApplication**: Meta-annotation combining 3 annotations
  - `@Configuration`: Marks class as source of bean definitions
  - `@EnableAutoConfiguration`: Enables Spring Boot's magic
  - `@ComponentScan`: Scans for components in package and sub-packages

**Code Breakdown:**
```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 🎯 @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
// This single annotation does A LOT:
// 1. Enables auto-configuration (Tomcat, Jackson, Spring MVC, etc.)
// 2. Scans this package and sub-packages for components
// 3. Marks this as a configuration class
@SpringBootApplication
public class DemoApplication {

    // 🚀 main() method - Java application entry point
    // When you run: mvn spring-boot:run
    // Or: java -jar target/demo.jar
    // This method executes first
    public static void main(String[] args) {
        // SpringApplication.run() does the following:
        // 1. Creates Spring ApplicationContext (IoC Container)
        // 2. Scans for @Component, @Service, @Controller, etc.
        // 3. Creates beans and injects dependencies
        // 4. Configures embedded Tomcat server
        // 5. Starts the server on port 8080 (default)
        // 6. Application ready to handle HTTP requests!
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

**What Happens When Application Starts:**

```
1. JVM starts
   ↓
2. main() method called
   ↓
3. SpringApplication.run() executed
   ↓
4. Spring Boot Auto-Configuration begins
   - Detects spring-boot-starter-web → Configures Tomcat + Spring MVC
   - Detects no database starter → Skips database config
   - Detects Jackson on classpath → Configures JSON converter
   ↓
5. Component Scanning
   - Scans: com.example.demo and all sub-packages
   - Finds: HelloController (marked with @RestController)
   - Finds: CorsConfig (marked with @Configuration)
   ↓
6. Bean Creation & Dependency Injection
   - Creates HelloController bean
   - Creates CorsConfig bean
   - Injects dependencies (none in this simple app)
   ↓
7. Embedded Tomcat Server Starts
   - Binds to port 8080
   - Ready to handle HTTP requests
   ↓
8. Application Ready!
   - Console shows: "Started DemoApplication in X seconds"
   - Endpoints available at http://localhost:8080/
```

### 2. HelloController.java - REST API Endpoint

**Location:** `backend/src/main/java/com/example/demo/controller/HelloController.java`

**Purpose:** Handles HTTP requests and returns responses (REST API)

**Spring Boot Concepts:**
- **@RestController**: Combines @Controller + @ResponseBody
- **@GetMapping**: Maps HTTP GET requests to method
- **Component Scanning**: Spring finds this and creates a bean
- **Auto-Configuration**: Jackson converts return value to JSON

**Code Breakdown:**
```java
package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

// 🎮 @RestController: Marks this class as a REST API controller
// Spring Boot Concepts:
// 1. Component Scanning: Spring finds this annotation and creates a bean
// 2. @RestController = @Controller + @ResponseBody
//    - @Controller: Makes this a Spring MVC controller
//    - @ResponseBody: Return values are automatically written to HTTP response body
// 3. Auto-Configuration: Spring MVC is auto-configured to handle REST requests
@RestController
public class HelloController {

    // 🗺️ @GetMapping: Maps HTTP GET requests to this method
    // When user visits: http://localhost:8080/api/hello
    // This method is called
    //
    // Spring Boot Concepts:
    // 1. Request Mapping: Spring MVC routes /api/hello to this method
    // 2. Auto-Configuration: DispatcherServlet handles routing automatically
    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        // 📦 Return a Map (key-value pairs)
        // Spring Boot's Auto-Configuration magic:
        // 1. Jackson (JSON library) is auto-configured
        // 2. Map is automatically converted to JSON
        // 3. HTTP Response:
        //    - Status: 200 OK
        //    - Content-Type: application/json
        //    - Body: {"message":"Hello World"}
        return Map.of("message", "Hello World");
    }

    // 💡 How this works end-to-end:
    //
    // 1. User/Frontend makes request: GET http://localhost:8080/api/hello
    // 2. Embedded Tomcat receives the request
    // 3. DispatcherServlet (Spring MVC) routes to this method
    // 4. Method executes and returns Map
    // 5. Jackson converts Map to JSON string
    // 6. Spring MVC sends HTTP response with JSON
    // 7. User/Frontend receives: {"message":"Hello World"}
}
```

**Request-Response Flow:**

```
CLIENT REQUEST:
───────────────
GET http://localhost:8080/api/hello
Accept: application/json

        ↓

EMBEDDED TOMCAT (Port 8080)
───────────────────────────
Receives HTTP request

        ↓

DISPATCHER SERVLET (Spring MVC)
────────────────────────────────
Routes /api/hello to HelloController.hello()

        ↓

HELLO CONTROLLER
────────────────
@GetMapping("/api/hello")
hello() method executes
Returns: Map.of("message", "Hello World")

        ↓

JACKSON (JSON Converter)
────────────────────────
Converts Map to JSON string
Result: {"message":"Hello World"}

        ↓

HTTP RESPONSE:
──────────────
HTTP/1.1 200 OK
Content-Type: application/json

{"message":"Hello World"}

        ↓

CLIENT RECEIVES RESPONSE
```

### 3. CorsConfig.java - Cross-Origin Configuration

**Location:** `backend/src/main/java/com/example/demo/config/CorsConfig.java`

**Purpose:** Allows frontend (different domain) to access backend API

**Spring Boot Concepts:**
- **@Configuration**: Marks class as configuration source
- **WebMvcConfigurer**: Interface to customize Spring MVC
- **Component Scanning**: Spring finds and processes this

**What is CORS?**

```
❌ WITHOUT CORS:
─────────────────
Frontend: https://myapp.vercel.app
Backend:  https://myapi.render.com

Frontend tries to fetch: https://myapi.render.com/api/hello
Browser: BLOCKED! "Cross-Origin Request Blocked"

Problem: Browser security prevents one domain from accessing another


✅ WITH CORS:
─────────────
Frontend: https://myapp.vercel.app
Backend:  https://myapi.render.com (with CORS enabled)

Backend sends response with header:
Access-Control-Allow-Origin: *

Browser: OK! Cross-origin request allowed
Frontend receives data successfully
```

**Code Breakdown:**
```java
package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 🔧 @Configuration: Marks this as a configuration class
// Spring Boot Concepts:
// 1. Component Scanning: Spring finds @Configuration classes
// 2. Bean Definitions: @Configuration classes can define @Bean methods
// 3. Configuration Processing: Applied during application startup
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // 🌐 Override addCorsMappings to configure CORS globally
    // Spring Boot Concept: WebMvcConfigurer interface allows customizing Spring MVC
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configure CORS for all endpoints
        registry.addMapping("/**")              // Apply to all paths (/** = all URLs)
                .allowedOrigins("*")             // Allow requests from ANY domain
                .allowedMethods("*")             // Allow all HTTP methods (GET, POST, PUT, DELETE)
                .allowedHeaders("*");            // Allow all headers

        // 📚 What this means:
        // - Frontend at https://myapp.vercel.app can call our API
        // - Frontend at http://localhost:3000 can call our API
        // - Any domain can call our API (good for learning, review for production)

        // 🔒 Production Considerations:
        // Instead of "*", specify exact domains:
        // .allowedOrigins("https://myapp.vercel.app", "http://localhost:3000")
    }

    // 💡 How CORS works:
    //
    // 1. Frontend sends request to backend (different domain)
    // 2. Browser sends "preflight" request (OPTIONS method)
    // 3. Backend responds with CORS headers (Access-Control-Allow-Origin)
    // 4. Browser checks headers and allows/blocks request
    // 5. If allowed, actual request (GET/POST) is sent
    // 6. Backend responds with data + CORS headers
    // 7. Frontend receives data
}
```

### 4. application.properties - Configuration File

**Location:** `backend/src/main/resources/application.properties`

**Purpose:** Centralized configuration for the application

**Spring Boot Concept:** Externalized Configuration

**Code Breakdown:**
```properties
# ═══════════════════════════════════════════════════════════
# SPRING BOOT APPLICATION CONFIGURATION
# ═══════════════════════════════════════════════════════════
# Spring Boot Concept: Externalized Configuration
# - Configure application without changing code
# - Different configs for dev, test, prod (using profiles)
# - Override with environment variables or command-line args
# ═══════════════════════════════════════════════════════════

# ───────────────────────────────────────────────────────────
# SERVER CONFIGURATION
# ───────────────────────────────────────────────────────────
# Spring Boot Concept: Embedded Server Auto-Configuration
# Spring Boot automatically configures Tomcat server
# Default port is 8080 (can be changed here)
server.port=8080

# 💡 How to change port:
# server.port=9090
# Then access at: http://localhost:9090

# ───────────────────────────────────────────────────────────
# APPLICATION METADATA
# ───────────────────────────────────────────────────────────
# Application name (used in logs, monitoring, etc.)
spring.application.name=springboot-backend

# 💡 Spring Boot Auto-Configuration uses this in:
# - Logging
# - Spring Boot Actuator
# - Application monitoring tools

# ═══════════════════════════════════════════════════════════
# FUTURE CONFIGURATIONS (Week 3+)
# ═══════════════════════════════════════════════════════════

# ───────────────────────────────────────────────────────────
# DATABASE CONFIGURATION (Coming in Week 3)
# ───────────────────────────────────────────────────────────
# spring.datasource.url=jdbc:mysql://localhost:3306/studentdb
# spring.datasource.username=root
# spring.datasource.password=password
# spring.jpa.hibernate.ddl-auto=update
# spring.jpa.show-sql=true

# ───────────────────────────────────────────────────────────
# LOGGING CONFIGURATION
# ───────────────────────────────────────────────────────────
# logging.level.root=INFO
# logging.level.com.example.demo=DEBUG
# logging.file.name=application.log

# ───────────────────────────────────────────────────────────
# PROFILE-SPECIFIC CONFIGURATION
# ───────────────────────────────────────────────────────────
# spring.profiles.active=dev
# Create: application-dev.properties, application-prod.properties

# ═══════════════════════════════════════════════════════════
# KEY SPRING BOOT CONCEPTS DEMONSTRATED:
# ═══════════════════════════════════════════════════════════
# 1. Auto-Configuration: Tomcat configured automatically
# 2. Convention over Configuration: Sensible defaults
# 3. Externalized Configuration: Easy to change without code
# 4. Profile Support: Different configs for different environments
# ═══════════════════════════════════════════════════════════
```

### 5. pom.xml - Maven Configuration

**Location:** `backend/pom.xml`

**Purpose:** Defines project dependencies and build configuration

**Build Tool Concept:** Maven dependency management

**Key Sections:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">

    <!-- ═══════════════════════════════════════════════════════ -->
    <!-- PROJECT METADATA (GAV Coordinates)                        -->
    <!-- ═══════════════════════════════════════════════════════ -->
    <groupId>com.example</groupId>          <!-- Company/org identifier -->
    <artifactId>demo</artifactId>            <!-- Project name -->
    <version>0.0.1-SNAPSHOT</version>        <!-- Project version -->
    <name>springboot-backend</name>          <!-- Human-readable name -->

    <!-- ═══════════════════════════════════════════════════════ -->
    <!-- SPRING BOOT PARENT                                        -->
    <!-- ═══════════════════════════════════════════════════════ -->
    <!-- Spring Boot Concept: Starter Parent
         - Provides dependency management (no need to specify versions)
         - Provides sensible defaults
         - Provides Maven plugin configuration
    -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.4</version>
    </parent>

    <!-- ═══════════════════════════════════════════════════════ -->
    <!-- PROPERTIES                                                -->
    <!-- ═══════════════════════════════════════════════════════ -->
    <properties>
        <java.version>17</java.version>      <!-- Java version -->
    </properties>

    <!-- ═══════════════════════════════════════════════════════ -->
    <!-- DEPENDENCIES                                              -->
    <!-- ═══════════════════════════════════════════════════════ -->
    <dependencies>
        <!-- Spring Boot Starter Web
             What it includes (transitively):
             - spring-boot-starter (core)
             - spring-boot-starter-tomcat (embedded server)
             - spring-webmvc (Spring MVC)
             - jackson (JSON converter)

             Spring Boot Concepts:
             - Auto-Configuration: Configures all of above automatically
             - Starter Dependencies: Pre-packaged dependency bundles
        -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <!-- No version needed - inherited from parent -->
        </dependency>
    </dependencies>

    <!-- ═══════════════════════════════════════════════════════ -->
    <!-- BUILD CONFIGURATION                                       -->
    <!-- ═══════════════════════════════════════════════════════ -->
    <build>
        <plugins>
            <!-- Spring Boot Maven Plugin
                 Provides:
                 - mvn spring-boot:run (run application)
                 - mvn package (creates executable JAR)
                 - Embedded server in JAR
            -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

**Maven Commands:**
```bash
# Install dependencies
mvn install

# Run application
mvn spring-boot:run

# Package as JAR
mvn clean package
# Output: target/demo-0.0.1-SNAPSHOT.jar

# Run packaged JAR
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

---

## 🎨 Frontend Deep Dive

### Frontend Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    USER'S BROWSER                        │
│              Renders React Components                    │
└─────────────────────────────────────────────────────────┘
                         ↓ ↑
                   User Interactions
                         ↓ ↑
┌─────────────────────────────────────────────────────────┐
│                   REACT APPLICATION                      │
│                                                          │
│  main.tsx                                               │
│  - Entry point                                          │
│  - Renders <App /> component                            │
│                                                          │
│  App.tsx                                                │
│  - Main component                                        │
│  - Manages state (useState)                             │
│  - Fetches data from API (useEffect)                    │
│  - Displays UI                                          │
└─────────────────────────────────────────────────────────┘
                         ↓ ↑
                   fetch() API calls
                         ↓ ↑
┌─────────────────────────────────────────────────────────┐
│                   VITE DEV SERVER                        │
│                                                          │
│  - Hot Module Replacement (HMR)                         │
│  - Proxy /api/* to backend                              │
│  - Fast rebuild on file changes                         │
└─────────────────────────────────────────────────────────┘
                         ↓ ↑
              Proxied requests to backend
                         ↓ ↑
┌─────────────────────────────────────────────────────────┐
│              SPRING BOOT BACKEND                         │
│          http://localhost:8080/api/hello                 │
└─────────────────────────────────────────────────────────┘
```

### 1. main.tsx - Frontend Entry Point

**Location:** `frontend/src/main.tsx`

**Purpose:** Application entry point - mounts React to DOM

**Code Breakdown:**
```typescript
// ═══════════════════════════════════════════════════════════
// REACT APPLICATION ENTRY POINT
// ═══════════════════════════════════════════════════════════
// This is where the React application starts
// Similar to: public static void main() in Java
// ═══════════════════════════════════════════════════════════

// React 18 - Core library
import React from 'react'
// ReactDOM - Renders React components to browser DOM
import ReactDOM from 'react-dom/client'
// Main App component
import App from './App.tsx'

// 🚀 Render React application to DOM
// React.StrictMode: Development mode checks
// - Helps find common bugs
// - Warns about deprecated APIs
// - Only runs in development (not production)
ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)

// 💡 What happens here:
// 1. Find HTML element with id="root" (in index.html)
// 2. Create React root
// 3. Render <App /> component inside root
// 4. App component manages entire application
```

### 2. App.tsx - Main Component

**Location:** `frontend/src/App.tsx`

**Purpose:** Main UI component - fetches and displays backend data

**Code Breakdown:**
```typescript
// ═══════════════════════════════════════════════════════════
// MAIN APPLICATION COMPONENT
// ═══════════════════════════════════════════════════════════
// This component:
// 1. Fetches data from Spring Boot backend
// 2. Manages state (message from backend)
// 3. Displays UI to user
// ═══════════════════════════════════════════════════════════

// React Hooks - for state and side effects
import { useState, useEffect } from 'react'
// Styles
import './App.css'

function App() {
  // ─────────────────────────────────────────────────────────
  // STATE MANAGEMENT
  // ─────────────────────────────────────────────────────────
  // useState Hook: Manages component state
  // Similar to: private String message = "Loading..."; in Java
  // But React re-renders when state changes!
  //
  // message: Current value
  // setMessage: Function to update value
  // <string>: TypeScript type annotation
  const [message, setMessage] = useState<string>('Loading...')

  // ─────────────────────────────────────────────────────────
  // SIDE EFFECTS (API CALL)
  // ─────────────────────────────────────────────────────────
  // useEffect Hook: Runs code after component renders
  // Similar to: @PostConstruct in Spring Boot
  //
  // Empty array [] means: run only once (on mount)
  useEffect(() => {
    // Fetch data from backend
    // URL: /api/hello
    // Vite proxy forwards to: http://localhost:8080/api/hello
    fetch('/api/hello')
      // Convert response to JSON
      .then(response => response.json())
      // Extract message and update state
      .then(data => {
        setMessage(data.message)  // Updates state → Re-renders component
      })
      // Handle errors
      .catch(error => {
        console.error('Error fetching data:', error)
        setMessage('Error: Failed to fetch')
      })
  }, [])  // [] = run only once when component mounts

  // ─────────────────────────────────────────────────────────
  // RENDER UI
  // ─────────────────────────────────────────────────────────
  // JSX: HTML-like syntax in JavaScript
  // Rendered to actual HTML by React
  return (
    <div className="App">
      <h1>React + TypeScript + Spring Boot</h1>

      <div className="card">
        <p>Backend says: <strong>{message}</strong></p>
      </div>

      <p className="info">
        Frontend: React 18 + TypeScript + Vite<br/>
        Backend: Spring Boot 3.1.4 + Java 17
      </p>
    </div>
  )
}

// Export component for use in main.tsx
export default App

// ═══════════════════════════════════════════════════════════
// COMPONENT LIFECYCLE:
// ═══════════════════════════════════════════════════════════
// 1. Component mounts (first render)
//    - message = "Loading..."
//    - Displays: "Backend says: Loading..."
//
// 2. useEffect runs (after first render)
//    - Calls fetch('/api/hello')
//    - Waits for response
//
// 3. Response received
//    - setMessage(data.message)
//    - State changes!
//
// 4. Component re-renders (because state changed)
//    - message = "Hello World"
//    - Displays: "Backend says: Hello World"
//
// 5. User sees updated UI!
// ═══════════════════════════════════════════════════════════
```

**Data Flow:**

```
1. User opens: http://localhost:3000
   ↓
2. Browser loads index.html
   ↓
3. Vite loads main.tsx
   ↓
4. main.tsx renders <App />
   ↓
5. App component renders with message="Loading..."
   User sees: "Backend says: Loading..."
   ↓
6. useEffect runs → fetch('/api/hello')
   ↓
7. Vite proxy forwards to: http://localhost:8080/api/hello
   ↓
8. Spring Boot backend responds: {"message":"Hello World"}
   ↓
9. fetch() receives response
   ↓
10. setMessage("Hello World") called
    ↓
11. React detects state change → Re-renders App
    ↓
12. User sees: "Backend says: Hello World"
```

### 3. vite.config.ts - Build Configuration

**Location:** `frontend/vite.config.ts`

**Purpose:** Configure Vite build tool and dev server

**Code Breakdown:**
```typescript
// ═══════════════════════════════════════════════════════════
// VITE CONFIGURATION
// ═══════════════════════════════════════════════════════════
// Vite: Modern build tool for frontend
// - Fast Hot Module Replacement (HMR)
// - Optimized production builds
// - Dev server with proxy support
// ═══════════════════════════════════════════════════════════

import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  // React plugin: Enables React Fast Refresh (HMR for React)
  plugins: [react()],

  // ─────────────────────────────────────────────────────────
  // DEVELOPMENT SERVER CONFIGURATION
  // ─────────────────────────────────────────────────────────
  server: {
    port: 3000,  // Frontend runs on http://localhost:3000

    // 🔄 PROXY CONFIGURATION
    // Problem: Frontend (localhost:3000) calling Backend (localhost:8080)
    //          = Cross-Origin Request (CORS issue)
    //
    // Solution: Proxy API requests through Vite dev server
    //          Frontend thinks API is on same origin!
    //
    // How it works:
    // - Frontend calls: fetch('/api/hello')
    // - Vite intercepts: /api/* requests
    // - Vite forwards to: http://localhost:8080/api/hello
    // - Backend responds
    // - Vite sends response to frontend
    // - No CORS issue! (same origin from browser's perspective)
    proxy: {
      '/api': {
        target: 'http://localhost:8080',  // Backend URL
        changeOrigin: true,                 // Changes origin header
        secure: false                       // Allow HTTP (not HTTPS)
      }
    }
  }
})

// ═══════════════════════════════════════════════════════════
// PROXY EXAMPLE:
// ═══════════════════════════════════════════════════════════
// Frontend makes request: fetch('/api/hello')
//
// WITHOUT PROXY:
// Browser tries: http://localhost:3000/api/hello
// Result: 404 Not Found (no backend on port 3000)
//
// WITH PROXY:
// 1. Browser requests: http://localhost:3000/api/hello
// 2. Vite intercepts (sees /api prefix)
// 3. Vite forwards to: http://localhost:8080/api/hello
// 4. Spring Boot responds: {"message":"Hello World"}
// 5. Vite sends to browser
// 6. Browser receives response (thinks it came from port 3000)
// ═══════════════════════════════════════════════════════════
```

### 4. package.json - npm Configuration

**Location:** `frontend/package.json`

**Purpose:** Defines npm dependencies and scripts

**Code Breakdown:**
```json
{
  "name": "frontend",
  "version": "0.0.1",
  "private": true,
  "type": "module",

  // ═══════════════════════════════════════════════════════
  // NPM SCRIPTS (Commands to run)
  // ═══════════════════════════════════════════════════════
  // Similar to: Maven goals (mvn spring-boot:run)
  "scripts": {
    "dev": "vite",              // Run dev server: npm run dev
    "build": "tsc && vite build", // Build for production: npm run build
    "preview": "vite preview"    // Preview production build: npm run preview
  },

  // ═══════════════════════════════════════════════════════
  // RUNTIME DEPENDENCIES
  // ═══════════════════════════════════════════════════════
  // These are included in production build
  // Similar to: <dependencies> in pom.xml
  "dependencies": {
    "react": "^18.2.0",          // React library
    "react-dom": "^18.2.0"       // React DOM rendering
  },

  // ═══════════════════════════════════════════════════════
  // DEVELOPMENT DEPENDENCIES
  // ═══════════════════════════════════════════════════════
  // Only used during development
  // Similar to: <dependency><scope>test</scope> in Maven
  "devDependencies": {
    "@types/react": "^18.2.43",        // TypeScript types for React
    "@types/react-dom": "^18.2.17",    // TypeScript types for ReactDOM
    "@vitejs/plugin-react": "^4.2.1",  // Vite plugin for React
    "typescript": "^5.3.3",             // TypeScript compiler
    "vite": "^5.0.8"                    // Vite build tool
  }
}

// ═══════════════════════════════════════════════════════
// VERSION SYNTAX:
// ═══════════════════════════════════════════════════════
// ^18.2.0 means:
// - Allow: 18.2.x, 18.3.x, 18.9.x
// - Block: 19.x.x (major version change)
// - Updates: Minor and patch versions allowed
//
// Semantic Versioning: MAJOR.MINOR.PATCH
// - MAJOR: Breaking changes
// - MINOR: New features (backward compatible)
// - PATCH: Bug fixes
// ═══════════════════════════════════════════════════════

// ═══════════════════════════════════════════════════════
// NPM VS MAVEN COMPARISON:
// ═══════════════════════════════════════════════════════
// npm install          =  mvn install
// npm run dev          =  mvn spring-boot:run
// npm run build        =  mvn package
// package.json         =  pom.xml
// node_modules/        =  ~/.m2/repository/
// package-lock.json    =  Maven version management
// ═══════════════════════════════════════════════════════
```

---

## 🚀 GitHub Actions CI/CD

### CI/CD Architecture

```
┌─────────────────────────────────────────────────────────┐
│                    DEVELOPER                             │
│             Writes code + commits                        │
└─────────────────────────────────────────────────────────┘
                         ↓
                    git push origin main
                         ↓
┌─────────────────────────────────────────────────────────┐
│                    GITHUB                                │
│              Code pushed to main branch                  │
└─────────────────────────────────────────────────────────┘
                         ↓
              Triggers GitHub Actions
                         ↓
┌─────────────────────────────────────────────────────────┐
│              GITHUB ACTIONS WORKFLOWS                    │
│                                                          │
│  [backend-deploy.yml]         [frontend-deploy.yml]     │
│  1. Build with Maven          1. Build with npm         │
│  2. Run tests                 2. Deploy to Vercel       │
│  3. Trigger Render deploy     3. Update production      │
└─────────────────────────────────────────────────────────┘
                ↓                          ↓
          Deploy to Render            Deploy to Vercel
                ↓                          ↓
┌─────────────────────────┐   ┌─────────────────────────┐
│    RENDER (Backend)     │   │   VERCEL (Frontend)     │
│                         │   │                         │
│  - Builds Java app      │   │  - Builds React app     │
│  - Runs on Java runtime │   │  - Serves static files  │
│  - Provides REST API    │   │  - CDN distribution     │
└─────────────────────────┘   └─────────────────────────┘
                ↓                          ↓
┌─────────────────────────────────────────────────────────┐
│                   PRODUCTION URLS                        │
│                                                          │
│  Backend:  https://react-ts-springboot-backend.onrender.com │
│  Frontend: https://react-ts-springboot.vercel.app      │
└─────────────────────────────────────────────────────────┘
```

### Backend Deployment Workflow

**Location:** `.github/workflows/backend-deploy.yml`

**Purpose:** Automatically build, test, and deploy backend to Render

**Workflow Breakdown:**

```yaml
# ═══════════════════════════════════════════════════════════
# BACKEND CI/CD PIPELINE
# ═══════════════════════════════════════════════════════════
# Purpose: Automatically deploy Spring Boot backend to Render
# Triggers: Every push to main branch, or manual dispatch
# ═══════════════════════════════════════════════════════════

name: Backend Deployment

# ─────────────────────────────────────────────────────────
# WORKFLOW TRIGGERS
# ─────────────────────────────────────────────────────────
on:
  push:
    branches: [main]          # Run on push to main
  workflow_dispatch:          # Allow manual trigger

# ─────────────────────────────────────────────────────────
# CONCURRENCY CONTROL
# ─────────────────────────────────────────────────────────
# Only one deployment at a time
# Cancel in-progress deploys if new one starts
concurrency:
  group: backend-production
  cancel-in-progress: true

# ─────────────────────────────────────────────────────────
# JOBS
# ─────────────────────────────────────────────────────────
jobs:
  # Job 1: Build and Test
  ci:
    name: Backend Deployment - Build & Deploy
    runs-on: ubuntu-latest    # GitHub-hosted runner

    steps:
      # Step 1: Get code from repository
      - name: Checkout code
        uses: actions/checkout@v4

      # Step 2: Setup Java 17
      - name: Setup Java
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
          cache: 'maven'      # Cache Maven dependencies

      # Step 3: Build with Maven
      - name: Build with Maven
        run: cd backend && mvn clean package -DskipTests

      # Step 4: Run tests
      - name: Run tests
        run: cd backend && mvn test

      # Step 5: Upload JAR artifact
      - name: Upload JAR artifact
        uses: actions/upload-artifact@v4
        with:
          name: backend-jar
          path: backend/target/*.jar

  # Job 2: Deploy to Render
  deploy-production:
    name: Deploy Backend to Production
    needs: ci                 # Wait for CI job to complete
    runs-on: ubuntu-latest

    steps:
      # Trigger Render deployment via API
      - name: Deploy to Render
        run: |
          curl -X POST "https://api.render.com/v1/services/${{ secrets.RENDER_SERVICE_ID }}/deploys" \
            -H "Authorization: Bearer ${{ secrets.RENDER_API_KEY }}" \
            -d '{"clearCache": "clear"}'
```

**What Happens:**

```
1. Developer pushes to main
   ↓
2. GitHub Actions triggered
   ↓
3. CI Job runs:
   - Checkout code
   - Setup Java 17
   - mvn clean package (build JAR)
   - mvn test (run tests)
   - Upload JAR artifact
   ↓
4. Deploy Job runs:
   - Call Render API
   - Trigger deployment
   ↓
5. Render receives trigger:
   - Pull latest code from GitHub
   - Run: mvn clean package
   - Build Docker container
   - Deploy to production
   - Start Java application
   ↓
6. Backend live at: https://react-ts-springboot-backend.onrender.com
```

### Frontend Deployment Workflow

**Location:** `.github/workflows/frontend-deploy.yml`

**Purpose:** Automatically build and deploy frontend to Vercel

**Workflow Breakdown:**

```yaml
# ═══════════════════════════════════════════════════════════
# FRONTEND CI/CD PIPELINE
# ═══════════════════════════════════════════════════════════
# Purpose: Automatically deploy React frontend to Vercel
# Triggers: Every push to main branch, or manual dispatch
# ═══════════════════════════════════════════════════════════

name: Frontend Deployment

on:
  push:
    branches: [main]
  workflow_dispatch:

jobs:
  deploy-production:
    name: Deploy Frontend to Production
    runs-on: ubuntu-latest

    steps:
      # Step 1: Get code
      - name: Checkout code
        uses: actions/checkout@v4

      # Step 2: Setup Node.js
      - name: Setup Node.js
        uses: actions/setup-node@v4
        with:
          node-version: '18'
          cache: 'npm'
          cache-dependency-path: frontend/package-lock.json

      # Step 3: Install Vercel CLI
      - name: Install Vercel CLI
        run: npm install --global vercel@latest

      # Step 4: Deploy to Vercel
      - name: Deploy to Vercel Production
        run: |
          cd frontend
          vercel deploy --prod --token=${{ secrets.VERCEL_TOKEN }}
        env:
          VERCEL_ORG_ID: ${{ secrets.VERCEL_ORG_ID }}
          VERCEL_PROJECT_ID: ${{ secrets.VERCEL_PROJECT_ID }}
          VITE_API_URL: ${{ secrets.PROD_API_URL }}
```

**What Happens:**

```
1. Developer pushes to main
   ↓
2. GitHub Actions triggered
   ↓
3. Deploy Job runs:
   - Checkout code
   - Setup Node.js 18
   - Install Vercel CLI
   - Run: vercel deploy --prod
   ↓
4. Vercel receives deployment:
   - Install dependencies (npm install)
   - Build app (npm run build)
   - Deploy to CDN
   - Update production URL
   ↓
5. Frontend live at: https://react-ts-springboot.vercel.app
```

---

## 🔗 Full-Stack Integration

### How Frontend and Backend Communicate

```
DEVELOPMENT (Local):
────────────────────

Frontend: http://localhost:3000
Backend:  http://localhost:8080

Flow:
1. User visits http://localhost:3000
2. App.tsx calls: fetch('/api/hello')
3. Vite proxy intercepts /api/* requests
4. Vite forwards to: http://localhost:8080/api/hello
5. Spring Boot responds: {"message":"Hello World"}
6. Frontend displays: "Backend says: Hello World"


PRODUCTION (Deployed):
──────────────────────

Frontend: https://react-ts-springboot.vercel.app
Backend:  https://react-ts-springboot-backend.onrender.com

Flow:
1. User visits https://react-ts-springboot.vercel.app
2. App.tsx calls: fetch('https://react-ts-springboot-backend.onrender.com/api/hello')
3. Request goes directly to backend (no proxy)
4. Spring Boot checks CORS (CorsConfig allows all origins)
5. Spring Boot responds: {"message":"Hello World"}
6. Frontend displays: "Backend says: Hello World"
```

### Environment Configuration

**Development:**
```typescript
// frontend/.env (not committed)
VITE_API_URL=              // Empty = use Vite proxy

// vite.config.ts
proxy: {
  '/api': 'http://localhost:8080'  // Proxy to local backend
}
```

**Production:**
```typescript
// Set in Vercel dashboard
VITE_API_URL=https://react-ts-springboot-backend.onrender.com

// App.tsx uses this value
fetch(`${import.meta.env.VITE_API_URL}/api/hello`)
```

---

## 🧑‍💻 Development Workflow

### Local Development Setup

```bash
# ═══════════════════════════════════════════════════════
# STEP 1: Clone Repository
# ═══════════════════════════════════════════════════════
git clone git@github.com:SayefReyadh/react-ts-springboot-starter.git
cd react-ts-springboot-starter

# ═══════════════════════════════════════════════════════
# STEP 2: Start Backend (Terminal 1)
# ═══════════════════════════════════════════════════════
cd backend

# Install dependencies (first time only)
mvn install

# Run Spring Boot application
mvn spring-boot:run

# Expected output:
# Started DemoApplication in 2.3 seconds
# Server running at: http://localhost:8080

# Test endpoint:
curl http://localhost:8080/api/hello
# Response: {"message":"Hello World"}

# ═══════════════════════════════════════════════════════
# STEP 3: Start Frontend (Terminal 2)
# ═══════════════════════════════════════════════════════
cd frontend

# Install dependencies (first time only)
npm install

# Run Vite dev server
npm run dev

# Expected output:
# Local: http://localhost:3000

# ═══════════════════════════════════════════════════════
# STEP 4: Test Full-Stack Integration
# ═══════════════════════════════════════════════════════
# Open browser: http://localhost:3000
# You should see: "Backend says: Hello World"

# If you see this, integration is working! ✅
```

### Making Changes

**Backend Changes:**
```bash
# 1. Modify Java file (e.g., HelloController.java)
# 2. Stop server (Ctrl+C)
# 3. Restart: mvn spring-boot:run

# Tip: Use Spring Boot DevTools for auto-restart
```

**Frontend Changes:**
```bash
# 1. Modify TypeScript file (e.g., App.tsx)
# 2. Vite automatically rebuilds (Hot Module Replacement)
# 3. Browser refreshes automatically
# 4. See changes immediately!
```

### Testing

**Backend Testing:**
```bash
cd backend

# Run all tests
mvn test

# Run specific test
mvn test -Dtest=HelloControllerTest

# Test with Postman:
# GET http://localhost:8080/api/hello
```

**Frontend Testing:**
```bash
cd frontend

# Manual testing:
# Open http://localhost:3000 in browser
# Check browser console for errors (F12)

# Network tab:
# Verify fetch() calls to /api/hello
# Check response data
```

---

## 🚀 Deployment Architecture

### Production Infrastructure

```
┌─────────────────────────────────────────────────────────┐
│                        USERS                             │
│              Access via web browser                      │
└─────────────────────────────────────────────────────────┘
                         ↓
┌─────────────────────────────────────────────────────────┐
│                   VERCEL CDN (Frontend)                  │
│         https://react-ts-springboot.vercel.app          │
│                                                          │
│  - Serves static files (HTML, CSS, JS)                  │
│  - Global CDN (fast worldwide)                          │
│  - Automatic HTTPS                                      │
│  - Instant deployments                                  │
└─────────────────────────────────────────────────────────┘
                         ↓
                   API calls to backend
                         ↓
┌─────────────────────────────────────────────────────────┐
│                  RENDER (Backend)                        │
│   https://react-ts-springboot-backend.onrender.com     │
│                                                          │
│  - Runs Java 17 runtime                                 │
│  - Spring Boot application                              │
│  - REST API endpoints                                   │
│  - Auto-scaling                                         │
│  - HTTPS enabled                                        │
└─────────────────────────────────────────────────────────┘
```

### Deployment Process

**Backend (Render):**
```
1. Git push to main
   ↓
2. GitHub Actions triggers
   ↓
3. Render API called
   ↓
4. Render pulls latest code
   ↓
5. Render runs: mvn clean package
   ↓
6. Render builds Docker container
   ↓
7. Render deploys container
   ↓
8. Health check: GET /api/hello
   ↓
9. If healthy: Switch to new version
   ↓
10. Old version terminated
```

**Frontend (Vercel):**
```
1. Git push to main
   ↓
2. GitHub Actions triggers
   ↓
3. Vercel CLI deploys
   ↓
4. Vercel runs: npm install
   ↓
5. Vercel runs: npm run build
   ↓
6. Vercel uploads build to CDN
   ↓
7. Vercel updates DNS
   ↓
8. New version live
```

---

## 🎯 Learning Objectives Achieved

### Spring Boot Concepts Demonstrated

✅ **Auto-Configuration**
- Tomcat server configured automatically
- Jackson JSON converter configured automatically
- Spring MVC configured automatically

✅ **Component Scanning**
- `@RestController` discovered and registered
- `@Configuration` discovered and registered

✅ **Inversion of Control (IoC)**
- Spring creates and manages beans
- Application doesn't create objects manually

✅ **Layered Architecture**
- Controller layer (HelloController)
- Config layer (CorsConfig)
- Ready for Service and Repository layers

✅ **Embedded Server**
- No separate Tomcat installation
- Run as: `java -jar app.jar`

✅ **Externalized Configuration**
- `application.properties` for settings
- Easy to change without code changes

### Full-Stack Skills Demonstrated

✅ **REST API Development**
- Creating endpoints with Spring Boot
- Consuming APIs with React

✅ **Build Tools**
- Maven for Java (backend)
- npm for JavaScript (frontend)

✅ **Modern Frontend**
- React functional components
- TypeScript type safety
- Vite fast builds

✅ **DevOps**
- CI/CD pipelines
- Automated testing
- Automated deployment

✅ **Cloud Deployment**
- Backend on Render
- Frontend on Vercel
- Production-ready setup

---

## 📝 Assignment Extensions

### Suggested Improvements for Students

**1. Add Student Management API**
```java
// Model: Student.java
public class Student {
    private String id;
    private String name;
    private String email;
    private double cgpa;
}

// Controller: StudentController.java
@RestController
@RequestMapping("/api/students")
public class StudentController {
    @GetMapping
    public List<Student> getAllStudents() { ... }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable String id) { ... }

    @PostMapping
    public Student addStudent(@RequestBody Student student) { ... }
}
```

**2. Add Course Management API**
```java
// Model: Course.java
public class Course {
    private String code;
    private String name;
    private int credits;
    private String instructor;
}

// Controller: CourseController.java
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @GetMapping
    public List<Course> getAllCourses() { ... }
}
```

**3. Update Frontend to Display Data**
```typescript
// Fetch and display students
const [students, setStudents] = useState<Student[]>([])

useEffect(() => {
    fetch('/api/students')
        .then(res => res.json())
        .then(data => setStudents(data))
}, [])

return (
    <ul>
        {students.map(student => (
            <li key={student.id}>{student.name} - {student.cgpa}</li>
        ))}
    </ul>
)
```

---

## 🎓 Key Takeaways

### What Students Should Understand

1. **Spring Boot Architecture**
   - How @SpringBootApplication works
   - Component scanning and bean creation
   - Auto-configuration magic

2. **REST API Development**
   - Creating endpoints with @GetMapping
   - Returning JSON responses
   - CORS configuration

3. **Full-Stack Integration**
   - Frontend-backend communication
   - API consumption in React
   - Proxy configuration for development

4. **Build Tools**
   - Maven for Java projects
   - npm for JavaScript projects
   - Dependency management

5. **DevOps Practices**
   - CI/CD pipelines
   - Automated deployment
   - Cloud hosting

### Real-World Applications

This template demonstrates patterns used in:
- E-commerce platforms
- Social media applications
- Content management systems
- SaaS applications
- Enterprise web applications

---

## 📚 Additional Resources

**Spring Boot:**
- [Official Documentation](https://spring.io/projects/spring-boot)
- [Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Baeldung Spring Boot Tutorials](https://www.baeldung.com/spring-boot)

**React:**
- [Official Documentation](https://react.dev)
- [React TypeScript Cheatsheet](https://react-typescript-cheatsheet.netlify.app/)

**Tools:**
- [Postman](https://www.postman.com/) - API testing
- [Spring Initializr](https://start.spring.io/) - Generate Spring Boot projects

---

## 🙋 Questions for Discussion

1. What happens if you remove @RestController from HelloController?
2. How does Spring Boot know to start Tomcat on port 8080?
3. Why do we need CORS configuration?
4. What is the difference between Maven and npm?
5. How does the Vite proxy help during development?
6. What is the role of Component Scanning in Spring Boot?
7. Why use TypeScript instead of JavaScript?
8. How do GitHub Actions know when to run?

---

**Happy Learning! 🚀**

**Next Week:** Spring Data JPA and Database Integration
