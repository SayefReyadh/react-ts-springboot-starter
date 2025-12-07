package com.example.demo.service;

import com.example.demo.comparator.CourseComparators;
import com.example.demo.model.Course;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple Collections Examples for Students
 * 
 * Learn about:
 * 1. ArrayList - Ordered list (allows duplicates)
 * 2. HashSet - Unique elements (no duplicates)
 * 3. Stack - LIFO (Last In, First Out)
 * 4. Queue - FIFO (First In, First Out)
 * 5. Comparable vs Comparator - Sorting objects
 */
@Service
public class CollectionsExamplesService {
    
    // ==================== 1. ARRAYLIST - Ordered List ====================
    
    /**
     * ArrayList Example: Store student names
     * - Maintains order
     * - Allows duplicates
     * - Good for: Lists of items, ordered data
     */
    public List<String> arrayListExample() {
        List<String> students = new ArrayList<>();
        students.add("Alice");
        students.add("Bob");
        students.add("Charlie");
        students.add("Diana");
        students.add("Bob"); // Duplicate allowed!
        return students;
    }
    
    /**
     * FILTER ArrayList: Get names starting with 'B'
     */
    public List<String> filterStudents(List<String> students) {
        return students.stream()
                .filter(name -> name.startsWith("B"))
                .collect(Collectors.toList());
    }
    
    /**
     * SORT ArrayList: Alphabetically
     */
    public List<String> sortStudents(List<String> students) {
        List<String> sorted = new ArrayList<>(students);
        Collections.sort(sorted);
        return sorted;
    }
    
    /**
     * TRANSFORM ArrayList: Convert to uppercase
     */
    public List<String> transformToUpperCase(List<String> students) {
        return students.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
    
    // ==================== 2. HASHSET - Unique Elements ====================
    
    /**
     * HashSet Example: Store unique emails
     * - No duplicates
     * - No guaranteed order
     * - Good for: Unique items, fast lookup
     */
    public Set<String> hashSetExample() {
        Set<String> emails = new HashSet<>();
        emails.add("alice@university.edu");
        emails.add("bob@university.edu");
        emails.add("charlie@university.edu");
        emails.add("alice@university.edu"); // Duplicate ignored!
        return emails; // Only 3 emails
    }
    
    /**
     * Remove duplicates using HashSet
     */
    public Set<String> removeDuplicates(List<String> listWithDuplicates) {
        return new HashSet<>(listWithDuplicates);
    }
    
    // ==================== 3. STACK - Last In, First Out ====================
    
    /**
     * Stack Example: Browser back button
     * - LIFO: Last In, First Out
     * - Good for: Undo operations, navigation history
     */
    public Stack<String> stackExample() {
        Stack<String> browserHistory = new Stack<>();
        browserHistory.push("google.com");      // Visit page 1
        browserHistory.push("facebook.com");    // Visit page 2
        browserHistory.push("youtube.com");     // Visit page 3
        return browserHistory;
    }
    
    /**
     * Go back (pop from stack)
     */
    public String goBack(Stack<String> history) {
        if (!history.isEmpty()) {
            history.pop(); // Remove current page
            return history.isEmpty() ? "No history" : history.peek();
        }
        return "Empty history";
    }
    
    // ==================== 4. QUEUE - First In, First Out ====================
    
    /**
     * Queue Example: Print queue
     * - FIFO: First In, First Out
     * - Good for: Task processing, waiting lines
     */
    public Queue<String> queueExample() {
        Queue<String> printQueue = new LinkedList<>();
        printQueue.offer("Document1.pdf");  // First in line
        printQueue.offer("Document2.pdf");
        printQueue.offer("Document3.pdf");  // Last in line
        return printQueue;
    }
    
    /**
     * Process next item in queue
     */
    public String processNext(Queue<String> queue) {
        return queue.isEmpty() ? "Queue is empty" : queue.poll();
    }
    
    // ==================== 5. COMPARABLE vs COMPARATOR ====================
    
    /**
     * Create sample courses for sorting examples
     */
    public List<Course> createSampleCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("CS101", "Intro to Programming", 3, 
                LocalDate.of(2025, 1, 15), "Dr. Smith", 120));
        courses.add(new Course("CS202", "Data Structures", 4, 
                LocalDate.of(2025, 1, 20), "Dr. Johnson", 85));
        courses.add(new Course("CS150", "Web Development", 3, 
                LocalDate.of(2025, 1, 25), "Dr. Davis", 110));
        return courses;
    }
    
    /**
     * COMPARABLE: Sort using natural ordering (by course code)
     * The Course class implements Comparable interface
     */
    public List<Course> sortByCourseCode(List<Course> courses) {
        List<Course> sorted = new ArrayList<>(courses);
        Collections.sort(sorted); // Uses Course.compareTo()
        return sorted; // CS101, CS150, CS202
    }
    
    /**
     * COMPARATOR: Sort by credits (custom logic)
     */
    public List<Course> sortByCredits(List<Course> courses) {
        List<Course> sorted = new ArrayList<>(courses);
        sorted.sort(new CourseComparators.ByCreditsDescending());
        return sorted; // 4 credits first, then 3 credits
    }
    
    /**
     * COMPARATOR: Sort by enrollment (most popular first)
     */
    public List<Course> sortByEnrollment(List<Course> courses) {
        List<Course> sorted = new ArrayList<>(courses);
        sorted.sort((c1, c2) -> Integer.compare(c2.getEnrolledStudents(), c1.getEnrolledStudents()));
        return sorted; // 120, 110, 85
    }
    
    /**
     * FILTER courses: Get courses with > 100 students
     */
    public List<Course> filterPopularCourses(List<Course> courses) {
        return courses.stream()
                .filter(c -> c.getEnrolledStudents() > 100)
                .collect(Collectors.toList());
    }
    
    /**
     * TRANSFORM courses: Extract course names only
     */
    public List<String> getCourseNames(List<Course> courses) {
        return courses.stream()
                .map(Course::getCourseName)
                .collect(Collectors.toList());
    }
}
