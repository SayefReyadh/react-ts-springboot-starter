package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Controller
 * 
 * Modern alternative to raw TCP sockets using WebSocket protocol.
 * 
 * Advantages over raw sockets:
 * - Browser-native support
 * - Automatic reconnection
 * - Message formatting (JSON)
 * - Built-in authentication/authorization
 * - Load balancing support
 */
@Controller
public class WebSocketController {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    // Track connected users
    private final Map<String, String> connectedUsers = new ConcurrentHashMap<>();
    
    /**
     * Handle chat messages
     * 
     * Client sends to: /app/chat
     * Server broadcasts to: /topic/messages
     * 
     * Similar to TCP server broadcasting to all clients
     */
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage handleChatMessage(ChatMessage message) {
        message.setTimestamp(LocalDateTime.now().toString());
        System.out.println("Received chat message: " + message.getContent() + 
                          " from " + message.getSender());
        return message;
    }
    
    /**
     * Handle private messages (point-to-point)
     * 
     * Client sends to: /app/private
     * Server sends to specific user: /queue/private
     * 
     * Similar to one-to-one TCP communication
     */
    @MessageMapping("/private")
    public void handlePrivateMessage(PrivateMessage message) {
        message.setTimestamp(LocalDateTime.now().toString());
        
        // Send to specific user
        messagingTemplate.convertAndSendToUser(
            message.getRecipient(),
            "/queue/private",
            message
        );
        
        System.out.println("Private message from " + message.getSender() + 
                          " to " + message.getRecipient());
    }
    
    /**
     * Handle user join
     * 
     * Client sends to: /app/join
     * Server broadcasts to: /topic/users
     */
    @MessageMapping("/join")
    @SendTo("/topic/users")
    public UserEvent handleUserJoin(UserJoinMessage message) {
        connectedUsers.put(message.getUserId(), message.getUsername());
        
        UserEvent event = new UserEvent();
        event.setType("JOIN");
        event.setUsername(message.getUsername());
        event.setTimestamp(LocalDateTime.now().toString());
        event.setOnlineCount(connectedUsers.size());
        
        System.out.println("User joined: " + message.getUsername() + 
                          " (Total: " + connectedUsers.size() + ")");
        
        return event;
    }
    
    /**
     * Handle user leave
     */
    @MessageMapping("/leave")
    @SendTo("/topic/users")
    public UserEvent handleUserLeave(UserLeaveMessage message) {
        connectedUsers.remove(message.getUserId());
        
        UserEvent event = new UserEvent();
        event.setType("LEAVE");
        event.setUsername(message.getUsername());
        event.setTimestamp(LocalDateTime.now().toString());
        event.setOnlineCount(connectedUsers.size());
        
        System.out.println("User left: " + message.getUsername() + 
                          " (Total: " + connectedUsers.size() + ")");
        
        return event;
    }
    
    /**
     * REST endpoint to broadcast server announcements
     * POST /api/websocket/broadcast
     */
    @PostMapping("/api/websocket/broadcast")
    @ResponseBody
    public Map<String, Object> broadcastMessage(@RequestBody Map<String, String> payload) {
        String message = payload.get("message");
        
        ChatMessage announcement = new ChatMessage();
        announcement.setSender("SERVER");
        announcement.setContent(message);
        announcement.setTimestamp(LocalDateTime.now().toString());
        
        // Broadcast to all subscribers of /topic/messages
        messagingTemplate.convertAndSend("/topic/messages", announcement);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Broadcasted to all connected clients");
        response.put("recipientCount", connectedUsers.size());
        
        return response;
    }
    
    /**
     * Get connected users count
     * GET /api/websocket/users/count
     */
    @GetMapping("/api/websocket/users/count")
    @ResponseBody
    public Map<String, Object> getUsersCount() {
        Map<String, Object> response = new HashMap<>();
        response.put("onlineUsers", connectedUsers.size());
        response.put("users", new ArrayList<>(connectedUsers.values()));
        return response;
    }
    
    // ========== Message Models ==========
    
    public static class ChatMessage {
        private String sender;
        private String content;
        private String timestamp;
        
        public String getSender() { return sender; }
        public void setSender(String sender) { this.sender = sender; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    }
    
    public static class PrivateMessage {
        private String sender;
        private String recipient;
        private String content;
        private String timestamp;
        
        public String getSender() { return sender; }
        public void setSender(String sender) { this.sender = sender; }
        public String getRecipient() { return recipient; }
        public void setRecipient(String recipient) { this.recipient = recipient; }
        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    }
    
    public static class UserJoinMessage {
        private String userId;
        private String username;
        
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
    }
    
    public static class UserLeaveMessage {
        private String userId;
        private String username;
        
        public String getUserId() { return userId; }
        public void setUserId(String userId) { this.userId = userId; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
    }
    
    public static class UserEvent {
        private String type;
        private String username;
        private String timestamp;
        private int onlineCount;
        
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getTimestamp() { return timestamp; }
        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
        public int getOnlineCount() { return onlineCount; }
        public void setOnlineCount(int onlineCount) { this.onlineCount = onlineCount; }
    }
}
