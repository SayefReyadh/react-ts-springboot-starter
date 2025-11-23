package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 * HELLO CONTROLLER - Your First REST API Endpoint!
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * WHAT THIS FILE DOES:
 * This class creates a simple API endpoint that returns "Hello World" as JSON.
 * When you visit http://localhost:8080/api/hello, you get {"message":"Hello World"}
 *
 * REAL-WORLD ANALOGY:
 * Think of this like a waiter in a restaurant:
 * - Customer makes request: "I want menu item #5"
 * - Waiter (this controller) takes the request
 * - Waiter gets the item from kitchen (in future, this will be Service layer)
 * - Waiter brings it back to customer
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 */

// ┌─────────────────────────────────────────────────────────────────────────┐
// │ @RestController - This Makes It A REST API                              │
// └─────────────────────────────────────────────────────────────────────────┘
//
// WHAT IT DOES:
// This annotation tells Spring: "Hey! This class handles web requests and returns JSON"
//
// SPRING BOOT MAGIC THAT HAPPENS:
// 1. Spring finds this class when application starts (Component Scanning)
// 2. Spring creates an instance: new HelloController()
// 3. Spring stores it in a "container" (called IoC Container)
// 4. Spring sets up routes so requests go to the right methods
// 5. Spring automatically converts return values to JSON
//
// WITHOUT SPRING BOOT:
// You would need to:
// - Manually create the object
// - Set up Tomcat server yourself
// - Write code to convert objects to JSON
// - Configure URL routing manually
//
// WITH SPRING BOOT:
// Just add @RestController and Spring does everything for you!
//
@RestController
public class HelloController {

    // ┌─────────────────────────────────────────────────────────────────────┐
    // │ METHOD: hello() - The Actual API Endpoint                           │
    // └─────────────────────────────────────────────────────────────────────┘
    //
    // @GetMapping("/api/hello") - This Creates The URL
    //
    // WHAT IT MEANS:
    // "When someone visits http://localhost:8080/api/hello, run this method"
    //
    // STEP-BY-STEP FLOW:
    //
    // 1. User Types in Browser: http://localhost:8080/api/hello
    //    ↓
    // 2. Request Goes to Tomcat Server (running on port 8080)
    //    ↓
    // 3. Spring Checks: "Which method handles /api/hello?"
    //    ↓
    // 4. Spring Finds: This method! (because of @GetMapping)
    //    ↓
    // 5. Spring Calls: hello() method
    //    ↓
    // 6. Method Returns: Map.of("message", "Hello World")
    //    ↓
    // 7. Spring Converts: Map to JSON → {"message":"Hello World"}
    //    ↓
    // 8. Browser Shows: {"message":"Hello World"}
    //
    @GetMapping("/api/hello")
    public Map<String, String> hello() {

        // WHAT THIS LINE DOES:
        // Creates a Map (like a dictionary) with one entry:
        // Key: "message"
        // Value: "Hello World"
        //
        // Map.of() is a Java 9+ shortcut for creating Maps
        //
        // WHAT HAPPENS NEXT:
        // Spring Boot sees you're returning a Map
        // Spring Boot has a library called "Jackson" built-in
        // Jackson converts the Map to JSON automatically
        // No extra code needed!
        //
        // Java Map:                    JSON Response:
        // {                           {
        //   "message": "Hello World"    "message": "Hello World"
        // }                           }
        //
        return Map.of("message", "Hello World");
    }


    // ═══════════════════════════════════════════════════════════════════════
    // PRACTICE EXERCISES - Add These Methods Below!
    // ═══════════════════════════════════════════════════════════════════════


    // ┌─────────────────────────────────────────────────────────────────────┐
    // │ EXERCISE 1: Return Your Name                                        │
    // └─────────────────────────────────────────────────────────────────────┘
    //
    // TRY THIS:
    // Uncomment the code below and run the application
    // Then visit: http://localhost:8080/api/myname
    //
    /*
    @GetMapping("/api/myname")
    public Map<String, String> getMyName() {
        return Map.of("name", "Your Name Here");  // Change to your name!
    }
    */


    // ┌─────────────────────────────────────────────────────────────────────┐
    // │ EXERCISE 2: Greet Someone By Name                                   │
    // └─────────────────────────────────────────────────────────────────────┘
    //
    // TRY THIS:
    // This endpoint takes a name from the URL
    // Example: http://localhost:8080/api/greet/John
    // Response: {"greeting":"Hello John!"}
    //
    // WHAT IS @PathVariable?
    // It takes a part of the URL and uses it as a variable
    // URL: /api/greet/John → name = "John"
    // URL: /api/greet/Alice → name = "Alice"
    //
    /*
    @GetMapping("/api/greet/{name}")
    public Map<String, String> greet(@PathVariable String name) {
        return Map.of("greeting", "Hello " + name + "!");
    }
    */


    // ┌─────────────────────────────────────────────────────────────────────┐
    // │ EXERCISE 3: Return Multiple Values                                  │
    // └─────────────────────────────────────────────────────────────────────┘
    //
    // TRY THIS:
    // Return multiple pieces of information
    // Visit: http://localhost:8080/api/info
    //
    /*
    @GetMapping("/api/info")
    public Map<String, String> getInfo() {
        return Map.of(
            "university", "UIU",
            "course", "CSE 2118",
            "topic", "Spring Boot"
        );
    }
    */
    // Response will be:
    // {
    //   "university": "UIU",
    //   "course": "CSE 2118",
    //   "topic": "Spring Boot"
    // }


    // ┌─────────────────────────────────────────────────────────────────────┐
    // │ EXERCISE 4: Return a List                                           │
    // └─────────────────────────────────────────────────────────────────────┘
    //
    // TRY THIS:
    // Return a list of items instead of a single value
    // Visit: http://localhost:8080/api/courses
    //
    /*
    @GetMapping("/api/courses")
    public List<String> getCourses() {
        return List.of("AOOP", "DSA", "Database", "Web Tech");
    }
    */
    // Response: ["AOOP","DSA","Database","Web Tech"]

}

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 * COMMON QUESTIONS & ANSWERS
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * ❓ Q1: Why do I need @RestController? What happens without it?
 * ✅ A1: Without it, Spring won't know this is a web controller.
 *       Your methods won't be accessible via URLs.
 *       The application will start, but /api/hello won't work.
 *
 * ❓ Q2: What is the difference between @GetMapping and @PostMapping?
 * ✅ A2: @GetMapping - For READING data (like viewing a webpage)
 *       @PostMapping - For CREATING data (like submitting a form)
 *       @PutMapping - For UPDATING data
 *       @DeleteMapping - For DELETING data
 *
 * ❓ Q3: How does Map become JSON automatically?
 * ✅ A3: Spring Boot includes a library called "Jackson"
 *       Jackson is a JSON processor that converts:
 *       - Java objects → JSON (when sending response)
 *       - JSON → Java objects (when receiving request)
 *       It happens automatically. No code needed!
 *
 * ❓ Q4: Can I return a String instead of Map?
 * ✅ A4: Yes! Try this:
 *       @GetMapping("/api/test")
 *       public String test() {
 *           return "Just a string!";
 *       }
 *       Response: "Just a string!"
 *
 * ❓ Q5: What if I want to return different HTTP status codes?
 * ✅ A5: Use ResponseEntity:
 *       @GetMapping("/api/hello")
 *       public ResponseEntity<Map<String, String>> hello() {
 *           return ResponseEntity
 *               .status(HttpStatus.OK)  // 200
 *               .body(Map.of("message", "Hello"));
 *       }
 *
 * ❓ Q6: How do I test these endpoints?
 * ✅ A6: Three ways:
 *       1. Browser: Just type URL: http://localhost:8080/api/hello
 *       2. Postman: Create GET request with the URL
 *       3. Frontend: fetch('/api/hello').then(res => res.json())
 *
 * ❓ Q7: What does "localhost:8080" mean?
 * ✅ A7: localhost = Your own computer
 *       8080 = Port number (like an apartment number)
 *       Tomcat server runs on port 8080 by default
 *
 * ❓ Q8: Can I change the port from 8080 to something else?
 * ✅ A8: Yes! In application.properties, add:
 *       server.port=9090
 *       Then use: http://localhost:9090/api/hello
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * HOW TO TEST YOUR CODE
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * METHOD 1: Using Browser (Easiest!)
 * ──────────────────────────────────
 * 1. Start your application: mvn spring-boot:run
 * 2. Open browser
 * 3. Type: http://localhost:8080/api/hello
 * 4. You should see: {"message":"Hello World"}
 *
 * METHOD 2: Using Command Line (curl)
 * ────────────────────────────────────
 * Open terminal and run:
 * curl http://localhost:8080/api/hello
 *
 * METHOD 3: Using Postman (Professional Tool)
 * ────────────────────────────────────────────
 * 1. Open Postman
 * 2. Create new request
 * 3. Method: GET
 * 4. URL: http://localhost:8080/api/hello
 * 5. Click Send
 * 6. See response in body
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * WHAT TO DO IF IT DOESN'T WORK
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * ❌ ERROR: "Cannot connect" or "Connection refused"
 * ✅ FIX: Backend is not running. Run: mvn spring-boot:run
 *
 * ❌ ERROR: 404 Not Found
 * ✅ FIX: Check URL spelling. Make sure it's /api/hello not /api/Hello
 *
 * ❌ ERROR: Port 8080 already in use
 * ✅ FIX: Another app is using port 8080. Either:
 *        - Stop the other app, OR
 *        - Change port in application.properties: server.port=9090
 *
 * ❌ ERROR: Application won't start
 * ✅ FIX: Check console for error messages. Common issues:
 *        - Missing @SpringBootApplication in DemoApplication.java
 *        - Syntax errors in code
 *        - Maven dependencies not downloaded (run: mvn install)
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * KEY CONCEPTS TO REMEMBER
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * 1. @RestController = "This class handles web requests"
 * 2. @GetMapping("/path") = "When user visits /path, run this method"
 * 3. Return value is automatically converted to JSON
 * 4. You don't create controller objects yourself - Spring does it
 * 5. Each URL maps to one method
 * 6. Use different HTTP methods for different operations (GET, POST, PUT, DELETE)
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * NEXT STEPS
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * 1. ✅ Uncomment Exercise 1 and test it
 * 2. ✅ Uncomment Exercise 2 and try different names in URL
 * 3. ✅ Uncomment Exercise 3 and see multiple values returned
 * 4. ✅ Uncomment Exercise 4 and see a list in JSON
 * 5. ✅ Try creating your own endpoint
 * 6. ✅ Test everything with Postman
 * 7. ✅ Learn about @Service layer (Week 3)
 * 8. ✅ Learn about databases with JPA (Week 3)
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 */
