package com.example.demo.threading;

/**
 * Example 4: wait() and notify() Methods
 * 
 * Description:
 * Demonstrates inter-thread communication using wait() and notify().
 * - wait(): Causes current thread to wait until another thread notifies
 * - notify(): Wakes up a single thread that is waiting on this object's monitor
 * - notifyAll(): Wakes up all threads waiting on this object's monitor
 * 
 * These methods must be called within synchronized context.
 */
public class Example4_WaitNotify {
    
    // Shared resource class
    static class SharedResource {
        private int data = 0;
        private boolean hasData = false;
        
        // Producer method - produces data
        public synchronized void produce(int value) {
            // Wait if data is already available
            while (hasData) {
                try {
                    System.out.println("Producer waiting... (buffer full)");
                    wait(); // Release lock and wait for consumer to consume
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            // Produce data
            this.data = value;
            hasData = true;
            System.out.println("Produced: " + value);
            
            // Notify waiting consumer
            notify();
        }
        
        // Consumer method - consumes data
        public synchronized int consume() {
            // Wait if no data is available
            while (!hasData) {
                try {
                    System.out.println("Consumer waiting... (buffer empty)");
                    wait(); // Release lock and wait for producer to produce
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            // Consume data
            hasData = false;
            System.out.println("Consumed: " + data);
            
            // Notify waiting producer
            notify();
            
            return data;
        }
    }
    
    // Producer thread
    static class Producer extends Thread {
        private SharedResource resource;
        
        public Producer(SharedResource resource) {
            this.resource = resource;
        }
        
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                resource.produce(i);
                try {
                    Thread.sleep(500); // Simulate time taken to produce
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // Consumer thread
    static class Consumer extends Thread {
        private SharedResource resource;
        
        public Consumer(SharedResource resource) {
            this.resource = resource;
        }
        
        @Override
        public void run() {
            for (int i = 1; i <= 5; i++) {
                int value = resource.consume();
                try {
                    Thread.sleep(1000); // Simulate time taken to consume
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Example 4: wait() and notify() ===\n");
        System.out.println("Producer-Consumer Problem\n");
        
        // Create shared resource
        SharedResource sharedResource = new SharedResource();
        
        // Create producer and consumer threads
        Producer producer = new Producer(sharedResource);
        Consumer consumer = new Consumer(sharedResource);
        
        // Start both threads
        producer.start();
        consumer.start();
        
        // Wait for both to complete
        try {
            producer.join();
            consumer.join();
            System.out.println("\nAll operations completed successfully!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
