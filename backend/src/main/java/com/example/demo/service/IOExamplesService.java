package com.example.demo.service;

import com.example.demo.model.StudentRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

/**
 * Simple Java I/O Examples for Students
 * 
 * Three main concepts:
 * 1. Byte Streams - for binary data (images, files)
 * 2. Character Streams - for text data (easier to use!)
 * 3. Serialization - saving objects to files
 */
@Service
public class IOExamplesService {
    
    private static final String DATA_DIR = "data";
    
    public IOExamplesService() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            System.err.println("Error creating data directory: " + e.getMessage());
        }
    }
    
    // ==================== 1. BYTE STREAMS - For Binary Data ====================
    
    /**
     * Example 1: Write bytes to a file
     * Use this for: images, audio, video, any binary data
     */
    public String byteStreamWriteExample() throws IOException {
        String filename = DATA_DIR + "/example_bytes.bin";
        String message = "Hello from Byte Stream!";
        
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(message.getBytes());
        }
        
        return "✅ Wrote bytes to file: " + filename;
    }
    
    /**
     * Example 2: Read bytes from a file
     */
    public String byteStreamReadExample() throws IOException {
        String filename = DATA_DIR + "/example_bytes.bin";
        
        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] data = fis.readAllBytes();
            String message = new String(data);
            return "✅ Read from file: " + message;
        }
    }
    
    // ==================== 2. CHARACTER STREAMS - For Text Data ====================
    
    /**
     * Example 3: Write text to a file (EASIER WAY!)
     * Use this for: text files, logs, any string data
     */
    public String characterStreamWriteExample() throws IOException {
        String filename = DATA_DIR + "/example_text.txt";
        String message = "Hello from Character Stream! 你好";
        
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(message);
        }
        
        return "✅ Wrote text to file: " + filename;
    }
    
    /**
     * Example 4: Read text from a file
     */
    public String characterStreamReadExample() throws IOException {
        String filename = DATA_DIR + "/example_text.txt";
        StringBuilder content = new StringBuilder();
        
        try (FileReader reader = new FileReader(filename)) {
            int ch;
            while ((ch = reader.read()) != -1) {
                content.append((char) ch);
            }
        }
        
        return "✅ Read from file: " + content.toString();
    }
    
    /**
     * Example 5: Write multiple lines (BEST WAY for text!)
     */
    public String bufferedWriterExample() throws IOException {
        String filename = DATA_DIR + "/example_lines.txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("Line 1: First line");
            writer.newLine();
            writer.write("Line 2: Second line");
            writer.newLine();
            writer.write("Line 3: Third line");
        }
        
        return "✅ Wrote 3 lines to file: " + filename;
    }
    
    /**
     * Example 6: Read line by line (BEST WAY for text!)
     */
    public List<String> bufferedReaderExample() throws IOException {
        String filename = DATA_DIR + "/example_lines.txt";
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        
        return lines;
    }
    
    // ==================== 3. SERIALIZATION - Saving Objects ====================
    
    /**
     * Example 7: Save an object to a file
     * The object must implement Serializable interface!
     */
    public String serializationWriteExample() throws IOException {
        String filename = DATA_DIR + "/student.ser";
        
        // Create a student object
        StudentRecord student = new StudentRecord(
            1L,
            "Alice Johnson",
            "alice@university.edu",
            20,
            3.85,
            LocalDate.of(2023, 9, 1),
            "password123" // This WON'T be saved (it's transient)
        );
        
        // Save it to file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(student);
        }
        
        return "✅ Saved student object to: " + filename;
    }
    
    /**
     * Example 8: Load an object from a file
     */
    public StudentRecord serializationReadExample() throws IOException, ClassNotFoundException {
        String filename = DATA_DIR + "/student.ser";
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            StudentRecord student = (StudentRecord) ois.readObject();
            return student;
        }
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * List all files created
     */
    public List<String> listAllFiles() throws IOException {
        return Files.list(Paths.get(DATA_DIR))
                .map(path -> path.getFileName().toString())
                .sorted()
                .toList();
    }
}
