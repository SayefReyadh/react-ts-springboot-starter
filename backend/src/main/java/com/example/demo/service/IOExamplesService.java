package com.example.demo.service;

import com.example.demo.model.CourseData;
import com.example.demo.model.StudentRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service demonstrating Java I/O operations.
 * Covers:
 * 1. Byte Streams (FileInputStream, FileOutputStream, BufferedInputStream, BufferedOutputStream)
 * 2. Character Streams (FileReader, FileWriter, BufferedReader, BufferedWriter)
 * 3. Serialization (ObjectInputStream, ObjectOutputStream)
 * 4. Modern NIO (Files, Paths)
 */
@Service
public class IOExamplesService {
    
    private static final String DATA_DIR = "data";
    
    /**
     * Initialize data directory.
     */
    public IOExamplesService() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            System.err.println("Error creating data directory: " + e.getMessage());
        }
    }
    
    // ==================== BYTE STREAMS ====================
    
    /**
     * BYTE STREAMS: Work with raw binary data (8-bit bytes).
     * Use cases: Images, audio, video, binary files, serialization.
     * 
     * Classes: FileInputStream, FileOutputStream, BufferedInputStream, BufferedOutputStream
     */
    
    /**
     * Write binary data using FileOutputStream (Byte Stream).
     * Writes data byte by byte - NOT efficient for large files.
     */
    public String writeUsingByteStream(String filename, byte[] data) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            fos.write(data);
            return "Written " + data.length + " bytes to " + filepath + " using Byte Stream";
        }
    }
    
    /**
     * Read binary data using FileInputStream (Byte Stream).
     */
    public byte[] readUsingByteStream(String filename) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (FileInputStream fis = new FileInputStream(filepath)) {
            return fis.readAllBytes(); // Java 9+
        }
    }
    
    /**
     * Write binary data using BufferedOutputStream (Buffered Byte Stream).
     * More efficient - uses internal buffer to reduce I/O operations.
     */
    public String writeUsingBufferedByteStream(String filename, byte[] data) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(filepath))) {
            bos.write(data);
            bos.flush(); // Ensure buffer is written to file
            return "Written " + data.length + " bytes to " + filepath + " using Buffered Byte Stream";
        }
    }
    
    /**
     * Read binary data using BufferedInputStream (Buffered Byte Stream).
     */
    public byte[] readUsingBufferedByteStream(String filename) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream(filepath))) {
            return bis.readAllBytes();
        }
    }
    
    /**
     * Copy file using Byte Stream (demonstrates reading/writing chunks).
     */
    public String copyFileUsingByteStream(String sourceFile, String destFile) throws IOException {
        String sourcePath = DATA_DIR + "/" + sourceFile;
        String destPath = DATA_DIR + "/" + destFile;
        
        try (FileInputStream fis = new FileInputStream(sourcePath);
             FileOutputStream fos = new FileOutputStream(destPath)) {
            
            byte[] buffer = new byte[1024]; // 1KB buffer
            int bytesRead;
            int totalBytes = 0;
            
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
                totalBytes += bytesRead;
            }
            
            return "Copied " + totalBytes + " bytes from " + sourceFile + " to " + destFile;
        }
    }
    
    // ==================== CHARACTER STREAMS ====================
    
    /**
     * CHARACTER STREAMS: Work with text data (16-bit Unicode characters).
     * Use cases: Text files, CSV, JSON, XML, log files.
     * Automatically handle character encoding.
     * 
     * Classes: FileReader, FileWriter, BufferedReader, BufferedWriter
     */
    
    /**
     * Write text using FileWriter (Character Stream).
     * Writes characters - handles encoding automatically.
     */
    public String writeUsingCharacterStream(String filename, String content) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write(content);
            return "Written " + content.length() + " characters to " + filepath + " using Character Stream";
        }
    }
    
    /**
     * Read text using FileReader (Character Stream).
     */
    public String readUsingCharacterStream(String filename) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (FileReader reader = new FileReader(filepath)) {
            StringBuilder content = new StringBuilder();
            int character;
            
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
            
            return content.toString();
        }
    }
    
    /**
     * Write text using BufferedWriter (Buffered Character Stream).
     * More efficient - uses buffer and provides newLine() method.
     */
    public String writeUsingBufferedWriter(String filename, List<String> lines) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine(); // Platform-independent line separator
            }
            return "Written " + lines.size() + " lines to " + filepath + " using BufferedWriter";
        }
    }
    
    /**
     * Read text using BufferedReader (Buffered Character Stream).
     * Most efficient way to read text files line by line.
     */
    public List<String> readUsingBufferedReader(String filename) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        
        return lines;
    }
    
    /**
     * Append to file using FileWriter with append mode.
     */
    public String appendToFile(String filename, String content) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (FileWriter writer = new FileWriter(filepath, true)) { // true = append mode
            writer.write(content + System.lineSeparator());
            return "Appended content to " + filepath;
        }
    }
    
    // ==================== SERIALIZATION ====================
    
    /**
     * SERIALIZATION: Converting object to byte stream for storage/transmission.
     * DESERIALIZATION: Converting byte stream back to object.
     * 
     * Requirements:
     * - Class must implement Serializable interface
     * - All fields must be serializable (or marked transient)
     * 
     * Use cases: Saving object state, caching, distributed systems, session storage
     */
    
    /**
     * Serialize object to file using ObjectOutputStream.
     */
    public String serializeStudent(StudentRecord student, String filename) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filepath))) {
            oos.writeObject(student);
            return "Serialized student to " + filepath;
        }
    }
    
    /**
     * Deserialize object from file using ObjectInputStream.
     */
    public StudentRecord deserializeStudent(String filename) throws IOException, ClassNotFoundException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filepath))) {
            return (StudentRecord) ois.readObject();
        }
    }
    
    /**
     * Serialize multiple objects (write a list).
     */
    public String serializeStudentList(List<StudentRecord> students, String filename) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filepath))) {
            oos.writeObject(students);
            return "Serialized " + students.size() + " students to " + filepath;
        }
    }
    
    /**
     * Deserialize multiple objects (read a list).
     */
    @SuppressWarnings("unchecked")
    public List<StudentRecord> deserializeStudentList(String filename) throws IOException, ClassNotFoundException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filepath))) {
            return (List<StudentRecord>) ois.readObject();
        }
    }
    
    /**
     * Serialize CourseData object.
     */
    public String serializeCourse(CourseData course, String filename) throws IOException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filepath))) {
            oos.writeObject(course);
            return "Serialized course to " + filepath;
        }
    }
    
    /**
     * Deserialize CourseData object.
     */
    public CourseData deserializeCourse(String filename) throws IOException, ClassNotFoundException {
        String filepath = DATA_DIR + "/" + filename;
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filepath))) {
            return (CourseData) ois.readObject();
        }
    }
    
    // ==================== COMPARISON EXAMPLES ====================
    
    /**
     * Demonstrate Byte Stream vs Character Stream for text.
     * Shows why Character Streams are better for text.
     */
    public String compareByteVsCharacterStream(String text) throws IOException {
        // Using Byte Stream (manual encoding)
        String byteFile = "byte_text.dat";
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        writeUsingByteStream(byteFile, bytes);
        byte[] readBytes = readUsingByteStream(byteFile);
        String fromBytes = new String(readBytes, StandardCharsets.UTF_8);
        
        // Using Character Stream (automatic encoding)
        String charFile = "char_text.txt";
        writeUsingCharacterStream(charFile, text);
        String fromChars = readUsingCharacterStream(charFile);
        
        return "Byte Stream Result: " + fromBytes + "\n" +
               "Character Stream Result: " + fromChars + "\n" +
               "Both produce same result, but Character Stream handles encoding automatically!";
    }
    
    // ==================== MODERN NIO (Java 7+) ====================
    
    /**
     * Modern way to read file using NIO (Files class).
     * Simpler and more efficient.
     */
    public String readFileNIO(String filename) throws IOException {
        Path path = Paths.get(DATA_DIR, filename);
        return Files.readString(path, StandardCharsets.UTF_8);
    }
    
    /**
     * Modern way to write file using NIO.
     */
    public String writeFileNIO(String filename, String content) throws IOException {
        Path path = Paths.get(DATA_DIR, filename);
        Files.writeString(path, content, StandardCharsets.UTF_8);
        return "Written using NIO to " + filename;
    }
    
    /**
     * Read all lines using NIO.
     */
    public List<String> readAllLinesNIO(String filename) throws IOException {
        Path path = Paths.get(DATA_DIR, filename);
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
    
    /**
     * Write all lines using NIO.
     */
    public String writeAllLinesNIO(String filename, List<String> lines) throws IOException {
        Path path = Paths.get(DATA_DIR, filename);
        Files.write(path, lines, StandardCharsets.UTF_8);
        return "Written " + lines.size() + " lines using NIO to " + filename;
    }
    
    // ==================== PRACTICAL EXAMPLES ====================
    
    /**
     * Create sample StudentRecord for testing.
     */
    public StudentRecord createSampleStudent() {
        return new StudentRecord(
            1L,
            "Alice Johnson",
            "alice@university.edu",
            20,
            3.85,
            LocalDate.of(2023, 9, 1),
            "secret123" // This won't be serialized (transient)
        );
    }
    
    /**
     * Create sample CourseData for testing.
     */
    public CourseData createSampleCourse() {
        return new CourseData(
            "CS101",
            "Introduction to Programming",
            "Dr. Smith",
            Arrays.asList("Alice", "Bob", "Charlie", "Diana"),
            30
        );
    }
    
    /**
     * Create sample text lines for testing.
     */
    public List<String> createSampleTextLines() {
        return Arrays.asList(
            "Line 1: Java I/O is powerful",
            "Line 2: Byte streams for binary data",
            "Line 3: Character streams for text data",
            "Line 4: Serialization for objects",
            "Line 5: Always use try-with-resources!"
        );
    }
    
    /**
     * List all files in data directory.
     */
    public List<String> listDataFiles() throws IOException {
        Path dataPath = Paths.get(DATA_DIR);
        if (!Files.exists(dataPath)) {
            return new ArrayList<>();
        }
        
        return Files.list(dataPath)
                .map(path -> path.getFileName().toString())
                .sorted()
                .toList();
    }
    
    /**
     * Delete a file from data directory.
     */
    public String deleteFile(String filename) throws IOException {
        Path path = Paths.get(DATA_DIR, filename);
        if (Files.deleteIfExists(path)) {
            return "Deleted " + filename;
        } else {
            return "File not found: " + filename;
        }
    }
    
    /**
     * Get file size.
     */
    public long getFileSize(String filename) throws IOException {
        Path path = Paths.get(DATA_DIR, filename);
        return Files.size(path);
    }
}
