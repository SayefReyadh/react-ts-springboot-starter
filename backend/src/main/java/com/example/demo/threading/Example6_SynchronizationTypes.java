package com.example.demo.threading;

/**
 * Example 6: Synchronized Method vs Synchronized Block
 * 
 * Description:
 * Demonstrates the difference between synchronized methods and synchronized blocks.
 * - Synchronized method: Locks the entire method
 * - Synchronized block: Locks only a specific code block (more granular control)
 */
public class Example6_SynchronizationTypes {
    
    private int balance = 1000;
    private int transactionCount = 0;
    
    // METHOD 1: Synchronized Method
    // The entire method is locked - only one thread can execute it at a time
    public synchronized void withdrawUsingSyncMethod(int amount) {
        System.out.println(Thread.currentThread().getName() + 
                          " attempting to withdraw $" + amount);
        
        if (balance >= amount) {
            System.out.println(Thread.currentThread().getName() + 
                              " checking balance: $" + balance);
            
            // Simulate processing time
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + 
                              " completed withdrawal. New balance: $" + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + 
                              " withdrawal failed - insufficient funds");
        }
    }
    
    // METHOD 2: Synchronized Block
    // Only the critical section is locked - more efficient
    public void withdrawUsingSyncBlock(int amount) {
        System.out.println(Thread.currentThread().getName() + 
                          " attempting to withdraw $" + amount);
        
        // Code outside synchronized block can run concurrently
        transactionCount++; // This could be moved to sync block in production
        
        // Synchronized block - locks on 'this' object
        synchronized (this) {
            if (balance >= amount) {
                System.out.println(Thread.currentThread().getName() + 
                                  " checking balance: $" + balance);
                
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + 
                                  " completed withdrawal. New balance: $" + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + 
                                  " withdrawal failed - insufficient funds");
            }
        }
        // End of synchronized block
    }
    
    // METHOD 3: Synchronized Block with specific lock object
    private final Object lock = new Object();
    
    public void withdrawWithCustomLock(int amount) {
        // Synchronized on a specific lock object instead of 'this'
        synchronized (lock) {
            if (balance >= amount) {
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + 
                                  " withdrew $" + amount + 
                                  ". Balance: $" + balance);
            }
        }
    }
    
    public int getBalance() {
        return balance;
    }
    
    public void resetBalance() {
        balance = 1000;
    }
    
    // Main method to demonstrate
    public static void main(String[] args) {
        System.out.println("=== Example 6: Synchronized Method vs Block ===\n");
        
        Example6_SynchronizationTypes account = new Example6_SynchronizationTypes();
        
        // Test 1: Using synchronized method
        System.out.println("--- Test 1: Synchronized Method ---");
        Thread t1 = new Thread(() -> account.withdrawUsingSyncMethod(600), "Thread-1");
        Thread t2 = new Thread(() -> account.withdrawUsingSyncMethod(500), "Thread-2");
        
        t1.start();
        t2.start();
        
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nFinal balance: $" + account.getBalance());
        
        // Reset for next test
        account.resetBalance();
        
        // Test 2: Using synchronized block
        System.out.println("\n--- Test 2: Synchronized Block ---");
        Thread t3 = new Thread(() -> account.withdrawUsingSyncBlock(600), "Thread-3");
        Thread t4 = new Thread(() -> account.withdrawUsingSyncBlock(500), "Thread-4");
        
        t3.start();
        t4.start();
        
        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("\nFinal balance: $" + account.getBalance());
        
        System.out.println("\n--- Key Differences ---");
        System.out.println("1. Synchronized Method: Locks entire method");
        System.out.println("2. Synchronized Block: Locks only critical section");
        System.out.println("3. Synchronized Block allows finer control over lock scope");
    }
}
