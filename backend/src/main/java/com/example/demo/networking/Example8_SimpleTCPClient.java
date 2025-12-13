package com.example.demo.networking;

import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Example 8: Simple TCP Client
 * 
 * Description:
 * A basic TCP client that:
 * - Connects to server on localhost:8080
 * - Sends user input to server
 * - Receives and displays server responses
 * - Allows user to type messages via console
 * 
 * Run the SERVER first, then run this client.
 */
public class Example8_SimpleTCPClient {
    
    public static void main(String[] args) {
        System.out.println("=== Example 8: Simple TCP Client ===\n");
        
        String serverAddress = "localhost";
        int serverPort = 8080;
        
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);
        
        try {
            // Step 1: Connect to server
            System.out.println("Connecting to server at " + serverAddress + ":" + serverPort);
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server!");
            
            // Step 2: Create input and output streams
            // BufferedReader to read text from server
            BufferedReader input = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            
            // PrintWriter to send text to server
            PrintWriter output = new PrintWriter(
                socket.getOutputStream(), 
                true // auto-flush enabled
            );
            
            // Step 3: Read welcome message from server
            String welcomeMessage = input.readLine();
            System.out.println("Server says: " + welcomeMessage);
            
            // Step 4: Communication loop - send messages to server
            System.out.println("\n--- Type your messages (type 'bye' to exit) ---");
            
            while (true) {
                // Get user input
                System.out.print("You: ");
                String message = scanner.nextLine();
                
                // Send message to server
                output.println(message);
                
                // Read server response
                String response = input.readLine();
                if (response != null) {
                    System.out.println("Server: " + response);
                }
                
                // Exit if user typed 'bye'
                if (message.equalsIgnoreCase("bye")) {
                    System.out.println("Closing connection...");
                    break;
                }
            }
            
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverAddress);
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Step 5: Close resources
            try {
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                    System.out.println("Socket closed");
                }
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Client shutdown complete");
    }
}
