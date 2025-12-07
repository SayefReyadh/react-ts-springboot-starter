package com.example.demo.controller;

import com.example.demo.model.StudentRecord;
import com.example.demo.service.IOExamplesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple I/O Examples Controller for Students
 * Test at: http://localhost:8080/api/io/*
 */
@RestController
@RequestMapping("/api/io")
@CrossOrigin(origins = "*")
public class IOController {
    
    @Autowired
    private IOExamplesService ioService;
    
    // ==================== EXAMPLE 1 & 2: BYTE STREAMS ====================
    
    /**
     * GET /api/io/demo/byte-stream
     * Demo: Write and read using Byte Stream
     */
    @GetMapping("/demo/byte-stream")
    public ResponseEntity<Map<String, Object>> demoByteStream() {
        try {
            // Write
            String writeResult = ioService.byteStreamWriteExample();
            
            // Read
            String readResult = ioService.byteStreamReadExample();
            
            Map<String, Object> response = new HashMap<>();
            response.put("example", "Byte Stream (for binary data)");
            response.put("step1_write", writeResult);
            response.put("step2_read", readResult);
            response.put("useCase", "Images, audio, video, binary files");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== EXAMPLE 3 & 4: CHARACTER STREAMS ====================
    
    /**
     * GET /api/io/demo/character-stream
     * Demo: Write and read using Character Stream
     */
    @GetMapping("/demo/character-stream")
    public ResponseEntity<Map<String, Object>> demoCharacterStream() {
        try {
            // Write
            String writeResult = ioService.characterStreamWriteExample();
            
            // Read
            String readResult = ioService.characterStreamReadExample();
            
            Map<String, Object> response = new HashMap<>();
            response.put("example", "Character Stream (for text data)");
            response.put("step1_write", writeResult);
            response.put("step2_read", readResult);
            response.put("advantage", "Handles Unicode automatically - easier for text!");
            response.put("useCase", "Text files, logs, CSV, JSON");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== EXAMPLE 5 & 6: BUFFERED (BEST FOR TEXT!) ====================
    
    /**
     * GET /api/io/demo/buffered
     * Demo: Write and read multiple lines (RECOMMENDED WAY)
     */
    @GetMapping("/demo/buffered")
    public ResponseEntity<Map<String, Object>> demoBuffered() {
        try {
            // Write 3 lines
            String writeResult = ioService.bufferedWriterExample();
            
            // Read line by line
            List<String> lines = ioService.bufferedReaderExample();
            
            Map<String, Object> response = new HashMap<>();
            response.put("example", "BufferedWriter/BufferedReader (BEST for text files!)");
            response.put("step1_write", writeResult);
            response.put("step2_read", lines);
            response.put("advantage", "Most efficient! Has readLine() and newLine() methods");
            response.put("recommendation", "⭐ Use this for all text file operations!");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== EXAMPLE 7 & 8: SERIALIZATION ====================
    
    /**
     * GET /api/io/demo/serialization
     * Demo: Save and load an object
     */
    @GetMapping("/demo/serialization")
    public ResponseEntity<Map<String, Object>> demoSerialization() {
        try {
            // Save object to file
            String writeResult = ioService.serializationWriteExample();
            
            // Load object from file
            StudentRecord student = ioService.serializationReadExample();
            
            Map<String, Object> response = new HashMap<>();
            response.put("example", "Serialization (saving objects to files)");
            response.put("step1_save", writeResult);
            response.put("step2_load", student);
            response.put("note", "⚠️ Password field is NULL - it was marked 'transient'");
            response.put("useCase", "Saving game state, caching, session storage");
            
            return ResponseEntity.ok(response);
        } catch (IOException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== ALL EXAMPLES IN ONE ====================
    
    /**
     * GET /api/io/demo/all
     * Run all examples at once!
     */
    @GetMapping("/demo/all")
    public ResponseEntity<Map<String, Object>> demoAll() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Example 1 & 2: Byte Stream
            ioService.byteStreamWriteExample();
            String byteRead = ioService.byteStreamReadExample();
            
            // Example 3 & 4: Character Stream
            ioService.characterStreamWriteExample();
            String charRead = ioService.characterStreamReadExample();
            
            // Example 5 & 6: Buffered
            ioService.bufferedWriterExample();
            List<String> lines = ioService.bufferedReaderExample();
            
            // Example 7 & 8: Serialization
            ioService.serializationWriteExample();
            StudentRecord student = ioService.serializationReadExample();
            
            response.put("✅ Example 1-2", "Byte Stream: " + byteRead);
            response.put("✅ Example 3-4", "Character Stream: " + charRead);
            response.put("✅ Example 5-6", "Buffered (lines): " + lines);
            response.put("✅ Example 7-8", "Serialization: " + student.getName());
            response.put("allFiles", ioService.listAllFiles());
            response.put("message", "All 8 examples completed successfully! 🎉");
            
            return ResponseEntity.ok(response);
        } catch (IOException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== HELPER ENDPOINT ====================
    
    /**
     * GET /api/io/files
     * List all created files
     */
    @GetMapping("/files")
    public ResponseEntity<Map<String, Object>> listFiles() {
        try {
            List<String> files = ioService.listAllFiles();
            
            Map<String, Object> response = new HashMap<>();
            response.put("files", files);
            response.put("count", files.size());
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
