package com.example.demo.comparator;

import com.example.demo.model.Course;
import java.util.Comparator;

/**
 * Custom Comparators for Course objects.
 * Demonstrates different ways to sort courses using Comparator interface.
 * 
 * Key Differences:
 * - Comparable: Natural ordering (defined inside the class)
 * - Comparator: Custom ordering (defined outside the class)
 */
public class CourseComparators {
    
    /**
     * Sort courses by credits (descending order).
     * Useful for finding most intensive courses.
     */
    public static class ByCreditsDescending implements Comparator<Course> {
        @Override
        public int compare(Course c1, Course c2) {
            return Integer.compare(c2.getCredits(), c1.getCredits());
        }
    }
    
    /**
     * Sort courses by enrolled students (descending order).
     * Useful for finding most popular courses.
     */
    public static class ByEnrolledStudentsDescending implements Comparator<Course> {
        @Override
        public int compare(Course c1, Course c2) {
            return Integer.compare(c2.getEnrolledStudents(), c1.getEnrolledStudents());
        }
    }
    
    /**
     * Sort courses by start date (earliest first).
     * Useful for academic planning.
     */
    public static class ByStartDate implements Comparator<Course> {
        @Override
        public int compare(Course c1, Course c2) {
            return c1.getStartDate().compareTo(c2.getStartDate());
        }
    }
    
    /**
     * Sort courses by instructor name alphabetically.
     * Useful for organizing by faculty.
     */
    public static class ByInstructor implements Comparator<Course> {
        @Override
        public int compare(Course c1, Course c2) {
            return c1.getInstructor().compareTo(c2.getInstructor());
        }
    }
    
    /**
     * Sort courses by course name alphabetically.
     */
    public static class ByCourseName implements Comparator<Course> {
        @Override
        public int compare(Course c1, Course c2) {
            return c1.getCourseName().compareTo(c2.getCourseName());
        }
    }
    
    // ==================== Using Lambda Expressions (Modern Java) ====================
    
    /**
     * Modern approach using static lambda comparators.
     * These are more concise alternatives to the classes above.
     */
    public static final Comparator<Course> BY_CREDITS_DESC = 
        (c1, c2) -> Integer.compare(c2.getCredits(), c1.getCredits());
    
    public static final Comparator<Course> BY_ENROLLED_DESC = 
        (c1, c2) -> Integer.compare(c2.getEnrolledStudents(), c1.getEnrolledStudents());
    
    public static final Comparator<Course> BY_START_DATE = 
        Comparator.comparing(Course::getStartDate);
    
    public static final Comparator<Course> BY_INSTRUCTOR = 
        Comparator.comparing(Course::getInstructor);
    
    public static final Comparator<Course> BY_COURSE_NAME = 
        Comparator.comparing(Course::getCourseName);
    
    /**
     * Compound comparator: Sort by credits first, then by enrolled students.
     * Demonstrates chaining comparators.
     */
    public static final Comparator<Course> BY_CREDITS_THEN_ENROLLMENT = 
        Comparator.comparing(Course::getCredits)
                  .reversed()
                  .thenComparing(Course::getEnrolledStudents, Comparator.reverseOrder());
}
