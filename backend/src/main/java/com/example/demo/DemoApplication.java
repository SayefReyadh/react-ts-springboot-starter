package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Student Management System.
 * This class serves as the entry point for the Spring Boot application.
 * 
 * @SpringBootApplication enables:
 * - @Configuration: Marks this class as a source of bean definitions
 * - @EnableAutoConfiguration: Enables Spring Boot's auto-configuration mechanism
 * - @ComponentScan: Scans for components, configurations, and services in the package
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Main method that launches the Spring Boot application.
     * @param args Command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
