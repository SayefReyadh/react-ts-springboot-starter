package com.example.demo.threading;

/**
 * Example 3: Thread Lifecycle Methods - sleep() and yield()
 * 
 * Description:
 * Demonstrates the use of sleep() and yield() methods:
 * - sleep(): Pauses thread execution for a specified time
 * - yield(): Hints to scheduler that current thread is willing to yield its current use of CPU
 */
public class Example3_ThreadLifecycleMethods {
    
    // Inner class demonstrating sleep()
    static class SleepExample extends Thread {
        private String threadName;
        
        public SleepExample(String name) {
            this.threadName = name;
        }
        
        @Override
        public void run() {
            System.out.println(threadName + " started");
            
            for (int i = 1; i <= 3; i++) {
                System.out.println(threadName + " - Iteration: " + i);
                
                try {
                    // Thread sleeps for 1 second
                    System.out.println(threadName + " is sleeping for 1 second...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(threadName + " was interrupted during sleep");
                    e.printStackTrace();
                }
            }
            
            System.out.println(threadName + " finished");
        }
    }
    
    // Inner class demonstrating yield()
    static class YieldExample extends Thread {
        private String threadName;
        
        public YieldExample(String name) {
            this.threadName = name;
        }
        
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                System.out.println(threadName + " - Count: " + i);
                
                // Yield gives a hint to scheduler that this thread is willing to yield
                // Other threads of same priority may get a chance to execute
                Thread.yield();
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Example 3: Thread Lifecycle Methods ===\n");
        
        // Demonstrate sleep()
        System.out.println("--- Demonstrating sleep() ---");
        SleepExample sleepThread = new SleepExample("SleepThread");
        sleepThread.start();
        
        // Wait for sleep thread to complete
        try {
            sleepThread.join(); // Main thread waits for sleepThread to finish
            System.out.println("\n--- Demonstrating yield() ---");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Demonstrate yield()
        YieldExample yieldThread1 = new YieldExample("YieldThread-1");
        YieldExample yieldThread2 = new YieldExample("YieldThread-2");
        
        yieldThread1.start();
        yieldThread2.start();
        
        System.out.println("\nNote: yield() is just a hint to scheduler.");
        System.out.println("You may or may not see interleaved execution.");
    }
}
