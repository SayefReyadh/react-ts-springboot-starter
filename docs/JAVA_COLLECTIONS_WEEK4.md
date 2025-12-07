# 📚 Week 4: Java Collections Framework - Basic Examples

## 🎯 Overview
Simple examples demonstrating Java Collections for backend applications, covering ArrayList, HashSet, Stack, Queue, and Comparable vs Comparator.

---

## 📋 Table of Contents
1. [Collections Quick Reference](#collections-quick-reference)
2. [6 Core Examples](#6-core-examples)
3. [API Endpoints](#api-endpoints)
4. [Testing Guide](#testing-guide)
5. [Practice Exercises](#practice-exercises)

---

## 🔍 Collections Quick Reference

| Collection | Order | Duplicates | Use Case |
|------------|-------|------------|----------|
| **ArrayList** 📝 | Ordered (indexed) | Yes | Student lists, ordered data |
| **HashSet** 🔖 | No order | No | Unique emails, fast lookup |
| **Stack** 📚 | LIFO | Yes | Browser history, undo operations |
| **Queue** 🎫 | FIFO | Yes | Request processing, task scheduling |

---

## 6 Core Examples

### Example 1: ArrayList - Basic Operations
**Purpose**: Demonstrate ordered list with duplicates

```java
List<String> students = new ArrayList<>();
students.add("Alice");
students.add("Bob");
students.add("Charlie");
students.add("Bob"); // Duplicate allowed

System.out.println("Students: " + students);
System.out.println("Size: " + students.size());
```

**Output**:
```
Students: [Alice, Bob, Charlie, Bob]
Size: 4
```

---

### Example 2: Filter, Sort, Transform ArrayList
**Purpose**: Stream operations on ArrayList

```java
// Filter: Students starting with 'B'
List<String> filtered = students.stream()
    .filter(name -> name.startsWith("B"))
    .collect(Collectors.toList());

// Sort: Alphabetically
List<String> sorted = students.stream()
    .sorted()
    .collect(Collectors.toList());

// Transform: To uppercase
List<String> uppercase = students.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());
```

**Output**:
```
Filtered: [Bob, Bob]
Sorted: [Alice, Bob, Bob, Charlie]
Uppercase: [ALICE, BOB, CHARLIE, BOB]
```

---

### Example 3: HashSet - Unique Elements
**Purpose**: Demonstrate unique collection with O(1) lookup

```java
Set<String> emails = new HashSet<>();
emails.add("alice@university.edu");
emails.add("bob@university.edu");
emails.add("alice@university.edu"); // Duplicate ignored

System.out.println("Unique emails: " + emails);
System.out.println("Size: " + emails.size());
```

**Output**:
```
Unique emails: [alice@university.edu, bob@university.edu]
Size: 2
```

---

### Example 4: Stack - LIFO (Browser History)
**Purpose**: Last In, First Out behavior

```java
Stack<String> history = new Stack<>();
history.push("Home");
history.push("Courses");
history.push("Profile");

System.out.println("Current page: " + history.peek());
String previous = history.pop(); // Go back
System.out.println("After back: " + history.peek());
```

**Output**:
```
Current page: Profile
After back: Courses
```

---

### Example 5: Queue - FIFO (Registration Queue)
**Purpose**: First In, First Out behavior

```java
Queue<String> queue = new LinkedList<>();
queue.offer("Alice");
queue.offer("Bob");
queue.offer("Charlie");

System.out.println("Next student: " + queue.peek());
String processed = queue.poll(); // Process first
System.out.println("Processed: " + processed);
System.out.println("Next: " + queue.peek());
```

**Output**:
```
Next student: Alice
Processed: Alice
Next: Bob
```

---

### Example 6: Comparable vs Comparator
**Purpose**: Natural vs Custom sorting

#### Comparable (Natural Ordering)
```java
public class Course implements Comparable<Course> {
    private String courseCode;
    
    @Override
    public int compareTo(Course other) {
        return this.courseCode.compareTo(other.courseCode);
    }
}

// Usage
Collections.sort(courses); // Sorts by course code
```

#### Comparator (Custom Ordering)
```java
// Sort by credits (descending)
courses.sort(Comparator.comparing(Course::getCredits).reversed());

// Sort by enrollment
courses.sort(Comparator.comparing(Course::getEnrolledStudents).reversed());

// Filter and sort
List<Course> popular = courses.stream()
    .filter(c -> c.getEnrolledStudents() > 90)
    .sorted(Comparator.comparing(Course::getCourseCode))
    .collect(Collectors.toList());
```

**Key Difference**:
| Aspect | Comparable | Comparator |
|--------|-----------|------------|
| Location | Inside class | External |
| Method | `compareTo()` | `compare()` |
| Count | One | Many |
| Purpose | Natural order | Custom order |

---

## 🌐 API Endpoints

### Test All Examples
```
GET http://localhost:8080/api/collections/demo/all
```

### Individual Examples
```
GET http://localhost:8080/api/collections/demo/arraylist
GET http://localhost:8080/api/collections/demo/hashset
GET http://localhost:8080/api/collections/demo/stack
GET http://localhost:8080/api/collections/demo/queue
GET http://localhost:8080/api/collections/demo/comparable
GET http://localhost:8080/api/collections/demo/comparator
GET http://localhost:8080/api/collections/demo/filter-transform
```

---

## 🧪 Testing Guide

### 1. Start Backend
```bash
cd backend
mvn spring-boot:run
```

### 2. Test All Examples
```bash
curl http://localhost:8080/api/collections/demo/all
```

### 3. Test Individual Examples
```bash
# ArrayList
curl http://localhost:8080/api/collections/demo/arraylist

# HashSet
curl http://localhost:8080/api/collections/demo/hashset

# Stack
curl http://localhost:8080/api/collections/demo/stack

# Queue
curl http://localhost:8080/api/collections/demo/queue

# Comparable (Course sorting by code)
curl http://localhost:8080/api/collections/demo/comparable

# Comparator (Course sorting by credits/enrollment)
curl http://localhost:8080/api/collections/demo/comparator
```

---

## ✏️ Practice Exercises

### Exercise 1: ArrayList Filtering
Create a list of 10 student names and:
- Filter names longer than 5 characters
- Sort them alphabetically
- Convert to uppercase

### Exercise 2: HashSet Duplicates
Given: `["apple", "banana", "apple", "cherry", "banana"]`
- Remove duplicates using HashSet
- Count how many duplicates were removed

### Exercise 3: Stack Navigation
Implement a simple browser history:
- Push 5 pages
- Go back 2 times
- Go forward 1 time (hint: use second stack)

### Exercise 4: Queue Processing
Simulate a print queue:
- Add 5 print jobs
- Process them one by one
- Show remaining queue after each process

### Exercise 5: Comparable
Create a `Student` class with ID and name:
- Implement `Comparable<Student>` to sort by ID
- Test with 5 students

### Exercise 6: Comparator
Create comparators for `Student`:
- By name (alphabetical)
- By ID (descending)
- Test both sorting methods

---

## 📁 File Structure

```
backend/src/main/java/com/example/demo/
├── model/
│   └── Course.java (implements Comparable)
├── comparator/
│   └── CourseComparators.java (Comparator examples)
├── service/
│   └── CollectionsExamplesService.java (6 core examples)
└── controller/
    └── CollectionsController.java (REST endpoints)
```

---

## 🎓 Key Takeaways

### When to Use Each Collection

**ArrayList** 📝
- Need ordered, indexed access
- Duplicates allowed
- Example: Student roster

**HashSet** 🔖
- Need unique elements only
- Fast O(1) lookup
- Example: Email validation

**Stack** 📚
- LIFO (Last In, First Out)
- Example: Undo/redo, navigation history

**Queue** 🎫
- FIFO (First In, First Out)
- Example: Request processing

### Comparable vs Comparator

**Use Comparable** when:
- One natural ordering exists
- You control the class

**Use Comparator** when:
- Multiple sorting options needed
- External sorting logic preferred

---

## 📚 Summary

This guide covers:
✅ **6 Core Examples** - ArrayList, HashSet, Stack, Queue, Comparable, Comparator  
✅ **Simple REST API** - Easy testing with `/demo` endpoints  
✅ **Practical Use Cases** - Real backend scenarios  
✅ **Stream Operations** - Filter, sort, transform  
✅ **Practice Exercises** - Hands-on learning  

Happy coding! 🚀
