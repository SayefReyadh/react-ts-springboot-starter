package com.example.demo.networking;

import java.io.*;
import java.net.*;

/**
 * Example 7: Simple TCP Server
 * 
 * Description:
 * A basic TCP server that:
 * - Listens on a specific port (8080)
 * - Accepts one client connection
 * - Receives messages from client
 * - Sends responses back to client
 * - Uses BufferedReader and PrintWriter for text communication
 * 
 * Run this BEFORE running the client program.
 */
public class Example7_SimpleTCPServer {
    
    public static void main(String[] args) {
        System.out.println("=== Example 7: Simple TCP Server ===\n");
        
        int port = 8080;
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        
        try {
            // Step 1: Create ServerSocket on port 8080
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            System.out.println("Waiting for client connection...");
            
            // Step 2: Accept client connection (blocking call)
            clientSocket = serverSocket.accept();
            System.out.println("Client connected from: " + 
                              clientSocket.getInetAddress().getHostAddress());
            
            // Step 3: Create input and output streams
            // BufferedReader to read text from client
            BufferedReader input = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            );
            
            // PrintWriter to send text to client
            PrintWriter output = new PrintWriter(
                clientSocket.getOutputStream(), 
                true // auto-flush enabled
            );
            
            // Step 4: Send welcome message to client
            output.println("Welcome to Simple TCP Server!");
            System.out.println("Sent: Welcome message");
            
            // Step 5: Communication loop - receive and respond to messages
            String clientMessage;
            while ((clientMessage = input.readLine()) != null) {
                System.out.println("Received from client: " + clientMessage);
                
                // Check for exit command
                if (clientMessage.equalsIgnoreCase("bye")) {
                    System.out.println("Client requested to close connection");
                    output.println("Goodbye! Connection closing...");
                    break;
                }
                
                // Echo the message back with a prefix
                String response = "Server Echo: " + clientMessage;
                output.println(response);
                System.out.println("Sent: " + response);
            }
            
            System.out.println("\nClient disconnected");
            
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Step 6: Close resources
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                    System.out.println("Client socket closed");
                }
                if (serverSocket != null && !serverSocket.isClosed()) {
                    serverSocket.close();
                    System.out.println("Server socket closed");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Server shutdown complete");
    }
}
