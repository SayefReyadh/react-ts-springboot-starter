package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CollectionsExamplesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * REST Controller demonstrating Java Collections in a backend application.
 * All endpoints are accessible at: http://localhost:8080/api/collections/*
 */
@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "*")
public class CollectionsController {
    
    @Autowired
    private CollectionsExamplesService collectionsService;
    
    // ==================== ArrayList Endpoints ====================
    
    /**
     * GET /api/collections/arraylist/demo
     * Demonstrates ArrayList basic operations.
     */
    @GetMapping("/arraylist/demo")
    public ResponseEntity<Map<String, Object>> arrayListDemo() {
        List<String> students = collectionsService.arrayListExample();
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "ArrayList: Ordered collection, allows duplicates");
        response.put("originalList", students);
        response.put("size", students.size());
        response.put("containsDuplicates", students.size() != new HashSet<>(students).size());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * POST /api/collections/arraylist/filter
     * Filters ArrayList based on criteria.
     */
    @PostMapping("/arraylist/filter")
    public ResponseEntity<Map<String, Object>> filterArrayList(@RequestBody List<String> students) {
        List<String> filtered = collectionsService.filterArrayList(students);
        
        Map<String, Object> response = new HashMap<>();
        response.put("original", students);
        response.put("filtered", filtered);
        response.put("filterCriteria", "Names starting with 'B'");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * POST /api/collections/arraylist/sort
     * Sorts ArrayList alphabetically.
     */
    @PostMapping("/arraylist/sort")
    public ResponseEntity<Map<String, Object>> sortArrayList(@RequestBody List<String> students) {
        List<String> sorted = collectionsService.sortArrayList(students);
        
        Map<String, Object> response = new HashMap<>();
        response.put("original", students);
        response.put("sorted", sorted);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * POST /api/collections/arraylist/transform
     * Transforms ArrayList elements.
     */
    @PostMapping("/arraylist/transform")
    public ResponseEntity<Map<String, Object>> transformArrayList(@RequestBody List<String> students) {
        List<String> transformed = collectionsService.transformArrayList(students);
        
        Map<String, Object> response = new HashMap<>();
        response.put("original", students);
        response.put("transformed", transformed);
        response.put("transformation", "Converted to UPPERCASE");
        
        return ResponseEntity.ok(response);
    }
    
    // ==================== HashSet Endpoints ====================
    
    /**
     * GET /api/collections/hashset/demo
     * Demonstrates HashSet basic operations.
     */
    @GetMapping("/hashset/demo")
    public ResponseEntity<Map<String, Object>> hashSetDemo() {
        Set<String> emails = collectionsService.hashSetExample();
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "HashSet: Unordered, no duplicates, O(1) lookup");
        response.put("uniqueEmails", emails);
        response.put("size", emails.size());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * POST /api/collections/hashset/remove-duplicates
     * Removes duplicates from a list.
     */
    @PostMapping("/hashset/remove-duplicates")
    public ResponseEntity<Map<String, Object>> removeDuplicates(@RequestBody List<String> list) {
        Set<String> unique = collectionsService.removeDuplicates(list);
        
        Map<String, Object> response = new HashMap<>();
        response.put("originalList", list);
        response.put("originalSize", list.size());
        response.put("uniqueElements", unique);
        response.put("uniqueSize", unique.size());
        response.put("duplicatesRemoved", list.size() - unique.size());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/hashset/check-email?email=alice@university.edu
     * Checks if email exists in set.
     */
    @GetMapping("/hashset/check-email")
    public ResponseEntity<Map<String, Object>> checkEmail(@RequestParam String email) {
        Set<String> emails = collectionsService.hashSetExample();
        boolean exists = collectionsService.emailExists(emails, email);
        
        Map<String, Object> response = new HashMap<>();
        response.put("email", email);
        response.put("exists", exists);
        response.put("allEmails", emails);
        
        return ResponseEntity.ok(response);
    }
    
    // ==================== Stack Endpoints ====================
    
    /**
     * GET /api/collections/stack/demo
     * Demonstrates Stack (LIFO) operations.
     */
    @GetMapping("/stack/demo")
    public ResponseEntity<Map<String, Object>> stackDemo() {
        Stack<String> history = collectionsService.stackExample();
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "Stack: LIFO (Last In First Out)");
        response.put("navigationHistory", new ArrayList<>(history));
        response.put("currentPage", history.peek());
        response.put("size", history.size());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/stack/navigate-back
     * Simulates browser back button.
     */
    @GetMapping("/stack/navigate-back")
    public ResponseEntity<Map<String, Object>> navigateBack() {
        Stack<String> history = collectionsService.stackExample();
        String currentPage = history.peek();
        String previousPage = collectionsService.navigateBack(history);
        
        Map<String, Object> response = new HashMap<>();
        response.put("beforeNavigation", currentPage);
        response.put("afterNavigation", previousPage);
        response.put("remainingHistory", new ArrayList<>(history));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/stack/check-balanced?expression=(())
     * Checks if parentheses are balanced.
     */
    @GetMapping("/stack/check-balanced")
    public ResponseEntity<Map<String, Object>> checkBalanced(@RequestParam String expression) {
        boolean balanced = collectionsService.isBalancedParentheses(expression);
        
        Map<String, Object> response = new HashMap<>();
        response.put("expression", expression);
        response.put("isBalanced", balanced);
        
        return ResponseEntity.ok(response);
    }
    
    // ==================== Queue Endpoints ====================
    
    /**
     * GET /api/collections/queue/demo
     * Demonstrates Queue (FIFO) operations.
     */
    @GetMapping("/queue/demo")
    public ResponseEntity<Map<String, Object>> queueDemo() {
        Queue<String> queue = collectionsService.queueExample();
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "Queue: FIFO (First In First Out)");
        response.put("registrationQueue", new ArrayList<>(queue));
        response.put("nextStudent", queue.peek());
        response.put("size", queue.size());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/queue/process-next
     * Processes next student in queue.
     */
    @GetMapping("/queue/process-next")
    public ResponseEntity<Map<String, Object>> processNext() {
        Queue<String> queue = collectionsService.queueExample();
        String nextStudent = collectionsService.peekNextStudent(queue);
        String processed = collectionsService.processNextStudent(queue);
        
        Map<String, Object> response = new HashMap<>();
        response.put("processedStudent", processed);
        response.put("nextInQueue", collectionsService.peekNextStudent(queue));
        response.put("remainingQueue", new ArrayList<>(queue));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/priority-queue/demo
     * Demonstrates PriorityQueue.
     */
    @GetMapping("/priority-queue/demo")
    public ResponseEntity<Map<String, Object>> priorityQueueDemo() {
        PriorityQueue<Integer> grades = collectionsService.priorityQueueExample();
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "PriorityQueue: Elements ordered by priority");
        response.put("allGrades", new ArrayList<>(grades));
        response.put("highestGrade", grades.peek());
        
        return ResponseEntity.ok(response);
    }
    
    // ==================== Course Collections - Comparable/Comparator ====================
    
    /**
     * GET /api/collections/courses/all
     * Gets all sample courses.
     */
    @GetMapping("/courses/all")
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(collectionsService.createSampleCourses());
    }
    
    /**
     * GET /api/collections/courses/sort-natural
     * Sorts courses using Comparable (by course code).
     */
    @GetMapping("/courses/sort-natural")
    public ResponseEntity<Map<String, Object>> sortCoursesNatural() {
        List<Course> courses = collectionsService.createSampleCourses();
        List<Course> sorted = collectionsService.sortByCourseCodeNatural(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "Sorted using Comparable (natural ordering by course code)");
        response.put("sortedCourses", sorted);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/courses/sort-by-credits
     * Sorts courses by credits using Comparator.
     */
    @GetMapping("/courses/sort-by-credits")
    public ResponseEntity<Map<String, Object>> sortCoursesByCredits() {
        List<Course> courses = collectionsService.createSampleCourses();
        List<Course> sorted = collectionsService.sortByCredits(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "Sorted using Comparator (by credits descending)");
        response.put("sortedCourses", sorted);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/courses/sort-by-enrollment
     * Sorts courses by enrollment using Comparator.
     */
    @GetMapping("/courses/sort-by-enrollment")
    public ResponseEntity<Map<String, Object>> sortCoursesByEnrollment() {
        List<Course> courses = collectionsService.createSampleCourses();
        List<Course> sorted = collectionsService.sortByEnrollment(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "Sorted using Comparator (by enrolled students descending)");
        response.put("sortedCourses", sorted);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/courses/sort-by-date
     * Sorts courses by start date using Comparator.
     */
    @GetMapping("/courses/sort-by-date")
    public ResponseEntity<Map<String, Object>> sortCoursesByDate() {
        List<Course> courses = collectionsService.createSampleCourses();
        List<Course> sorted = collectionsService.sortByStartDate(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "Sorted using Comparator (by start date)");
        response.put("sortedCourses", sorted);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/courses/sort-by-instructor
     * Sorts courses by instructor using lambda Comparator.
     */
    @GetMapping("/courses/sort-by-instructor")
    public ResponseEntity<Map<String, Object>> sortCoursesByInstructor() {
        List<Course> courses = collectionsService.createSampleCourses();
        List<Course> sorted = collectionsService.sortByInstructor(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "Sorted using Lambda Comparator (by instructor name)");
        response.put("sortedCourses", sorted);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/courses/filter-popular
     * Filters popular courses (>90 students).
     */
    @GetMapping("/courses/filter-popular")
    public ResponseEntity<Map<String, Object>> filterPopularCourses() {
        List<Course> courses = collectionsService.createSampleCourses();
        List<Course> popular = collectionsService.filterPopularCourses(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "Courses with > 90 students");
        response.put("popularCourses", popular);
        response.put("count", popular.size());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/courses/filter-four-credits
     * Filters 4-credit courses.
     */
    @GetMapping("/courses/filter-four-credits")
    public ResponseEntity<Map<String, Object>> filterFourCreditCourses() {
        List<Course> courses = collectionsService.createSampleCourses();
        List<Course> fourCredit = collectionsService.filterFourCreditCourses(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "4-credit courses");
        response.put("courses", fourCredit);
        response.put("count", fourCredit.size());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/courses/unique-instructors
     * Gets unique instructors using Set.
     */
    @GetMapping("/courses/unique-instructors")
    public ResponseEntity<Map<String, Object>> getUniqueInstructors() {
        List<Course> courses = collectionsService.createSampleCourses();
        Set<String> instructors = collectionsService.getUniqueInstructors(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "Unique instructors extracted using Set");
        response.put("instructors", instructors);
        response.put("count", instructors.size());
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/courses/statistics
     * Gets course statistics using collection operations.
     */
    @GetMapping("/courses/statistics")
    public ResponseEntity<Map<String, Object>> getCourseStatistics() {
        List<Course> courses = collectionsService.createSampleCourses();
        
        Map<String, Object> response = new HashMap<>();
        response.put("totalCourses", courses.size());
        response.put("totalEnrollment", collectionsService.getTotalEnrollment(courses));
        response.put("mostPopularCourse", collectionsService.getMostPopularCourse(courses));
        response.put("uniqueInstructors", collectionsService.getUniqueInstructors(courses).size());
        response.put("coursesByInstructor", collectionsService.groupCoursesByInstructor(courses));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/courses/complex-filter
     * Complex filtering and sorting example.
     */
    @GetMapping("/courses/complex-filter")
    public ResponseEntity<Map<String, Object>> complexFilterAndSort() {
        List<Course> courses = collectionsService.createSampleCourses();
        List<Course> result = collectionsService.complexFilterAndSort(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("description", "4-credit courses sorted by enrollment (desc) then by course code");
        response.put("filteredAndSortedCourses", result);
        
        return ResponseEntity.ok(response);
    }
}
