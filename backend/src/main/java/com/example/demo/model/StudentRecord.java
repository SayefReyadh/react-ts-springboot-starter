package com.example.demo.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * StudentRecord - Demonstrates Java Serialization.
 * 
 * Serialization: Converting an object into a byte stream for storage or transmission.
 * Deserialization: Converting byte stream back into an object.
 * 
 * Key Points:
 * - Class must implement Serializable interface
 * - serialVersionUID ensures version compatibility
 * - transient fields are NOT serialized
 * - static fields are NOT serialized
 */
public class StudentRecord implements Serializable {
    
    /**
     * serialVersionUID is used during deserialization to verify that
     * sender and receiver are compatible.
     * If not specified, JVM generates one automatically, but this can cause issues.
     */
    private static final long serialVersionUID = 1L;
    
    // These fields WILL be serialized
    private Long id;
    private String name;
    private String email;
    private int age;
    private double gpa;
    private LocalDate enrollmentDate;
    
    /**
     * transient keyword: Field will NOT be serialized.
     * Useful for sensitive data like passwords, or derived/calculated fields.
     */
    private transient String password; // Not serialized for security
    private transient int loginAttempts; // Not serialized (session data)
    
    /**
     * static fields are NOT serialized (belong to class, not instance).
     */
    private static int totalStudents = 0;
    
    // ==================== Constructors ====================
    
    public StudentRecord() {
        totalStudents++;
    }
    
    public StudentRecord(Long id, String name, String email, int age, double gpa, 
                        LocalDate enrollmentDate, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gpa = gpa;
        this.enrollmentDate = enrollmentDate;
        this.password = password;
        this.loginAttempts = 0;
        totalStudents++;
    }
    
    // ==================== Getters & Setters ====================
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public double getGpa() {
        return gpa;
    }
    
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    
    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public int getLoginAttempts() {
        return loginAttempts;
    }
    
    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }
    
    public static int getTotalStudents() {
        return totalStudents;
    }
    
    // ==================== toString ====================
    
    @Override
    public String toString() {
        return "StudentRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", gpa=" + gpa +
                ", enrollmentDate=" + enrollmentDate +
                ", password='" + (password != null ? "****" : "null") + '\'' +
                ", loginAttempts=" + loginAttempts +
                '}';
    }
}
