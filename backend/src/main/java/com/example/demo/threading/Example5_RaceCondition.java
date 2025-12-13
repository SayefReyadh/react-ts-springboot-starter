package com.example.demo.threading;

/**
 * Example 5: Race Condition Problem and Solution
 * 
 * Description:
 * Demonstrates a race condition when multiple threads access shared data without synchronization,
 * and shows how to fix it using synchronization.
 */
public class Example5_RaceCondition {
    
    // Shared counter WITHOUT synchronization (demonstrates race condition)
    static class UnsafeCounter {
        private int count = 0;
        
        // NOT synchronized - multiple threads can execute this simultaneously
        public void increment() {
            count++; // This is actually 3 operations: read, increment, write
        }
        
        public int getCount() {
            return count;
        }
    }
    
    // Shared counter WITH synchronization (fixes race condition)
    static class SafeCounter {
        private int count = 0;
        
        // Synchronized method - only one thread can execute this at a time
        public synchronized void increment() {
            count++;
        }
        
        public int getCount() {
            return count;
        }
    }
    
    // Worker thread that increments counter
    static class IncrementTask extends Thread {
        private Object counter;
        
        public IncrementTask(Object counter) {
            this.counter = counter;
        }
        
        @Override
        public void run() {
            // Each thread increments 1000 times
            for (int i = 0; i < 1000; i++) {
                if (counter instanceof UnsafeCounter) {
                    ((UnsafeCounter) counter).increment();
                } else if (counter instanceof SafeCounter) {
                    ((SafeCounter) counter).increment();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Example 5: Race Condition and Solution ===\n");
        
        // Demonstrate UNSAFE counter (with race condition)
        System.out.println("--- Part 1: WITHOUT Synchronization (Race Condition) ---");
        UnsafeCounter unsafeCounter = new UnsafeCounter();
        
        // Create 5 threads that will increment unsafeCounter
        Thread[] unsafeThreads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            unsafeThreads[i] = new IncrementTask(unsafeCounter);
            unsafeThreads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : unsafeThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Expected count: 5000 (5 threads × 1000 increments)");
        System.out.println("Actual count: " + unsafeCounter.getCount());
        System.out.println("Lost updates due to race condition!\n");
        
        // Demonstrate SAFE counter (with synchronization)
        System.out.println("--- Part 2: WITH Synchronization (Problem Fixed) ---");
        SafeCounter safeCounter = new SafeCounter();
        
        // Create 5 threads that will increment safeCounter
        Thread[] safeThreads = new Thread[5];
        for (int i = 0; i < 5; i++) {
            safeThreads[i] = new IncrementTask(safeCounter);
            safeThreads[i].start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : safeThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Expected count: 5000 (5 threads × 1000 increments)");
        System.out.println("Actual count: " + safeCounter.getCount());
        System.out.println("Synchronization prevents race condition!");
    }
}
