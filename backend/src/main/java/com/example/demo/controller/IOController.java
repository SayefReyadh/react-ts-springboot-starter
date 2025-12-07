package com.example.demo.controller;

import com.example.demo.model.CourseData;
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
 * REST Controller demonstrating Java I/O operations.
 * All endpoints are accessible at: http://localhost:8080/api/io/*
 */
@RestController
@RequestMapping("/api/io")
@CrossOrigin(origins = "*")
public class IOController {
    
    @Autowired
    private IOExamplesService ioService;
    
    // ==================== BYTE STREAM ENDPOINTS ====================
    
    /**
     * POST /api/io/byte-stream/write
     * Write binary data using Byte Stream.
     */
    @PostMapping("/byte-stream/write")
    public ResponseEntity<Map<String, Object>> writeByteStream(@RequestBody Map<String, String> request) {
        try {
            String filename = request.getOrDefault("filename", "byte_data.bin");
            String content = request.getOrDefault("content", "Sample binary data");
            byte[] data = content.getBytes();
            
            String result = ioService.writeUsingByteStream(filename, data);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", result);
            response.put("type", "Byte Stream (FileOutputStream)");
            response.put("bytesWritten", data.length);
            response.put("filename", filename);
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * GET /api/io/byte-stream/read?filename=byte_data.bin
     * Read binary data using Byte Stream.
     */
    @GetMapping("/byte-stream/read")
    public ResponseEntity<Map<String, Object>> readByteStream(@RequestParam String filename) {
        try {
            byte[] data = ioService.readUsingByteStream(filename);
            String content = new String(data);
            
            Map<String, Object> response = new HashMap<>();
            response.put("filename", filename);
            response.put("bytesRead", data.length);
            response.put("content", content);
            response.put("type", "Byte Stream (FileInputStream)");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * POST /api/io/buffered-byte-stream/write
     * Write using Buffered Byte Stream (more efficient).
     */
    @PostMapping("/buffered-byte-stream/write")
    public ResponseEntity<Map<String, Object>> writeBufferedByteStream(@RequestBody Map<String, String> request) {
        try {
            String filename = request.getOrDefault("filename", "buffered_byte_data.bin");
            String content = request.getOrDefault("content", "Buffered binary data");
            byte[] data = content.getBytes();
            
            String result = ioService.writeUsingBufferedByteStream(filename, data);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", result);
            response.put("type", "Buffered Byte Stream (BufferedOutputStream)");
            response.put("bytesWritten", data.length);
            response.put("advantage", "Uses internal buffer - more efficient for large files");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * POST /api/io/byte-stream/copy
     * Copy file using Byte Stream.
     */
    @PostMapping("/byte-stream/copy")
    public ResponseEntity<Map<String, Object>> copyFile(@RequestBody Map<String, String> request) {
        try {
            String sourceFile = request.get("sourceFile");
            String destFile = request.get("destFile");
            
            String result = ioService.copyFileUsingByteStream(sourceFile, destFile);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", result);
            response.put("operation", "File copy using Byte Stream");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== CHARACTER STREAM ENDPOINTS ====================
    
    /**
     * POST /api/io/character-stream/write
     * Write text using Character Stream.
     */
    @PostMapping("/character-stream/write")
    public ResponseEntity<Map<String, Object>> writeCharacterStream(@RequestBody Map<String, String> request) {
        try {
            String filename = request.getOrDefault("filename", "text_data.txt");
            String content = request.getOrDefault("content", "Sample text with Unicode: 你好, مرحبا, नमस्ते");
            
            String result = ioService.writeUsingCharacterStream(filename, content);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", result);
            response.put("type", "Character Stream (FileWriter)");
            response.put("charactersWritten", content.length());
            response.put("advantage", "Handles character encoding automatically");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * GET /api/io/character-stream/read?filename=text_data.txt
     * Read text using Character Stream.
     */
    @GetMapping("/character-stream/read")
    public ResponseEntity<Map<String, Object>> readCharacterStream(@RequestParam String filename) {
        try {
            String content = ioService.readUsingCharacterStream(filename);
            
            Map<String, Object> response = new HashMap<>();
            response.put("filename", filename);
            response.put("content", content);
            response.put("charactersRead", content.length());
            response.put("type", "Character Stream (FileReader)");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * POST /api/io/buffered-writer/write
     * Write multiple lines using BufferedWriter.
     */
    @PostMapping("/buffered-writer/write")
    public ResponseEntity<Map<String, Object>> writeBufferedWriter(@RequestBody Map<String, Object> request) {
        try {
            String filename = (String) request.getOrDefault("filename", "lines.txt");
            @SuppressWarnings("unchecked")
            List<String> lines = (List<String>) request.getOrDefault("lines", 
                    ioService.createSampleTextLines());
            
            String result = ioService.writeUsingBufferedWriter(filename, lines);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", result);
            response.put("type", "Buffered Character Stream (BufferedWriter)");
            response.put("linesWritten", lines.size());
            response.put("advantage", "Efficient for writing multiple lines");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * GET /api/io/buffered-reader/read?filename=lines.txt
     * Read multiple lines using BufferedReader.
     */
    @GetMapping("/buffered-reader/read")
    public ResponseEntity<Map<String, Object>> readBufferedReader(@RequestParam String filename) {
        try {
            List<String> lines = ioService.readUsingBufferedReader(filename);
            
            Map<String, Object> response = new HashMap<>();
            response.put("filename", filename);
            response.put("lines", lines);
            response.put("lineCount", lines.size());
            response.put("type", "Buffered Character Stream (BufferedReader)");
            response.put("advantage", "Most efficient for reading text line by line");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * POST /api/io/character-stream/append
     * Append text to existing file.
     */
    @PostMapping("/character-stream/append")
    public ResponseEntity<Map<String, Object>> appendToFile(@RequestBody Map<String, String> request) {
        try {
            String filename = request.get("filename");
            String content = request.get("content");
            
            String result = ioService.appendToFile(filename, content);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", result);
            response.put("operation", "Append mode");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== SERIALIZATION ENDPOINTS ====================
    
    /**
     * POST /api/io/serialize/student
     * Serialize StudentRecord object to file.
     */
    @PostMapping("/serialize/student")
    public ResponseEntity<Map<String, Object>> serializeStudent(@RequestBody(required = false) StudentRecord student) {
        try {
            if (student == null) {
                student = ioService.createSampleStudent();
            }
            
            String filename = "student_" + student.getId() + ".ser";
            String result = ioService.serializeStudent(student, filename);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", result);
            response.put("type", "Object Serialization");
            response.put("serializedObject", student);
            response.put("filename", filename);
            response.put("note", "Password field is transient - not serialized");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * GET /api/io/deserialize/student?filename=student_1.ser
     * Deserialize StudentRecord object from file.
     */
    @GetMapping("/deserialize/student")
    public ResponseEntity<Map<String, Object>> deserializeStudent(@RequestParam String filename) {
        try {
            StudentRecord student = ioService.deserializeStudent(filename);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Deserialized student from " + filename);
            response.put("type", "Object Deserialization");
            response.put("deserializedObject", student);
            response.put("note", "Password is null - was marked transient");
            
            return ResponseEntity.ok(response);
        } catch (IOException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * POST /api/io/serialize/student-list
     * Serialize list of students.
     */
    @PostMapping("/serialize/student-list")
    public ResponseEntity<Map<String, Object>> serializeStudentList(@RequestBody(required = false) List<StudentRecord> students) {
        try {
            if (students == null || students.isEmpty()) {
                students = List.of(ioService.createSampleStudent());
            }
            
            String filename = "students.ser";
            String result = ioService.serializeStudentList(students, filename);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", result);
            response.put("type", "List Serialization");
            response.put("studentCount", students.size());
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * GET /api/io/deserialize/student-list?filename=students.ser
     * Deserialize list of students.
     */
    @GetMapping("/deserialize/student-list")
    public ResponseEntity<Map<String, Object>> deserializeStudentList(@RequestParam String filename) {
        try {
            List<StudentRecord> students = ioService.deserializeStudentList(filename);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Deserialized student list from " + filename);
            response.put("students", students);
            response.put("count", students.size());
            
            return ResponseEntity.ok(response);
        } catch (IOException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * POST /api/io/serialize/course
     * Serialize CourseData object.
     */
    @PostMapping("/serialize/course")
    public ResponseEntity<Map<String, Object>> serializeCourse(@RequestBody(required = false) CourseData course) {
        try {
            if (course == null) {
                course = ioService.createSampleCourse();
            }
            
            String filename = "course_" + course.getCourseCode() + ".ser";
            String result = ioService.serializeCourse(course, filename);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", result);
            response.put("serializedObject", course);
            response.put("note", "Collections (List) are serializable");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * GET /api/io/deserialize/course?filename=course_CS101.ser
     * Deserialize CourseData object.
     */
    @GetMapping("/deserialize/course")
    public ResponseEntity<Map<String, Object>> deserializeCourse(@RequestParam String filename) {
        try {
            CourseData course = ioService.deserializeCourse(filename);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Deserialized course from " + filename);
            response.put("deserializedObject", course);
            
            return ResponseEntity.ok(response);
        } catch (IOException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== COMPARISON & DEMO ENDPOINTS ====================
    
    /**
     * GET /api/io/compare/byte-vs-character
     * Compare Byte Stream vs Character Stream for text handling.
     */
    @GetMapping("/compare/byte-vs-character")
    public ResponseEntity<Map<String, Object>> compareByteVsCharacter() {
        try {
            String text = "Hello World! Unicode: 你好, مرحبا, नमस्ते";
            String result = ioService.compareByteVsCharacterStream(text);
            
            Map<String, Object> response = new HashMap<>();
            response.put("comparison", result);
            response.put("originalText", text);
            response.put("conclusion", "Use Character Streams for text - they handle encoding automatically!");
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * GET /api/io/demo/complete
     * Complete demonstration of all I/O operations.
     */
    @GetMapping("/demo/complete")
    public ResponseEntity<Map<String, Object>> completeDemo() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 1. Byte Stream
            byte[] byteData = "Byte stream data".getBytes();
            ioService.writeUsingByteStream("demo_byte.bin", byteData);
            
            // 2. Character Stream
            ioService.writeUsingCharacterStream("demo_text.txt", "Character stream text");
            
            // 3. BufferedWriter
            ioService.writeUsingBufferedWriter("demo_lines.txt", ioService.createSampleTextLines());
            
            // 4. Serialization
            StudentRecord student = ioService.createSampleStudent();
            ioService.serializeStudent(student, "demo_student.ser");
            
            response.put("message", "Complete I/O demonstration successful");
            response.put("filesCreated", List.of(
                "demo_byte.bin (Byte Stream)",
                "demo_text.txt (Character Stream)",
                "demo_lines.txt (BufferedWriter)",
                "demo_student.ser (Serialization)"
            ));
            response.put("allFiles", ioService.listDataFiles());
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    // ==================== FILE MANAGEMENT ENDPOINTS ====================
    
    /**
     * GET /api/io/files/list
     * List all files in data directory.
     */
    @GetMapping("/files/list")
    public ResponseEntity<Map<String, Object>> listFiles() {
        try {
            List<String> files = ioService.listDataFiles();
            
            Map<String, Object> response = new HashMap<>();
            response.put("files", files);
            response.put("count", files.size());
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * DELETE /api/io/files/delete?filename=demo.txt
     * Delete a file.
     */
    @DeleteMapping("/files/delete")
    public ResponseEntity<Map<String, Object>> deleteFile(@RequestParam String filename) {
        try {
            String result = ioService.deleteFile(filename);
            return ResponseEntity.ok(Map.of("message", result));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
    
    /**
     * GET /api/io/files/size?filename=demo.txt
     * Get file size.
     */
    @GetMapping("/files/size")
    public ResponseEntity<Map<String, Object>> getFileSize(@RequestParam String filename) {
        try {
            long size = ioService.getFileSize(filename);
            
            Map<String, Object> response = new HashMap<>();
            response.put("filename", filename);
            response.put("sizeBytes", size);
            response.put("sizeKB", String.format("%.2f KB", size / 1024.0));
            
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
