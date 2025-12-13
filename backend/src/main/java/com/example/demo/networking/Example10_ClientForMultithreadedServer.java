package com.example.demo.networking;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Example 10: Client for Multithreaded Server
 * 
 * Description:
 * Client that connects to the multithreaded server (Example 9).
 * 
 * Special commands:
 * - 'time' : Get server time
 * - 'info' : Get your client information
 * - 'bye'  : Disconnect from server
 * 
 * You can run multiple instances of this client simultaneously
 * to see the multithreaded server in action!
 */
public class Example10_ClientForMultithreadedServer {
    
    public static void main(String[] args) {
        System.out.println("=== Example 10: Client for Multithreaded Server ===\n");
        
        String serverAddress = "localhost";
        int serverPort = 8080;
        
        try (
            Socket socket = new Socket(serverAddress, serverPort);
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            PrintWriter output = new PrintWriter(
                socket.getOutputStream(), true
            );
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to multithreaded server!");
            
            // Read and display welcome messages from server
            System.out.println("\n--- Server Messages ---");
            for (int i = 0; i < 3; i++) {
                String message = input.readLine();
                if (message != null) {
                    System.out.println(message);
                }
            }
            
            System.out.println("\n--- Special Commands ---");
            System.out.println("'time' - Get server time");
            System.out.println("'info' - Get client information");
            System.out.println("'bye'  - Disconnect");
            System.out.println("----------------------\n");
            
            // Communication loop
            while (true) {
                System.out.print("You: ");
                String message = scanner.nextLine();
                
                // Send message to server
                output.println(message);
                
                // Read server response
                String response;
                
                // For 'info' command, read multiple lines
                if (message.equalsIgnoreCase("info")) {
                    System.out.println("Server response:");
                    for (int i = 0; i < 3; i++) {
                        response = input.readLine();
                        if (response != null) {
                            System.out.println("  " + response);
                        }
                    }
                } else {
                    // Read single line response
                    response = input.readLine();
                    if (response != null) {
                        System.out.println("Server: " + response);
                    }
                }
                
                // Exit if user typed 'bye'
                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("Disconnected from server");
                    break;
                }
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Cannot find server: " + serverAddress);
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Client shutdown complete");
    }
}
