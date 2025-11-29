package com.example.demo.model;

import jakarta.persistence.*;

/**
 * Student entity representing a student in the database.
 * This class is mapped to the "students" table using JPA (Java Persistence API).
 * 
 * @Entity marks this class as a JPA entity
 * @Table specifies the table name in the database
 */
@Entity
@Table(name = "students")
public class Student {
    
    /**
     * Primary key for the Student entity.
     * @Id marks this field as the primary key
     * @GeneratedValue with IDENTITY strategy means the database auto-generates the ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Student's full name.
     * @Column(nullable = false) means this field is required in the database
     */
    @Column(nullable = false)
    private String name;
    
    /**
     * Student's email address.
     * @Column(nullable = false) makes this field mandatory
     */
    @Column(nullable = false)
    private String email;
    
    /**
     * University-assigned student ID.
     * @Column specifies:
     * - name = "university_id": Maps to column name in database
     * - nullable = false: Field is required
     * - unique = true: No two students can have the same university ID
     */
    @Column(name = "university_id", nullable = false, unique = true)
    private String universityId;
    
    // ==================== Constructors ====================
    
    /**
     * Default no-argument constructor.
     * Required by JPA for entity instantiation.
     */
    public Student() {
    }
    
    /**
     * Constructor with parameters for creating a new Student.
     * @param name Student's full name
     * @param email Student's email address
     * @param universityId Student's university ID
     */
    public Student(String name, String email, String universityId) {
        this.name = name;
        this.email = email;
        this.universityId = universityId;
    }
    
    // ==================== Getters and Setters ====================
    
    /**
     * Gets the student's ID.
     * @return The unique identifier of the student
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Sets the student's ID.
     * @param id The unique identifier to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the student's name.
     * @return The student's full name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the student's name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Gets the student's email address.
     * @return The student's email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the student's email address.
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Gets the student's university ID.
     * @return The university-assigned student ID
     */
    public String getUniversityId() {
        return universityId;
    }
    
    /**
     * Sets the student's university ID.
     * @param universityId The university ID to set
     */
    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }
}
