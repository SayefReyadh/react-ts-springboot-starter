package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

/**
 * WebSocket Configuration
 * 
 * Enables WebSocket support in Spring Boot for real-time bidirectional communication.
 * 
 * Key Concepts:
 * - WebSocket: Full-duplex communication over a single TCP connection
 * - STOMP: Simple Text Oriented Messaging Protocol
 * - Message Broker: Routes messages between clients and server
 * 
 * This is the MODERN alternative to raw TCP sockets!
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    /**
     * Configure message broker
     * - /topic: For broadcasting to multiple subscribers (pub-sub)
     * - /queue: For point-to-point messaging
     * - /app: Application destination prefix for @MessageMapping
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable simple in-memory message broker
        // In production, use RabbitMQ, ActiveMQ, or Kafka
        config.enableSimpleBroker("/topic", "/queue");
        
        // Prefix for messages FROM client TO server
        config.setApplicationDestinationPrefixes("/app");
    }
    
    /**
     * Register WebSocket endpoints
     * - /ws: WebSocket connection endpoint
     * - withSockJS(): Fallback options for browsers that don't support WebSocket
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Enable SockJS fallback
        
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*"); // For native WebSocket clients
    }
}
