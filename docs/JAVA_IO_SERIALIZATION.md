# Java I/O, Serialization & Streams - Complete Guide

## Overview
This guide covers Java I/O fundamentals, including:
- **Byte Streams** vs **Character Streams**
- **Serialization** and **Deserialization**
- **Buffered I/O** for better performance
- **Modern NIO** (Java 7+)
- Practical backend use cases

---

## Table of Contents
1. [Byte Streams vs Character Streams](#byte-streams-vs-character-streams)
2. [Byte Streams Examples](#byte-streams-examples)
3. [Character Streams Examples](#character-streams-examples)
4. [Serialization](#serialization)
5. [Best Practices](#best-practices)
6. [API Endpoints](#api-endpoints)
7. [Practice Exercises](#practice-exercises)

---

## Byte Streams vs Character Streams

### Key Differences

| Aspect | Byte Streams | Character Streams |
|--------|--------------|-------------------|
| **Data Type** | 8-bit bytes | 16-bit Unicode characters |
| **Base Classes** | `InputStream`, `OutputStream` | `Reader`, `Writer` |
| **Use Cases** | Binary files, images, audio, video | Text files, CSV, JSON, XML |
| **Encoding** | Manual (raw bytes) | Automatic (handles encoding) |
| **Examples** | `FileInputStream`, `FileOutputStream` | `FileReader`, `FileWriter` |
| **Buffered** | `BufferedInputStream`, `BufferedOutputStream` | `BufferedReader`, `BufferedWriter` |

### When to Use Each

**Use Byte Streams when:**
- Working with binary data (images, videos, audio)
- Need raw byte manipulation
- Serializing objects
- Network communication (sockets)

**Use Character Streams when:**
- Working with text files
- Need Unicode support
- Reading/writing CSV, JSON, XML
- Handling international characters
- Reading configuration files

---

## Byte Streams Examples

### 1. Basic Byte Stream (FileInputStream / FileOutputStream)

```java
// Write bytes to file
try (FileOutputStream fos = new FileOutputStream("data.bin")) {
    byte[] data = "Hello World".getBytes();
    fos.write(data);
}

// Read bytes from file
try (FileInputStream fis = new FileInputStream("data.bin")) {
    byte[] data = fis.readAllBytes(); // Java 9+
    String content = new String(data);
    System.out.println(content);
}
```

**Characteristics:**
- Works with raw bytes
- No buffering (slower for large files)
- Suitable for small files or when you need precise byte control

### 2. Buffered Byte Stream (More Efficient)

```java
// Write with buffer
try (BufferedOutputStream bos = new BufferedOutputStream(
        new FileOutputStream("data.bin"))) {
    byte[] data = largeDataArray;
    bos.write(data);
    bos.flush(); // Force write buffer to disk
}

// Read with buffer
try (BufferedInputStream bis = new BufferedInputStream(
        new FileInputStream("data.bin"))) {
    byte[] buffer = new byte[1024]; // 1KB buffer
    int bytesRead;
    while ((bytesRead = bis.read(buffer)) != -1) {
        // Process buffer
    }
}
```

**Advantages:**
- Uses internal buffer (default 8KB)
- Reduces number of I/O operations
- Much faster for large files
- **Always prefer buffered streams in production**

### 3. Copying Files with Byte Streams

```java
public void copyFile(String source, String dest) throws IOException {
    try (FileInputStream fis = new FileInputStream(source);
         FileOutputStream fos = new FileOutputStream(dest)) {
        
        byte[] buffer = new byte[1024];
        int bytesRead;
        
        while ((bytesRead = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
    }
}
```

---

## Character Streams Examples

### 1. Basic Character Stream (FileReader / FileWriter)

```java
// Write characters to file
try (FileWriter writer = new FileWriter("text.txt")) {
    String text = "Hello World! 你好 مرحبا";
    writer.write(text);
    // Handles Unicode automatically!
}

// Read characters from file
try (FileReader reader = new FileReader("text.txt")) {
    int character;
    StringBuilder content = new StringBuilder();
    
    while ((character = reader.read()) != -1) {
        content.append((char) character);
    }
    
    System.out.println(content.toString());
}
```

**Characteristics:**
- Handles character encoding automatically
- Supports Unicode (international characters)
- Works character by character (not efficient for large files)

### 2. BufferedReader / BufferedWriter (RECOMMENDED)

```java
// Write multiple lines
try (BufferedWriter writer = new BufferedWriter(
        new FileWriter("lines.txt"))) {
    writer.write("Line 1");
    writer.newLine(); // Platform-independent line separator
    writer.write("Line 2");
    writer.newLine();
    writer.write("Line 3");
}

// Read line by line (most common pattern)
try (BufferedReader reader = new BufferedReader(
        new FileReader("lines.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
}
```

**Advantages:**
- Most efficient way to read/write text
- `readLine()` method for easy line-by-line reading
- `newLine()` method for platform-independent line breaks
- **This is the standard way to process text files**

### 3. Append Mode

```java
// Append to existing file (not overwrite)
try (FileWriter writer = new FileWriter("log.txt", true)) { // true = append
    writer.write("New log entry\n");
}
```

---

## Serialization

### What is Serialization?

**Serialization**: Converting an object into a byte stream for:
- **Storage**: Save object state to disk
- **Transmission**: Send objects over network
- **Caching**: Store objects in memory or Redis

**Deserialization**: Converting byte stream back into an object

### Requirements

1. Class must implement `Serializable` interface
2. All instance fields must be serializable (or marked `transient`)
3. Class should have `serialVersionUID`

### Example Class

```java
public class StudentRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Serialized fields
    private Long id;
    private String name;
    private String email;
    private int age;
    private double gpa;
    
    // NOT serialized (transient = skip during serialization)
    private transient String password; // Security: don't save password
    private transient int loginAttempts; // Session data, don't persist
    
    // NOT serialized (static = belongs to class, not instance)
    private static int totalStudents;
}
```

### Serialization Example

```java
// Serialize object to file
public void serializeStudent(StudentRecord student) throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream("student.ser"))) {
        oos.writeObject(student);
    }
}

// Deserialize object from file
public StudentRecord deserializeStudent() throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream("student.ser"))) {
        return (StudentRecord) ois.readObject();
    }
}
```

### Important Notes

1. **transient fields**: NOT saved during serialization
   ```java
   private transient String password; // Will be null after deserialization
   ```

2. **static fields**: NOT saved (belong to class, not instance)
   ```java
   private static int count; // Not serialized
   ```

3. **serialVersionUID**: Version control for serialization
   ```java
   private static final long serialVersionUID = 1L;
   ```
   - If class structure changes, deserialization may fail
   - Explicit `serialVersionUID` helps manage compatibility

4. **Collections**: Standard collections (ArrayList, HashMap, etc.) are serializable
   ```java
   private List<String> names; // OK - ArrayList is Serializable
   ```

### Serializing Collections

```java
// Serialize a list
List<StudentRecord> students = Arrays.asList(student1, student2);
try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream("students.ser"))) {
    oos.writeObject(students);
}

// Deserialize a list
try (ObjectInputStream ois = new ObjectInputStream(
        new FileInputStream("students.ser"))) {
    List<StudentRecord> students = (List<StudentRecord>) ois.readObject();
}
```

---

## Best Practices

### 1. Always Use Try-With-Resources

```java
// ✅ GOOD - Auto-closes streams
try (FileWriter writer = new FileWriter("file.txt")) {
    writer.write("content");
}

// ❌ BAD - Manual close, may leak resources
FileWriter writer = new FileWriter("file.txt");
writer.write("content");
writer.close(); // May not execute if exception occurs
```

### 2. Prefer Buffered Streams

```java
// ✅ GOOD - Buffered for performance
try (BufferedWriter writer = new BufferedWriter(
        new FileWriter("file.txt"))) {
    // Fast writes
}

// ❌ LESS EFFICIENT - No buffering
try (FileWriter writer = new FileWriter("file.txt")) {
    // Slow for large files
}
```

### 3. Use Character Streams for Text

```java
// ✅ GOOD - Character stream for text
try (BufferedReader reader = new BufferedReader(
        new FileReader("text.txt"))) {
    String line = reader.readLine();
}

// ❌ LESS IDEAL - Byte stream requires manual encoding
try (FileInputStream fis = new FileInputStream("text.txt")) {
    byte[] bytes = fis.readAllBytes();
    String text = new String(bytes, StandardCharsets.UTF_8); // Manual
}
```

### 4. Use NIO for Simple Operations (Java 7+)

```java
// ✅ MODERN - Simple and clean
String content = Files.readString(Path.of("file.txt"));
Files.writeString(Path.of("file.txt"), "content");

List<String> lines = Files.readAllLines(Path.of("file.txt"));
Files.write(Path.of("file.txt"), lines);
```

### 5. Handle Exceptions Properly

```java
try {
    // I/O operations
} catch (FileNotFoundException e) {
    // File doesn't exist
} catch (IOException e) {
    // Other I/O errors
}
```

---

## Stream Hierarchy

### Byte Stream Classes

```
InputStream (abstract)
├── FileInputStream - Read bytes from file
├── BufferedInputStream - Buffered byte reading
├── ByteArrayInputStream - Read from byte array
└── ObjectInputStream - Deserialize objects

OutputStream (abstract)
├── FileOutputStream - Write bytes to file
├── BufferedOutputStream - Buffered byte writing
├── ByteArrayOutputStream - Write to byte array
└── ObjectOutputStream - Serialize objects
```

### Character Stream Classes

```
Reader (abstract)
├── FileReader - Read characters from file
├── BufferedReader - Buffered character reading (readLine())
├── StringReader - Read from String
└── InputStreamReader - Byte-to-character bridge

Writer (abstract)
├── FileWriter - Write characters to file
├── BufferedWriter - Buffered character writing (newLine())
├── StringWriter - Write to String
└── OutputStreamWriter - Character-to-byte bridge
```

---

## API Endpoints

### Byte Stream Operations

```bash
# Write using byte stream
POST http://localhost:8080/api/io/byte-stream/write
Body: {"filename": "data.bin", "content": "Binary data"}

# Read using byte stream
GET http://localhost:8080/api/io/byte-stream/read?filename=data.bin

# Write using buffered byte stream (more efficient)
POST http://localhost:8080/api/io/buffered-byte-stream/write
Body: {"filename": "buffered.bin", "content": "Buffered data"}

# Copy file
POST http://localhost:8080/api/io/byte-stream/copy
Body: {"sourceFile": "source.bin", "destFile": "copy.bin"}
```

### Character Stream Operations

```bash
# Write using character stream
POST http://localhost:8080/api/io/character-stream/write
Body: {"filename": "text.txt", "content": "Hello 你好 مرحبا"}

# Read using character stream
GET http://localhost:8080/api/io/character-stream/read?filename=text.txt

# Write multiple lines (BufferedWriter)
POST http://localhost:8080/api/io/buffered-writer/write
Body: {"filename": "lines.txt", "lines": ["Line 1", "Line 2", "Line 3"]}

# Read multiple lines (BufferedReader)
GET http://localhost:8080/api/io/buffered-reader/read?filename=lines.txt

# Append to file
POST http://localhost:8080/api/io/character-stream/append
Body: {"filename": "text.txt", "content": "Appended text"}
```

### Serialization Operations

```bash
# Serialize student object
POST http://localhost:8080/api/io/serialize/student
Body: {"id": 1, "name": "Alice", "email": "alice@edu", "age": 20, "gpa": 3.85, "password": "secret"}

# Deserialize student object
GET http://localhost:8080/api/io/deserialize/student?filename=student_1.ser

# Serialize list of students
POST http://localhost:8080/api/io/serialize/student-list
Body: [{"id": 1, "name": "Alice", ...}, {"id": 2, "name": "Bob", ...}]

# Deserialize list
GET http://localhost:8080/api/io/deserialize/student-list?filename=students.ser

# Serialize course
POST http://localhost:8080/api/io/serialize/course
Body: {"courseCode": "CS101", "courseName": "Programming", ...}

# Deserialize course
GET http://localhost:8080/api/io/deserialize/course?filename=course_CS101.ser
```

### Comparison & Demo

```bash
# Compare byte vs character streams
GET http://localhost:8080/api/io/compare/byte-vs-character

# Complete demo (creates multiple files)
GET http://localhost:8080/api/io/demo/complete
```

### File Management

```bash
# List all files
GET http://localhost:8080/api/io/files/list

# Delete file
DELETE http://localhost:8080/api/io/files/delete?filename=demo.txt

# Get file size
GET http://localhost:8080/api/io/files/size?filename=demo.txt
```

---

## Practice Exercises

### Exercise 1: Byte Stream
```
Write a program that:
1. Creates a byte array with values 0-255
2. Writes it to "bytes.bin" using FileOutputStream
3. Reads it back using FileInputStream
4. Verifies all bytes are correct
```

### Exercise 2: Character Stream
```
Write a program that:
1. Creates a text file with 10 lines
2. Uses BufferedWriter to write lines
3. Uses BufferedReader to read lines
4. Counts total words in the file
```

### Exercise 3: File Copy
```
Write an efficient file copy method that:
1. Uses buffered streams
2. Copies in 4KB chunks
3. Shows progress (bytes copied)
4. Handles large files (>100MB)
```

### Exercise 4: Serialization
```
Create a Book class with:
1. Fields: isbn, title, author, price
2. Make it Serializable
3. Mark 'price' as transient
4. Serialize 5 books to file
5. Deserialize and verify price is null
```

### Exercise 5: Log File Writer
```
Create a log writer that:
1. Uses BufferedWriter in append mode
2. Writes timestamped log entries
3. Automatically rotates file after 1000 lines
4. Uses try-with-resources properly
```

### Exercise 6: CSV Reader
```
Write a CSV reader that:
1. Uses BufferedReader
2. Reads line by line
3. Splits by comma
4. Converts to Student objects
5. Returns List<Student>
```

### Exercise 7: Serialization Comparison
```
Compare file sizes:
1. Serialize 1000 students to .ser file
2. Write same data to JSON text file
3. Write same data to CSV text file
4. Compare file sizes and performance
```

---

## Common Pitfalls & Solutions

### 1. Forgetting to Close Streams

```java
// ❌ PROBLEM - Resource leak
FileWriter writer = new FileWriter("file.txt");
writer.write("content");
// Forgot to close - resource leak!

// ✅ SOLUTION - Try-with-resources
try (FileWriter writer = new FileWriter("file.txt")) {
    writer.write("content");
} // Automatically closed
```

### 2. Not Flushing Buffered Streams

```java
// ❌ PROBLEM - Data may not be written
BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt"));
writer.write("content");
writer.close(); // Data might be lost!

// ✅ SOLUTION - Flush before close (or use try-with-resources)
try (BufferedWriter writer = new BufferedWriter(new FileWriter("file.txt"))) {
    writer.write("content");
    writer.flush(); // Ensure data is written
}
```

### 3. Using Byte Streams for Text

```java
// ❌ PROBLEM - Manual encoding handling
FileOutputStream fos = new FileOutputStream("text.txt");
fos.write("Hello 你好".getBytes(StandardCharsets.UTF_8));

// ✅ SOLUTION - Use character streams
try (FileWriter writer = new FileWriter("text.txt")) {
    writer.write("Hello 你好"); // Handles encoding automatically
}
```

### 4. Serialization Without serialVersionUID

```java
// ❌ PROBLEM - Version incompatibility
public class Student implements Serializable {
    private String name;
    // No serialVersionUID - JVM generates one
    // If class changes, deserialization fails!
}

// ✅ SOLUTION - Explicit serialVersionUID
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
}
```

### 5. Forgetting transient for Sensitive Data

```java
// ❌ PROBLEM - Password is serialized!
public class User implements Serializable {
    private String username;
    private String password; // Saved to file - security risk!
}

// ✅ SOLUTION - Mark sensitive fields transient
public class User implements Serializable {
    private String username;
    private transient String password; // NOT saved
}
```

---

## Real-World Use Cases

### 1. Byte Streams
- **Image Upload**: Reading uploaded images
- **File Download**: Sending files to clients
- **Binary Protocols**: Network communication
- **Encryption**: Reading/writing encrypted data

### 2. Character Streams
- **Log Files**: Writing application logs
- **CSV Export**: Exporting data to CSV
- **Configuration**: Reading .properties files
- **Reports**: Generating text reports

### 3. Serialization
- **Session Storage**: Saving user sessions
- **Caching**: Storing objects in Redis/Memcached
- **Messaging**: Sending objects between services
- **Persistence**: Quick object storage (not recommended for long-term)

---

## Testing the Examples

### 1. Start the Backend
```bash
cd backend
mvn spring-boot:run
```

### 2. Test with curl

**Write text file:**
```bash
curl -X POST http://localhost:8080/api/io/character-stream/write \
  -H "Content-Type: application/json" \
  -d '{"filename": "test.txt", "content": "Hello World"}'
```

**Read text file:**
```bash
curl http://localhost:8080/api/io/character-stream/read?filename=test.txt
```

**Serialize student:**
```bash
curl -X POST http://localhost:8080/api/io/serialize/student
```

**Deserialize student:**
```bash
curl http://localhost:8080/api/io/deserialize/student?filename=student_1.ser
```

**Complete demo:**
```bash
curl http://localhost:8080/api/io/demo/complete
```

---

## File Structure

```
backend/src/main/java/com/example/demo/
├── model/
│   ├── StudentRecord.java (Serializable with transient fields)
│   └── CourseData.java (Serializable with collections)
├── service/
│   └── IOExamplesService.java (All I/O operations)
└── controller/
    └── IOController.java (REST API endpoints)

data/ (auto-created)
├── *.bin (byte stream files)
├── *.txt (character stream files)
└── *.ser (serialized objects)
```

---

## Summary

### Key Takeaways

✅ **Byte Streams**: For binary data (images, audio, serialization)  
✅ **Character Streams**: For text data (CSV, JSON, logs)  
✅ **Buffered Streams**: Always prefer for better performance  
✅ **Serialization**: Easy object persistence (use `transient` for sensitive data)  
✅ **Try-with-resources**: Always close streams automatically  
✅ **Modern NIO**: Simpler API for common operations (Java 7+)  

### Decision Tree

```
Need to work with I/O?
│
├─ Binary data? (images, audio, objects)
│  └─ Use Byte Streams (FileInputStream/FileOutputStream)
│     └─ Large files? Use Buffered versions
│
├─ Text data? (CSV, JSON, logs)
│  └─ Use Character Streams (FileReader/FileWriter)
│     └─ Multiple lines? Use BufferedReader/BufferedWriter
│
└─ Save objects?
   └─ Use Serialization (ObjectInputStream/ObjectOutputStream)
      └─ Mark sensitive fields as transient
```

Happy coding! 🚀
