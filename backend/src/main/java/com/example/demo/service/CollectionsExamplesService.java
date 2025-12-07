package com.example.demo.service;

import com.example.demo.comparator.CourseComparators;
import com.example.demo.model.Course;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service demonstrating Java Collections framework.
 * Shows practical backend use cases for ArrayList, HashSet, Stack, and Queue.
 */
@Service
public class CollectionsExamplesService {
    
    // ==================== ArrayList Examples ====================
    
    /**
     * ArrayList: Ordered, allows duplicates, best for indexed access.
     * Use case: Maintaining course enrollment history, ordered lists.
     */
    public List<String> arrayListExample() {
        List<String> studentNames = new ArrayList<>();
        
        // Adding elements
        studentNames.add("Alice Johnson");
        studentNames.add("Bob Smith");
        studentNames.add("Charlie Brown");
        studentNames.add("Diana Prince");
        studentNames.add("Bob Smith"); // Duplicates allowed
        
        return studentNames;
    }
    
    /**
     * Filter ArrayList: Get students whose names start with 'B'.
     */
    public List<String> filterArrayList(List<String> students) {
        return students.stream()
                .filter(name -> name.startsWith("B"))
                .collect(Collectors.toList());
    }
    
    /**
     * Sort ArrayList: Alphabetically.
     */
    public List<String> sortArrayList(List<String> students) {
        List<String> sorted = new ArrayList<>(students);
        Collections.sort(sorted);
        return sorted;
    }
    
    /**
     * Transform ArrayList: Convert to uppercase.
     */
    public List<String> transformArrayList(List<String> students) {
        return students.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
    
    // ==================== HashSet Examples ====================
    
    /**
     * HashSet: Unordered, no duplicates, fast lookup.
     * Use case: Unique email addresses, preventing duplicate enrollments.
     */
    public Set<String> hashSetExample() {
        Set<String> uniqueEmails = new HashSet<>();
        
        uniqueEmails.add("alice@university.edu");
        uniqueEmails.add("bob@university.edu");
        uniqueEmails.add("charlie@university.edu");
        uniqueEmails.add("alice@university.edu"); // Duplicate ignored
        
        return uniqueEmails; // Only 3 elements
    }
    
    /**
     * Remove duplicates from a list using HashSet.
     */
    public Set<String> removeDuplicates(List<String> list) {
        return new HashSet<>(list);
    }
    
    /**
     * Check if email exists (O(1) time complexity).
     */
    public boolean emailExists(Set<String> emails, String email) {
        return emails.contains(email);
    }
    
    /**
     * Find common elements between two sets (intersection).
     */
    public Set<String> findCommonElements(Set<String> set1, Set<String> set2) {
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        return intersection;
    }
    
    // ==================== Stack Examples ====================
    
    /**
     * Stack: LIFO (Last In First Out).
     * Use case: Undo operations, navigation history, expression evaluation.
     */
    public Stack<String> stackExample() {
        Stack<String> pageHistory = new Stack<>();
        
        // Navigate pages
        pageHistory.push("Home Page");
        pageHistory.push("Courses Page");
        pageHistory.push("Student Profile");
        pageHistory.push("Edit Profile");
        
        return pageHistory;
    }
    
    /**
     * Simulate browser back button using Stack.
     */
    public String navigateBack(Stack<String> history) {
        if (!history.isEmpty()) {
            String currentPage = history.pop(); // Remove current page
            return history.isEmpty() ? "No previous page" : history.peek();
        }
        return "Empty history";
    }
    
    /**
     * Check if parentheses are balanced using Stack.
     * Example: "(())" -> balanced, "(()" -> not balanced
     */
    public boolean isBalancedParentheses(String expression) {
        Stack<Character> stack = new Stack<>();
        
        for (char ch : expression.toCharArray()) {
            if (ch == '(') {
                stack.push(ch);
            } else if (ch == ')') {
                if (stack.isEmpty()) {
                    return false;
                }
                stack.pop();
            }
        }
        
        return stack.isEmpty();
    }
    
    // ==================== Queue Examples ====================
    
    /**
     * Queue: FIFO (First In First Out).
     * Use case: Processing requests, task scheduling, message processing.
     */
    public Queue<String> queueExample() {
        Queue<String> registrationQueue = new LinkedList<>();
        
        // Students joining registration queue
        registrationQueue.offer("Alice");
        registrationQueue.offer("Bob");
        registrationQueue.offer("Charlie");
        registrationQueue.offer("Diana");
        
        return registrationQueue;
    }
    
    /**
     * Process registration queue: Handle next student.
     */
    public String processNextStudent(Queue<String> queue) {
        if (!queue.isEmpty()) {
            return queue.poll(); // Remove and return first element
        }
        return "Queue is empty";
    }
    
    /**
     * Peek at next student without removing.
     */
    public String peekNextStudent(Queue<String> queue) {
        return queue.isEmpty() ? "Queue is empty" : queue.peek();
    }
    
    /**
     * PriorityQueue: Elements ordered by priority (natural ordering or Comparator).
     * Use case: Task prioritization, emergency handling.
     */
    public PriorityQueue<Integer> priorityQueueExample() {
        PriorityQueue<Integer> grades = new PriorityQueue<>(Collections.reverseOrder());
        
        grades.offer(85);
        grades.offer(92);
        grades.offer(78);
        grades.offer(95);
        grades.offer(88);
        
        return grades; // Head is always highest grade (95)
    }
    
    // ==================== Course Collections with Comparable/Comparator ====================
    
    /**
     * Create sample courses for demonstration.
     */
    public List<Course> createSampleCourses() {
        List<Course> courses = new ArrayList<>();
        
        courses.add(new Course("CS101", "Introduction to Programming", 3, 
                LocalDate.of(2025, 1, 15), "Dr. Smith", 120));
        courses.add(new Course("CS202", "Data Structures", 4, 
                LocalDate.of(2025, 1, 20), "Dr. Johnson", 85));
        courses.add(new Course("CS305", "Database Systems", 3, 
                LocalDate.of(2025, 2, 1), "Dr. Williams", 95));
        courses.add(new Course("CS401", "Software Engineering", 4, 
                LocalDate.of(2025, 1, 15), "Dr. Brown", 70));
        courses.add(new Course("CS150", "Web Development", 3, 
                LocalDate.of(2025, 1, 25), "Dr. Davis", 110));
        
        return courses;
    }
    
    /**
     * Sort courses using Comparable (natural ordering by course code).
     */
    public List<Course> sortByCourseCodeNatural(List<Course> courses) {
        List<Course> sorted = new ArrayList<>(courses);
        Collections.sort(sorted); // Uses Comparable.compareTo()
        return sorted;
    }
    
    /**
     * Sort courses by credits using Comparator.
     */
    public List<Course> sortByCredits(List<Course> courses) {
        List<Course> sorted = new ArrayList<>(courses);
        sorted.sort(new CourseComparators.ByCreditsDescending());
        return sorted;
    }
    
    /**
     * Sort courses by enrollment using Comparator.
     */
    public List<Course> sortByEnrollment(List<Course> courses) {
        List<Course> sorted = new ArrayList<>(courses);
        sorted.sort(new CourseComparators.ByEnrolledStudentsDescending());
        return sorted;
    }
    
    /**
     * Sort courses by start date using Comparator.
     */
    public List<Course> sortByStartDate(List<Course> courses) {
        List<Course> sorted = new ArrayList<>(courses);
        sorted.sort(new CourseComparators.ByStartDate());
        return sorted;
    }
    
    /**
     * Sort courses by instructor using lambda Comparator.
     */
    public List<Course> sortByInstructor(List<Course> courses) {
        List<Course> sorted = new ArrayList<>(courses);
        sorted.sort(CourseComparators.BY_INSTRUCTOR);
        return sorted;
    }
    
    /**
     * Filter courses: Get courses with more than 90 students.
     */
    public List<Course> filterPopularCourses(List<Course> courses) {
        return courses.stream()
                .filter(course -> course.getEnrolledStudents() > 90)
                .collect(Collectors.toList());
    }
    
    /**
     * Filter courses: Get 4-credit courses.
     */
    public List<Course> filterFourCreditCourses(List<Course> courses) {
        return courses.stream()
                .filter(course -> course.getCredits() == 4)
                .collect(Collectors.toList());
    }
    
    /**
     * Transform courses: Extract course codes only.
     */
    public List<String> extractCourseCodes(List<Course> courses) {
        return courses.stream()
                .map(Course::getCourseCode)
                .collect(Collectors.toList());
    }
    
    /**
     * Get unique instructors using Set.
     */
    public Set<String> getUniqueInstructors(List<Course> courses) {
        return courses.stream()
                .map(Course::getInstructor)
                .collect(Collectors.toSet());
    }
    
    /**
     * Calculate total enrollment across all courses.
     */
    public int getTotalEnrollment(List<Course> courses) {
        return courses.stream()
                .mapToInt(Course::getEnrolledStudents)
                .sum();
    }
    
    /**
     * Find course with maximum enrollment.
     */
    public Optional<Course> getMostPopularCourse(List<Course> courses) {
        return courses.stream()
                .max(Comparator.comparingInt(Course::getEnrolledStudents));
    }
    
    /**
     * Group courses by instructor.
     */
    public Map<String, List<Course>> groupCoursesByInstructor(List<Course> courses) {
        return courses.stream()
                .collect(Collectors.groupingBy(Course::getInstructor));
    }
    
    /**
     * Complex example: Compound sorting and filtering.
     * Get 4-credit courses, sorted by enrollment descending, then by course code.
     */
    public List<Course> complexFilterAndSort(List<Course> courses) {
        return courses.stream()
                .filter(course -> course.getCredits() == 4)
                .sorted(Comparator.comparingInt(Course::getEnrolledStudents)
                        .reversed()
                        .thenComparing(Course::getCourseCode))
                .collect(Collectors.toList());
    }
}
