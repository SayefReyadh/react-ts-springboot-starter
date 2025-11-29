# Spring Boot Request Flow Guide

## Overview

This document explains how HTTP requests flow through the Spring Boot application, following the **layered architecture** pattern.

---

## Complete Request Flow Diagram

```
1. Client (Browser/Frontend - React Application)
   ↓ HTTP Request (e.g., GET /api/students/1)
   
2. Spring Boot Application Server
   ↓
   
3. DispatcherServlet (Spring's Front Controller)
   - Entry point for all HTTP requests
   - Routes requests to appropriate controllers
   ↓
   
4. CONTROLLER Layer (@RestController)
   📁 StudentController.java
   - Receives and validates HTTP requests
   - Maps URL paths to handler methods
   - Returns HTTP responses
   ↓
   
5. SERVICE Layer (@Service)
   📁 StudentService.java
   - Contains business logic
   - Manages transactions
   - Coordinates between controller and data layer
   ↓
   
6. REPOSITORY Layer (@Repository)
   📁 StudentRepository.java
   - Provides database abstraction
   - Extends JpaRepository for CRUD operations
   ↓
   
7. JPA/Hibernate (ORM Framework)
   - Translates Java method calls to SQL queries
   - Manages database connections and sessions
   ↓
   
8. DATABASE (H2/MySQL/PostgreSQL)
   - Executes SQL queries
   - Returns raw data
   ↓
   
9. Response Flow (Bottom to Top)
   Database → JPA → Repository → Service → Controller
   ↓
   
10. HTTP Response (JSON)
    Returns to Client
```

---

## Best Practices

### 1. **Keep Controllers Thin**
```java
// ❌ Bad: Business logic in controller
@PostMapping
public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    if (student.getName() == null || student.getName().isEmpty()) {
        throw new IllegalArgumentException("Name is required");
    }
    // More validation...
    Student saved = studentRepository.save(student);
    return new ResponseEntity<>(saved, HttpStatus.CREATED);
}

// ✅ Good: Delegate to service
@PostMapping
public ResponseEntity<Student> createStudent(@RequestBody Student student) {
    Student created = studentService.createStudent(student);
    return new ResponseEntity<>(created, HttpStatus.CREATED);
}
```

### 2. **Use DTOs for API Responses**
Separate internal entities from API contracts:
```java
// StudentDTO.java - for API responses
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    // Only fields client needs
}

// Student.java - internal entity
@Entity
public class Student {
    // May contain sensitive fields not exposed to API
}
```

### 3. **Handle Exceptions Properly**
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(StudentNotFoundException ex) {
        return new ResponseEntity<>(
            new ErrorResponse(ex.getMessage()), 
            HttpStatus.NOT_FOUND
        );
    }
}
```

### 4. **Use Proper HTTP Status Codes**
```java
// 200 OK - Successful GET, PUT
// 201 CREATED - Successful POST
// 204 NO_CONTENT - Successful DELETE
// 400 BAD_REQUEST - Invalid input
// 404 NOT_FOUND - Resource doesn't exist
// 500 INTERNAL_SERVER_ERROR - Server error
```

---

## Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA Guide](https://spring.io/guides/gs/accessing-data-jpa/)
- [REST API Best Practices](https://restfulapi.net/)
- Project Repository: [REPOSITORY_VIEW.md](./REPOSITORY_VIEW.md)
- Local Setup Guide: [LOCAL_SETUP.md](./LOCAL_SETUP.md)

---

**Last Updated:** November 29, 2025
