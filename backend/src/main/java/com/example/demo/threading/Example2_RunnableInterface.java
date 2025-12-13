package com.example.demo.threading;

/**
 * Example 2: Creating a Thread using Runnable interface
 * 
 * Description:
 * This example demonstrates how to create threads by implementing the Runnable interface.
 * This approach is preferred when you need to extend another class (Java doesn't support multiple inheritance).
 */
public class Example2_RunnableInterface implements Runnable {
    
    private String taskName;
    
    // Constructor to set task name
    public Example2_RunnableInterface(String name) {
        this.taskName = name;
    }
    
    // Implement the run() method from Runnable interface
    @Override
    public void run() {
        System.out.println(taskName + " started by: " + Thread.currentThread().getName());
        
        // Simulate some work
        for (int i = 1; i <= 5; i++) {
            System.out.println(taskName + " - Progress: " + i + "/5");
            
            try {
                // Sleep for 400 milliseconds
                Thread.sleep(400);
            } catch (InterruptedException e) {
                System.out.println(taskName + " was interrupted");
                e.printStackTrace();
            }
        }
        
        System.out.println(taskName + " completed by: " + Thread.currentThread().getName());
    }
    
    // Main method to demonstrate Runnable interface usage
    public static void main(String[] args) {
        System.out.println("=== Example 2: Thread using Runnable Interface ===\n");
        
        // Create Runnable objects
        Example2_RunnableInterface task1 = new Example2_RunnableInterface("Task-A");
        Example2_RunnableInterface task2 = new Example2_RunnableInterface("Task-B");
        
        // Create Thread objects and pass Runnable instances to them
        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);
        
        // Optionally set thread names
        thread1.setName("Worker-Thread-1");
        thread2.setName("Worker-Thread-2");
        
        // Start the threads
        thread1.start();
        thread2.start();
        
        System.out.println("Main thread: " + Thread.currentThread().getName() + " continues...");
        
        // Alternative way using lambda expression (Java 8+)
        System.out.println("\n--- Using Lambda Expression ---");
        Thread thread3 = new Thread(() -> {
            for (int i = 1; i <= 3; i++) {
                System.out.println("Lambda Task - Step: " + i);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread3.start();
    }
}
