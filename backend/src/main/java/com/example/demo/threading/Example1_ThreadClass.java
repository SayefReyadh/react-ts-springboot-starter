package com.example.demo.threading;

/**
 * Example 1: Creating a Thread using Thread class
 * 
 * Description:
 * This example demonstrates how to create and start a thread by extending the Thread class.
 * The run() method contains the code that will be executed in the new thread.
 */
public class Example1_ThreadClass extends Thread {
    
    private String threadName;
    
    // Constructor to set thread name
    public Example1_ThreadClass(String name) {
        this.threadName = name;
    }
    
    // Override the run() method - contains code to be executed by thread
    @Override
    public void run() {
        System.out.println(threadName + " started execution");
        
        // Simulate some work by counting to 5
        for (int i = 1; i <= 5; i++) {
            System.out.println(threadName + " - Count: " + i);
            
            try {
                // Sleep for 500 milliseconds (0.5 seconds)
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println(threadName + " was interrupted");
                e.printStackTrace();
            }
        }
        
        System.out.println(threadName + " finished execution");
    }
    
    // Main method to demonstrate thread creation and execution
    public static void main(String[] args) {
        System.out.println("=== Example 1: Thread using Thread Class ===\n");
        
        // Create two thread objects
        Example1_ThreadClass thread1 = new Example1_ThreadClass("Thread-1");
        Example1_ThreadClass thread2 = new Example1_ThreadClass("Thread-2");
        
        // Start both threads - this calls the run() method in a new thread
        thread1.start();
        thread2.start();
        
        System.out.println("Main thread continues executing...");
        
        // Note: Both threads run concurrently with the main thread
    }
}
