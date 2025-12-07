package com.example.demo.model;

import java.time.LocalDate;

/**
 * Course model demonstrating Comparable interface.
 * Implements natural ordering by course code.
 */
public class Course implements Comparable<Course> {
    
    private String courseCode;
    private String courseName;
    private int credits;
    private LocalDate startDate;
    private String instructor;
    private int enrolledStudents;
    
    // ==================== Constructors ====================
    
    public Course() {
    }
    
    public Course(String courseCode, String courseName, int credits, LocalDate startDate, 
                  String instructor, int enrolledStudents) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.startDate = startDate;
        this.instructor = instructor;
        this.enrolledStudents = enrolledStudents;
    }
    
    // ==================== Comparable Implementation ====================
    
    /**
     * Natural ordering: Sort by course code alphabetically.
     * This is used when calling Collections.sort() or using TreeSet.
     */
    @Override
    public int compareTo(Course other) {
        return this.courseCode.compareTo(other.courseCode);
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
    
    public int getCredits() {
        return credits;
    }
    
    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public String getInstructor() {
        return instructor;
    }
    
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    
    public int getEnrolledStudents() {
        return enrolledStudents;
    }
    
    public void setEnrolledStudents(int enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
    
    // ==================== toString ====================
    
    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", credits=" + credits +
                ", startDate=" + startDate +
                ", instructor='" + instructor + '\'' +
                ", enrolledStudents=" + enrolledStudents +
                '}';
    }
}
