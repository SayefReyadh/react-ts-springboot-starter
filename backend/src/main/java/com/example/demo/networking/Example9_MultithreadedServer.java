package com.example.demo.networking;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Example 9: Multithreaded TCP Server
 * 
 * Description:
 * An advanced TCP server that can handle MULTIPLE clients simultaneously.
 * - Uses threads to handle each client connection
 * - Allows concurrent connections
 * - Each client gets its own dedicated thread
 * - Demonstrates practical use of multithreading with networking
 * 
 * This server can accept multiple clients at the same time!
 */
public class Example9_MultithreadedServer {
    
    // Counter for client connections
    private static AtomicInteger clientCounter = new AtomicInteger(0);
    
    public static void main(String[] args) {
        System.out.println("=== Example 9: Multithreaded TCP Server ===\n");
        
        int port = 8080;
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Multithreaded server started on port " + port);
            System.out.println("Server can handle multiple clients simultaneously");
            System.out.println("Waiting for clients to connect...\n");
            
            // Infinite loop to accept multiple clients
            while (true) {
                // Accept client connection (blocking call)
                Socket clientSocket = serverSocket.accept();
                
                // Increment client counter
                int clientId = clientCounter.incrementAndGet();
                
                System.out.println("New client connected! [Client-" + clientId + "] from " + 
                                  clientSocket.getInetAddress().getHostAddress());
                
                // Create a new thread to handle this client
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientId);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
                
                System.out.println("Thread started for Client-" + clientId);
                System.out.println("Active clients: " + clientCounter.get() + "\n");
            }
            
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Inner class to handle individual client connections
     * Implements Runnable so it can be executed in a separate thread
     */
    static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private int clientId;
        
        public ClientHandler(Socket socket, int id) {
            this.clientSocket = socket;
            this.clientId = id;
        }
        
        @Override
        public void run() {
            System.out.println("[Client-" + clientId + "] Handler thread started");
            
            try (
                BufferedReader input = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
                );
                PrintWriter output = new PrintWriter(
                    clientSocket.getOutputStream(), true
                )
            ) {
                // Send welcome message
                output.println("Welcome to Multithreaded Server! You are Client-" + clientId);
                output.println("This server can handle multiple clients simultaneously.");
                output.println("Type your messages (type 'bye' to exit)");
                
                // Handle client messages
                String clientMessage;
                while ((clientMessage = input.readLine()) != null) {
                    System.out.println("[Client-" + clientId + "] Received: " + clientMessage);
                    
                    // Check for exit command
                    if (clientMessage.equalsIgnoreCase("bye")) {
                        output.println("Goodbye Client-" + clientId + "!");
                        System.out.println("[Client-" + clientId + "] Requested disconnect");
                        break;
                    }
                    
                    // Special command: 'time' - send current time
                    if (clientMessage.equalsIgnoreCase("time")) {
                        output.println("Server time: " + new java.util.Date());
                        continue;
                    }
                    
                    // Special command: 'info' - send client info
                    if (clientMessage.equalsIgnoreCase("info")) {
                        output.println("Your client ID: " + clientId);
                        output.println("Your IP: " + clientSocket.getInetAddress());
                        output.println("Active clients: " + clientCounter.get());
                        continue;
                    }
                    
                    // Echo message back with client ID
                    output.println("[Echo from server to Client-" + clientId + "] " + clientMessage);
                }
                
            } catch (IOException e) {
                System.err.println("[Client-" + clientId + "] Error: " + e.getMessage());
            } finally {
                // Close client socket and decrement counter
                try {
                    clientSocket.close();
                    clientCounter.decrementAndGet();
                    System.out.println("[Client-" + clientId + "] Disconnected");
                    System.out.println("Active clients: " + clientCounter.get() + "\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
