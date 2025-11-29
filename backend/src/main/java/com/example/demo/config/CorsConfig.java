package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS (Cross-Origin Resource Sharing) configuration for the application.
 * This configuration allows the backend API to be accessed from different origins (e.g., frontend).
 * 
 * @Configuration indicates that this class contains Spring configuration
 * WebMvcConfigurer allows customization of Spring MVC configuration
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configures CORS mappings for the application.
     * 
     * @param registry CorsRegistry to add mappings to
     * 
     * Configuration details:
     * - addMapping("/**"): Applies CORS settings to all endpoints
     * - allowedOrigins("*"): Allows requests from any origin (all domains)
     * - allowedMethods("*"): Allows all HTTP methods (GET, POST, PUT, DELETE, etc.)
     * - allowedHeaders("*"): Allows all request headers
     * 
     * Note: In production, it's recommended to specify specific origins instead of "*"
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }
}
