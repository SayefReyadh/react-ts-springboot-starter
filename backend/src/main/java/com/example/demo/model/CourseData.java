package com.example.demo.model;

import java.io.Serializable;
import java.util.List;

/**
 * CourseData - Another example of Serializable class.
 * Demonstrates serialization with collections.
 */
public class CourseData implements Serializable {
    
    private static final long serialVersionUID = 2L;
    
    private String courseCode;
    private String courseName;
    private String instructor;
    private List<String> enrolledStudents; // Collections are serializable
    private int capacity;
    
    // ==================== Constructors ====================
    
    public CourseData() {
    }
    
    public CourseData(String courseCode, String courseName, String instructor, 
                     List<String> enrolledStudents, int capacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructor = instructor;
        this.enrolledStudents = enrolledStudents;
        this.capacity = capacity;
    }
    
    // ==================== Getters & Setters ====================
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
    
    public String getCourseName() {
        return courseName;
    }
    
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }
    
    public void setEnrolledStudents(List<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    
    // ==================== toString ====================
    
    @Override
    public String toString() {
        return "CourseData{" +
                "courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", instructor='" + instructor + '\'' +
                ", enrolledStudents=" + enrolledStudents +
                ", capacity=" + capacity +
                '}';
    }
}
