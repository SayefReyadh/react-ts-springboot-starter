package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.service.CollectionsExamplesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Simple Collections Examples for Students
 * Test at: http://localhost:8080/api/collections/*
 */
@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "*")
public class CollectionsController {
    
    @Autowired
    private CollectionsExamplesService collectionsService;
    
    // ==================== EXAMPLE 1: ARRAYLIST ====================
    
    /**
     * GET /api/collections/demo/arraylist
     * Demo: ArrayList basics (ordered, allows duplicates)
     */
    @GetMapping("/demo/arraylist")
    public ResponseEntity<Map<String, Object>> demoArrayList() {
        // Create ArrayList
        List<String> students = collectionsService.arrayListExample();
        
        // Filter (names starting with 'B')
        List<String> filtered = collectionsService.filterStudents(students);
        
        // Sort (alphabetically)
        List<String> sorted = collectionsService.sortStudents(students);
        
        // Transform (to uppercase)
        List<String> uppercase = collectionsService.transformToUpperCase(students);
        
        Map<String, Object> response = new HashMap<>();
        response.put("📋 ArrayList", "Ordered list, allows duplicates");
        response.put("original", students);
        response.put("filtered (starts with B)", filtered);
        response.put("sorted (A-Z)", sorted);
        response.put("transformed (UPPERCASE)", uppercase);
        response.put("useCase", "Student lists, order matters");
        
        return ResponseEntity.ok(response);
    }
    
    // ==================== EXAMPLE 2: HASHSET ====================
    
    /**
     * GET /api/collections/demo/hashset
     * Demo: HashSet basics (unique elements only)
     */
    @GetMapping("/demo/hashset")
    public ResponseEntity<Map<String, Object>> demoHashSet() {
        // Create HashSet
        Set<String> emails = collectionsService.hashSetExample();
        
        // Remove duplicates from a list
        List<String> listWithDuplicates = Arrays.asList("A", "B", "A", "C", "B");
        Set<String> unique = collectionsService.removeDuplicates(listWithDuplicates);
        
        Map<String, Object> response = new HashMap<>();
        response.put("🎯 HashSet", "Unique elements only, no duplicates");
        response.put("uniqueEmails", emails);
        response.put("emailCount", emails.size() + " (duplicate ignored)");
        response.put("listWithDuplicates", listWithDuplicates);
        response.put("afterRemovingDuplicates", unique);
        response.put("useCase", "Unique emails, prevent duplicates");
        
        return ResponseEntity.ok(response);
    }
    
    // ==================== EXAMPLE 3: STACK ====================
    
    /**
     * GET /api/collections/demo/stack
     * Demo: Stack basics (LIFO - Last In, First Out)
     */
    @GetMapping("/demo/stack")
    public ResponseEntity<Map<String, Object>> demoStack() {
        // Create Stack (browser history)
        Stack<String> history = collectionsService.stackExample();
        
        String currentPage = history.peek();
        String previousPage = collectionsService.goBack(history);
        
        Map<String, Object> response = new HashMap<>();
        response.put("📚 Stack", "LIFO - Last In, First Out");
        response.put("browserHistory", Arrays.asList("google.com", "facebook.com", "youtube.com"));
        response.put("currentPage", "youtube.com");
        response.put("afterGoingBack", previousPage);
        response.put("useCase", "Browser back button, undo operations");
        
        return ResponseEntity.ok(response);
    }
    
    // ==================== EXAMPLE 4: QUEUE ====================
    
    /**
     * GET /api/collections/demo/queue
     * Demo: Queue basics (FIFO - First In, First Out)
     */
    @GetMapping("/demo/queue")
    public ResponseEntity<Map<String, Object>> demoQueue() {
        // Create Queue (print queue)
        Queue<String> queue = collectionsService.queueExample();
        
        String firstInLine = queue.peek();
        String processed = collectionsService.processNext(queue);
        String nextInLine = queue.peek();
        
        Map<String, Object> response = new HashMap<>();
        response.put("📝 Queue", "FIFO - First In, First Out");
        response.put("printQueue", Arrays.asList("Document1.pdf", "Document2.pdf", "Document3.pdf"));
        response.put("firstInLine", firstInLine);
        response.put("processed", processed);
        response.put("nextInLine", nextInLine);
        response.put("useCase", "Print queue, task processing");
        
        return ResponseEntity.ok(response);
    }
    
    // ==================== EXAMPLE 5: COMPARABLE vs COMPARATOR ====================
    
    /**
     * GET /api/collections/demo/comparable
     * Demo: Comparable (natural ordering)
     */
    @GetMapping("/demo/comparable")
    public ResponseEntity<Map<String, Object>> demoComparable() {
        List<Course> courses = collectionsService.createSampleCourses();
        List<Course> sorted = collectionsService.sortByCourseCode(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("🔤 Comparable", "Natural ordering (defined IN the class)");
        response.put("original", courses);
        response.put("sortedByCourseCode", sorted);
        response.put("howItWorks", "Course class implements Comparable interface");
        response.put("code", "Collections.sort(courses) // Uses compareTo()");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /api/collections/demo/comparator
     * Demo: Comparator (custom ordering)
     */
    @GetMapping("/demo/comparator")
    public ResponseEntity<Map<String, Object>> demoComparator() {
        List<Course> courses = collectionsService.createSampleCourses();
        
        // Sort by credits (using Comparator class)
        List<Course> byCredits = collectionsService.sortByCredits(courses);
        
        // Sort by enrollment (using lambda)
        List<Course> byEnrollment = collectionsService.sortByEnrollment(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("⚙️ Comparator", "Custom ordering (defined OUTSIDE the class)");
        response.put("original", courses);
        response.put("sortedByCredits", byCredits);
        response.put("sortedByEnrollment", byEnrollment);
        response.put("advantage", "Multiple ways to sort the same objects");
        response.put("code", "sorted.sort((c1, c2) -> ...)");
        
        return ResponseEntity.ok(response);
    }
    
    // ==================== EXAMPLE 6: FILTER & TRANSFORM ====================
    
    /**
     * GET /api/collections/demo/filter-transform
     * Demo: Filter and transform collections
     */
    @GetMapping("/demo/filter-transform")
    public ResponseEntity<Map<String, Object>> demoFilterTransform() {
        List<Course> courses = collectionsService.createSampleCourses();
        
        // Filter: Get popular courses (>100 students)
        List<Course> popular = collectionsService.filterPopularCourses(courses);
        
        // Transform: Extract course names only
        List<String> courseNames = collectionsService.getCourseNames(courses);
        
        Map<String, Object> response = new HashMap<>();
        response.put("🔍 Filter & Transform", "Process collections with streams");
        response.put("allCourses", courses);
        response.put("filtered (>100 students)", popular);
        response.put("transformed (names only)", courseNames);
        response.put("code", "stream().filter(...).map(...).collect()");
        
        return ResponseEntity.ok(response);
    }
    
    // ==================== ALL EXAMPLES IN ONE ====================
    
    /**
     * GET /api/collections/demo/all
     * Run all examples at once!
     */
    @GetMapping("/demo/all")
    public ResponseEntity<Map<String, Object>> demoAll() {
        Map<String, Object> response = new LinkedHashMap<>();
        
        // 1. ArrayList
        List<String> students = collectionsService.arrayListExample();
        response.put("1️⃣ ArrayList", Map.of(
            "description", "Ordered, allows duplicates",
            "example", students,
            "filtered", collectionsService.filterStudents(students)
        ));
        
        // 2. HashSet
        Set<String> emails = collectionsService.hashSetExample();
        response.put("2️⃣ HashSet", Map.of(
            "description", "Unique elements only",
            "example", emails,
            "size", emails.size() + " (duplicate ignored)"
        ));
        
        // 3. Stack
        Stack<String> history = collectionsService.stackExample();
        response.put("3️⃣ Stack", Map.of(
            "description", "LIFO - Last In, First Out",
            "example", new ArrayList<>(history),
            "top", history.peek()
        ));
        
        // 4. Queue
        Queue<String> queue = collectionsService.queueExample();
        response.put("4️⃣ Queue", Map.of(
            "description", "FIFO - First In, First Out",
            "example", new ArrayList<>(queue),
            "first", queue.peek()
        ));
        
        // 5. Comparable
        List<Course> courses = collectionsService.createSampleCourses();
        response.put("5️⃣ Comparable", Map.of(
            "description", "Natural ordering (IN the class)",
            "sorted", collectionsService.sortByCourseCode(courses)
        ));
        
        // 6. Comparator
        response.put("6️⃣ Comparator", Map.of(
            "description", "Custom ordering (OUTSIDE the class)",
            "byCredits", collectionsService.sortByCredits(courses),
            "byEnrollment", collectionsService.sortByEnrollment(courses)
        ));
        
        response.put("✅ Summary", "All 6 collection examples completed!");
        
        return ResponseEntity.ok(response);
    }
}
