package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 * SPRING BOOT APPLICATION - MAIN ENTRY POINT
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * Course: CSE 2118 - Advanced Object-Oriented Programming Laboratory
 * Topic: Spring Boot Fundamentals
 *
 * This class is the entry point of our Spring Boot application.
 * When you run this application (mvn spring-boot:run or java -jar app.jar),
 * the main() method is executed, which starts the entire Spring Boot application.
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * KEY SPRING BOOT CONCEPTS DEMONSTRATED:
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * 1. @SpringBootApplication Annotation
 * 2. Auto-Configuration
 * 3. Component Scanning
 * 4. Embedded Server (Tomcat)
 * 5. Spring IoC Container
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 */

/**
 * ─────────────────────────────────────────────────────────────────────────────
 * @SpringBootApplication - THE MAGIC ANNOTATION
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * This single annotation is actually a combination of THREE annotations:
 *
 * 1. @Configuration
 *    - Marks this class as a source of bean definitions
 *    - Allows us to define @Bean methods if needed
 *    - Part of Spring's Java-based configuration
 *
 * 2. @EnableAutoConfiguration
 *    - Enables Spring Boot's auto-configuration mechanism
 *    - Automatically configures your application based on dependencies
 *    - Examples of auto-configuration:
 *      • Detects spring-boot-starter-web → Configures embedded Tomcat server
 *      • Detects spring-boot-starter-web → Configures Spring MVC
 *      • Detects Jackson library → Configures JSON serialization
 *      • Detects JPA → Configures database connections (when added)
 *
 * 3. @ComponentScan
 *    - Tells Spring to scan this package (com.example.demo) and all sub-packages
 *    - Looks for components annotated with:
 *      • @Component - Generic Spring component
 *      • @Service - Business logic layer
 *      • @Repository - Data access layer
 *      • @Controller / @RestController - Web layer
 *      • @Configuration - Configuration classes
 *    - Creates beans for all found components
 *    - Manages their lifecycle in the IoC container
 *
 * ─────────────────────────────────────────────────────────────────────────────
 * WHY USE @SpringBootApplication?
 * ─────────────────────────────────────────────────────────────────────────────
 *
 * Before Spring Boot, you had to configure all three separately:
 *
 * @Configuration
 * @EnableAutoConfiguration
 * @ComponentScan
 * public class DemoApplication { ... }
 *
 * Spring Boot simplifies this to just one annotation!
 * This is "Convention over Configuration" principle in action.
 * ─────────────────────────────────────────────────────────────────────────────
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * ─────────────────────────────────────────────────────────────────────────
     * MAIN METHOD - APPLICATION ENTRY POINT
     * ─────────────────────────────────────────────────────────────────────────
     *
     * This is a standard Java application entry point.
     * When you run the application, the JVM calls this method first.
     *
     * Commands that execute this method:
     * 1. mvn spring-boot:run          (during development)
     * 2. java -jar target/demo.jar    (production deployment)
     *
     * ─────────────────────────────────────────────────────────────────────────
     * WHAT HAPPENS WHEN main() RUNS:
     * ─────────────────────────────────────────────────────────────────────────
     *
     * Step 1: JVM starts and calls main()
     *
     * Step 2: SpringApplication.run() is called
     *         This is Spring Boot's bootstrapping method
     *
     * Step 3: Spring Boot Auto-Configuration Process Begins
     *         a) Scans classpath for dependencies
     *         b) Based on what it finds, auto-configures components:
     *            - Found spring-boot-starter-web → Configure Tomcat + Spring MVC
     *            - Found Jackson → Configure JSON converter
     *            - Found Logback → Configure logging
     *         c) This is why Spring Boot is "opinionated" - it makes decisions for you!
     *
     * Step 4: Component Scanning Begins
     *         a) Scans package: com.example.demo and all sub-packages
     *         b) Finds classes with stereotype annotations:
     *            - HelloController (annotated with @RestController)
     *            - CorsConfig (annotated with @Configuration)
     *         c) Creates bean instances for each component
     *
     * Step 5: Spring IoC Container (Application Context) is Created
     *         a) Container is created to manage all beans
     *         b) All discovered beans are instantiated
     *         c) Dependencies are injected (if any @Autowired)
     *         d) Post-construction methods run (if any @PostConstruct)
     *         e) Beans are ready to use!
     *
     * Step 6: Embedded Tomcat Server Starts
     *         a) Tomcat server starts on port 8080 (default)
     *         b) Server listens for HTTP requests
     *         c) Spring MVC DispatcherServlet is registered
     *         d) Ready to handle web requests!
     *
     * Step 7: Application Ready
     *         Console output: "Started DemoApplication in 2.5 seconds"
     *         Application is now running and can handle HTTP requests
     *
     * ─────────────────────────────────────────────────────────────────────────
     * SPRING BOOT CONCEPTS IN ACTION:
     * ─────────────────────────────────────────────────────────────────────────
     *
     * 🚀 CONVENTION OVER CONFIGURATION
     *    - No XML configuration files needed
     *    - No manual Tomcat setup needed
     *    - No manual bean definitions needed (unless custom)
     *    - Spring Boot makes intelligent defaults
     *
     * 🏭 INVERSION OF CONTROL (IoC)
     *    - Spring creates and manages objects (beans)
     *    - You don't use 'new' keyword for Spring components
     *    - Container controls object lifecycle
     *
     * 💉 DEPENDENCY INJECTION
     *    - Spring injects dependencies automatically
     *    - Use @Autowired to inject dependencies
     *    - Promotes loose coupling
     *
     * 🔍 COMPONENT SCANNING
     *    - Automatic discovery of components
     *    - No need to manually register each class
     *    - Just use proper annotations
     *
     * 🎯 AUTO-CONFIGURATION
     *    - Intelligent configuration based on classpath
     *    - Reduces boilerplate code significantly
     *    - Can be overridden when needed
     *
     * ─────────────────────────────────────────────────────────────────────────
     *
     * @param args Command line arguments (not used in this simple application)
     *             In more complex apps, you can pass arguments like:
     *             java -jar app.jar --server.port=9090
     */
    public static void main(String[] args) {
        // SpringApplication.run() bootstraps the entire Spring Boot application
        // Parameters:
        // 1. DemoApplication.class - The primary configuration class
        // 2. args - Command line arguments to pass to the application
        SpringApplication.run(DemoApplication.class, args);

        // After this line executes, the application is fully started and running!
        // You can access it at: http://localhost:8080

        /**
         * ─────────────────────────────────────────────────────────────────────
         * LEARNING EXERCISE:
         * ─────────────────────────────────────────────────────────────────────
         *
         * Try these experiments to understand Spring Boot better:
         *
         * 1. Run the application and observe console output
         *    Look for: "Started DemoApplication in X seconds"
         *
         * 2. Add this line before SpringApplication.run():
         *    System.out.println("Starting Spring Boot Application...");
         *    Notice when it prints (before application starts)
         *
         * 3. Change server port in application.properties:
         *    server.port=9090
         *    Run again and access: http://localhost:9090/api/hello
         *
         * 4. Enable debug mode to see auto-configuration report:
         *    Add to application.properties: debug=true
         *    Run and see what Spring Boot auto-configured
         *
         * 5. Try removing @SpringBootApplication and see what happens:
         *    Application won't start - this annotation is essential!
         *
         * ─────────────────────────────────────────────────────────────────────
         */
    }
}

/**
 * ═══════════════════════════════════════════════════════════════════════════════
 * FREQUENTLY ASKED QUESTIONS (FAQs)
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * Q1: Why don't we need to install Tomcat separately?
 * A1: Spring Boot includes an embedded Tomcat server. When you run the application,
 *     Tomcat starts automatically. This is called "Embedded Server" pattern.
 *     Your application becomes a standalone executable JAR.
 *
 * Q2: Where are the beans created?
 * A2: Beans are created in the Spring IoC Container (Application Context).
 *     This happens automatically during SpringApplication.run().
 *     You can access the container if needed:
 *     ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
 *
 * Q3: How does Spring know what to auto-configure?
 * A3: Spring Boot checks your classpath for dependencies.
 *     Example: If spring-boot-starter-web is present, it knows you're building a web app,
 *     so it configures Tomcat, Spring MVC, and Jackson automatically.
 *
 * Q4: Can I customize auto-configuration?
 * A4: Yes! You can override defaults in application.properties or create custom
 *     @Configuration classes. Spring Boot follows "Opinionated defaults with the
 *     ability to override" principle.
 *
 * Q5: What happens if I remove @SpringBootApplication?
 * A5: The application won't work. You'll need to add @Configuration,
 *     @EnableAutoConfiguration, and @ComponentScan manually.
 *
 * Q6: Can I have multiple @SpringBootApplication classes?
 * A6: No! There should be only ONE @SpringBootApplication class per application.
 *     It marks the primary configuration source and entry point.
 *
 * Q7: How do I run this application?
 * A7: Development: mvn spring-boot:run
 *     Production: mvn clean package, then java -jar target/demo-0.0.1-SNAPSHOT.jar
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 * NEXT STEPS FOR LEARNING:
 * ═══════════════════════════════════════════════════════════════════════════════
 *
 * 1. Explore HelloController.java to see how REST endpoints work
 * 2. Explore CorsConfig.java to understand configuration classes
 * 3. Read application.properties to see external configuration
 * 4. Try adding your own @RestController
 * 5. Learn about @Service and @Repository layers (coming in Week 3)
 *
 * ═══════════════════════════════════════════════════════════════════════════════
 */
