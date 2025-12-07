# Week 4: Java Collections Framework - Complete Guide

## Overview
This document provides comprehensive examples and explanations for Java Collections, demonstrating practical backend application use cases for ArrayList, HashSet, Stack, Queue, and the differences between Comparable and Comparator.

---

## Table of Contents
1. [Collections Overview](#collections-overview)
2. [ArrayList Examples](#arraylist-examples)
3. [HashSet Examples](#hashset-examples)
4. [Stack Examples](#stack-examples)
5. [Queue Examples](#queue-examples)
6. [Comparable vs Comparator](#comparable-vs-comparator)
7. [API Endpoints](#api-endpoints)
8. [Practice Exercises](#practice-exercises)

---

## Collections Overview

### Why Collections Matter in Backend Apps

| Collection | Order | Duplicates | Use Case |
|------------|-------|------------|----------|
| **ArrayList** | Ordered (indexed) | Yes | Ordered lists, frequent access by index |
| **HashSet** | No order | No | Unique elements, fast lookup |
| **Stack** | LIFO | Yes | Undo operations, navigation history |
| **Queue** | FIFO | Yes | Task scheduling, request processing |

---

## ArrayList Examples

### Characteristics
- **Ordered**: Maintains insertion order
- **Indexed**: O(1) access by index
- **Duplicates**: Allowed
- **Use Cases**: Student lists, course history, ordered data

### Code Example
```java
List<String> students = new ArrayList<>();
students.add("Alice");
students.add("Bob");
students.add("Charlie");
students.add("Bob"); // Duplicate allowed

// Filter
List<String> filtered = students.stream()
    .filter(name -> name.startsWith("B"))
    .collect(Collectors.toList());

// Sort
Collections.sort(students); // Alphabetical order

// Transform
List<String> uppercase = students.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

### API Endpoint
```
GET http://localhost:8080/api/collections/arraylist/demo
POST http://localhost:8080/api/collections/arraylist/filter
POST http://localhost:8080/api/collections/arraylist/sort
POST http://localhost:8080/api/collections/arraylist/transform
```

---

## HashSet Examples

### Characteristics
- **Unordered**: No guaranteed order
- **Unique**: No duplicates
- **Fast Lookup**: O(1) contains() operation
- **Use Cases**: Unique emails, preventing duplicate enrollments

### Code Example
```java
Set<String> emails = new HashSet<>();
emails.add("alice@university.edu");
emails.add("bob@university.edu");
emails.add("alice@university.edu"); // Ignored (duplicate)

System.out.println(emails.size()); // 2

// Check existence (O(1) time)
boolean exists = emails.contains("alice@university.edu"); // true

// Remove duplicates from list
List<String> listWithDuplicates = Arrays.asList("A", "B", "A", "C");
Set<String> unique = new HashSet<>(listWithDuplicates); // {A, B, C}

// Intersection
Set<String> set1 = new HashSet<>(Arrays.asList("A", "B", "C"));
Set<String> set2 = new HashSet<>(Arrays.asList("B", "C", "D"));
set1.retainAll(set2); // {B, C}
```

### API Endpoint
```
GET http://localhost:8080/api/collections/hashset/demo
POST http://localhost:8080/api/collections/hashset/remove-duplicates
GET http://localhost:8080/api/collections/hashset/check-email?email=alice@university.edu
```

---

## Stack Examples

### Characteristics
- **LIFO**: Last In, First Out
- **Use Cases**: Undo operations, browser history, expression evaluation

### Code Example
```java
Stack<String> history = new Stack<>();

// Navigate pages
history.push("Home");
history.push("Courses");
history.push("Profile");

// Back button
String current = history.pop(); // "Profile"
String previous = history.peek(); // "Courses"

// Check balanced parentheses
public boolean isBalanced(String expr) {
    Stack<Character> stack = new Stack<>();
    for (char ch : expr.toCharArray()) {
        if (ch == '(') stack.push(ch);
        else if (ch == ')') {
            if (stack.isEmpty()) return false;
            stack.pop();
        }
    }
    return stack.isEmpty();
}
```

### API Endpoint
```
GET http://localhost:8080/api/collections/stack/demo
GET http://localhost:8080/api/collections/stack/navigate-back
GET http://localhost:8080/api/collections/stack/check-balanced?expression=(())
```

---

## Queue Examples

### Characteristics
- **FIFO**: First In, First Out
- **Use Cases**: Task scheduling, request processing, message queues

### Code Example
```java
Queue<String> registrationQueue = new LinkedList<>();

// Add students
registrationQueue.offer("Alice");
registrationQueue.offer("Bob");
registrationQueue.offer("Charlie");

// Process next
String next = registrationQueue.poll(); // "Alice" (removed)
String peek = registrationQueue.peek(); // "Bob" (not removed)

// PriorityQueue (elements ordered by priority)
PriorityQueue<Integer> grades = new PriorityQueue<>(Collections.reverseOrder());
grades.offer(85);
grades.offer(95);
grades.offer(78);

System.out.println(grades.peek()); // 95 (highest priority)
```

### API Endpoint
```
GET http://localhost:8080/api/collections/queue/demo
GET http://localhost:8080/api/collections/queue/process-next
GET http://localhost:8080/api/collections/priority-queue/demo
```

---

## Comparable vs Comparator

### Key Differences

| Aspect | Comparable | Comparator |
|--------|-----------|------------|
| **Location** | Inside the class | External class/lambda |
| **Method** | `compareTo(T o)` | `compare(T o1, T o2)` |
| **Purpose** | Natural ordering | Custom ordering |
| **Count** | Single implementation | Multiple implementations |
| **Modification** | Requires source code access | No class modification needed |

---

### Comparable Example (Natural Ordering)

**Course.java** - Implements Comparable
```java
public class Course implements Comparable<Course> {
    private String courseCode;
    private String courseName;
    private int credits;
    
    @Override
    public int compareTo(Course other) {
        // Natural ordering by course code
        return this.courseCode.compareTo(other.courseCode);
    }
}

// Usage
List<Course> courses = createSampleCourses();
Collections.sort(courses); // Sorts by course code (CS101, CS150, CS202...)
```

---

### Comparator Examples (Custom Ordering)

#### 1. Traditional Class-based Comparator
```java
public class ByCreditsDescending implements Comparator<Course> {
    @Override
    public int compare(Course c1, Course c2) {
        return Integer.compare(c2.getCredits(), c1.getCredits());
    }
}

// Usage
courses.sort(new ByCreditsDescending());
```

#### 2. Lambda Comparator (Modern Java)
```java
// Sort by credits descending
courses.sort((c1, c2) -> Integer.compare(c2.getCredits(), c1.getCredits()));

// Sort by instructor
courses.sort(Comparator.comparing(Course::getInstructor));

// Sort by start date
courses.sort(Comparator.comparing(Course::getStartDate));
```

#### 3. Compound Comparator (Multiple Criteria)
```java
// Sort by credits (desc), then by enrollment (desc)
Comparator<Course> compound = Comparator
    .comparing(Course::getCredits).reversed()
    .thenComparing(Course::getEnrolledStudents, Comparator.reverseOrder());

courses.sort(compound);
```

---

### Practical Sorting Examples

```java
// 1. Natural ordering (Comparable)
Collections.sort(courses); // By course code

// 2. By credits (Comparator)
courses.sort(new ByCreditsDescending());

// 3. By enrollment (Lambda)
courses.sort(CourseComparators.BY_ENROLLED_DESC);

// 4. By start date
courses.sort(CourseComparators.BY_START_DATE);

// 5. By instructor name
courses.sort(Comparator.comparing(Course::getInstructor));

// 6. Complex: 4-credit courses sorted by enrollment
List<Course> result = courses.stream()
    .filter(c -> c.getCredits() == 4)
    .sorted(Comparator.comparingInt(Course::getEnrolledStudents)
            .reversed()
            .thenComparing(Course::getCourseCode))
    .collect(Collectors.toList());
```

---

## API Endpoints

### Course Collections Endpoints

#### Get All Courses
```
GET http://localhost:8080/api/collections/courses/all
```

#### Sort Using Comparable (Natural Ordering)
```
GET http://localhost:8080/api/collections/courses/sort-natural
```
**Returns**: Courses sorted by course code (CS101, CS150, CS202, CS305, CS401)

#### Sort Using Comparator - By Credits
```
GET http://localhost:8080/api/collections/courses/sort-by-credits
```
**Returns**: Courses sorted by credits descending (4, 4, 3, 3, 3)

#### Sort Using Comparator - By Enrollment
```
GET http://localhost:8080/api/collections/courses/sort-by-enrollment
```
**Returns**: Courses sorted by enrolled students descending (120, 110, 95, 85, 70)

#### Sort Using Lambda - By Instructor
```
GET http://localhost:8080/api/collections/courses/sort-by-instructor
```
**Returns**: Courses sorted by instructor name

#### Filter Operations
```
GET http://localhost:8080/api/collections/courses/filter-popular
GET http://localhost:8080/api/collections/courses/filter-four-credits
```

#### Advanced Operations
```
GET http://localhost:8080/api/collections/courses/unique-instructors
GET http://localhost:8080/api/collections/courses/statistics
GET http://localhost:8080/api/collections/courses/complex-filter
```

---

## Practice Exercises

### Exercise 1: ArrayList Operations
```java
// Create a list of student names
// Filter students whose names have more than 5 characters
// Sort them alphabetically
// Convert all to lowercase
```

### Exercise 2: HashSet Operations
```java
// Create two sets of course codes
// Find common courses (intersection)
// Find all unique courses (union)
// Find courses in set1 but not in set2 (difference)
```

### Exercise 3: Stack Application
```java
// Implement a "Redo" feature (opposite of Undo)
// Use two stacks: undoStack and redoStack
```

### Exercise 4: Queue Application
```java
// Simulate a help desk ticketing system
// Process tickets in order of arrival (FIFO)
// Add priority tickets that jump to front
```

### Exercise 5: Comparable Implementation
```java
// Create a Book class implementing Comparable
// Sort by ISBN (natural ordering)
```

### Exercise 6: Multiple Comparators
```java
// Create comparators for Book class:
// - By title (alphabetically)
// - By publication date (newest first)
// - By price (lowest first)
// - By author then title (compound)
```

### Exercise 7: Complex Stream Operations
```java
// Given a list of courses:
// 1. Filter courses starting after Jan 20, 2025
// 2. With enrollment > 80
// 3. Sort by credits descending, then by course name
// 4. Extract only course codes
```

---

## Testing the Examples

### 1. Start the Backend
```bash
cd backend
mvn spring-boot:run
```

### 2. Test Endpoints with curl or Postman

**ArrayList Demo:**
```bash
curl http://localhost:8080/api/collections/arraylist/demo
```

**HashSet Demo:**
```bash
curl http://localhost:8080/api/collections/hashset/demo
```

**Course Sorting (Comparable):**
```bash
curl http://localhost:8080/api/collections/courses/sort-natural
```

**Course Sorting (Comparator):**
```bash
curl http://localhost:8080/api/collections/courses/sort-by-enrollment
```

**Complex Filter:**
```bash
curl http://localhost:8080/api/collections/courses/complex-filter
```

---

## Key Takeaways

### When to Use Each Collection

1. **ArrayList**
   - Need indexed access
   - Ordered data matters
   - Frequent get/set operations
   - Example: Student roster, course history

2. **HashSet**
   - Need unique elements
   - Fast lookup required
   - Order doesn't matter
   - Example: Unique emails, course codes

3. **Stack**
   - LIFO behavior needed
   - Example: Undo/redo, navigation history, expression parsing

4. **Queue**
   - FIFO behavior needed
   - Example: Request processing, task scheduling, print queue

### Comparable vs Comparator Decision

**Use Comparable when:**
- There's one obvious natural ordering
- You control the class source code
- Example: Students sorted by ID

**Use Comparator when:**
- Need multiple sorting options
- Don't control the class source code
- Want flexible, external sorting logic
- Example: Courses sorted by credits, enrollment, or date

### Modern Java Best Practices

1. **Use Lambda Expressions** for Comparators
   ```java
   // Instead of
   courses.sort(new ByCreditsDescending());
   
   // Use
   courses.sort((c1, c2) -> Integer.compare(c2.getCredits(), c1.getCredits()));
   ```

2. **Use Stream API** for filtering/transforming
   ```java
   List<Course> popular = courses.stream()
       .filter(c -> c.getEnrolledStudents() > 90)
       .collect(Collectors.toList());
   ```

3. **Use Method References** when possible
   ```java
   courses.sort(Comparator.comparing(Course::getCredits));
   ```

---

## File Structure

```
backend/src/main/java/com/example/demo/
├── model/
│   └── Course.java (implements Comparable)
├── comparator/
│   └── CourseComparators.java (multiple Comparator implementations)
├── service/
│   └── CollectionsExamplesService.java (all collection operations)
└── controller/
    └── CollectionsController.java (REST API endpoints)
```

---

## Additional Resources

- [Java Collections Framework Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/package-summary.html)
- [Comparable Interface](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Comparable.html)
- [Comparator Interface](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Comparator.html)
- [Stream API Guide](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/package-summary.html)

---

## Summary

This Week 4 example demonstrates:
✅ All major Java Collections (ArrayList, HashSet, Stack, Queue)  
✅ Real backend use cases for each collection type  
✅ Comparable vs Comparator with practical examples  
✅ Filtering, sorting, and transforming collections  
✅ Modern Java 8+ features (Streams, Lambdas, Method References)  
✅ REST API endpoints to test each concept  
✅ Practice exercises for hands-on learning  

Happy coding! 🚀
