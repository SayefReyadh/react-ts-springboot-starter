package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Student-related API endpoints.
 * This class handles HTTP requests and responses for student operations.
 * 
 * @RestController combines @Controller and @ResponseBody
 * @RequestMapping("/api/students") sets the base path for all endpoints
 * @CrossOrigin allows requests from any origin (for development)
 * 
 * All endpoints return ResponseEntity for better HTTP response control
 */
@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    
    /**
     * Dependency injection of StudentService.
     * @Autowired tells Spring to inject the service instance
     */
    @Autowired
    private StudentService studentService;
    
    // ==================== API Endpoints ====================
    
    /**
     * GET /api/students - Retrieves all students.
     * @return ResponseEntity containing list of all students and HTTP 200 OK status
     * 
     * Example: GET http://localhost:8080/api/students
     */
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    
    /**
     * GET /api/students/{id} - Retrieves a specific student by ID.
     * @param id The student ID from the URL path
     * @return ResponseEntity with student and HTTP 200 OK if found, or HTTP 404 NOT FOUND if not found
     * 
     * Example: GET http://localhost:8080/api/students/1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id)
                .map(student -> new ResponseEntity<>(student, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    /**
     * POST /api/students - Creates a new student.
     * @param student The student object from the request body (JSON)
     * @return ResponseEntity with created student and HTTP 201 CREATED status,
     *         HTTP 409 CONFLICT if university ID already exists,
     *         or HTTP 400 BAD REQUEST if validation fails
     * 
     * Example: POST http://localhost:8080/api/students
     * Body: {"name": "John Doe", "email": "john@example.com", "universityId": "U12345"}
     */
    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.createStudent(student);
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Check if it's a duplicate university ID error
            if (e.getMessage().contains("already exists")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
            // Other validation errors (missing fields, etc.)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * PUT /api/students/{id} - Updates an existing student.
     * @param id The student ID from the URL path
     * @param student The updated student data from the request body (JSON)
     * @return ResponseEntity with updated student and HTTP 200 OK if successful,
     *         HTTP 404 NOT FOUND if student doesn't exist,
     *         HTTP 409 CONFLICT if university ID is already in use,
     *         or HTTP 400 BAD REQUEST if validation fails
     * 
     * Example: PUT http://localhost:8080/api/students/1
     * Body: {"name": "John Smith", "email": "john.smith@example.com", "universityId": "U12345"}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Check if student not found
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
            // Check if it's a university ID conflict
            if (e.getMessage().contains("already in use")) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            }
            // Other validation errors
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * DELETE /api/students/{id} - Deletes a student.
     * @param id The student ID from the URL path
     * @return ResponseEntity with HTTP 204 NO CONTENT if successful,
     *         or HTTP 404 NOT FOUND if student doesn't exist
     * 
     * Example: DELETE http://localhost:8080/api/students/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
