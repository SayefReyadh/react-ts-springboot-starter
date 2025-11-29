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
     * @return ResponseEntity with created student and HTTP 201 CREATED status
     * 
     * Example: POST http://localhost:8080/api/students
     * Body: {"name": "John Doe", "email": "john@example.com", "universityId": "U12345"}
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
    
    /**
     * PUT /api/students/{id} - Updates an existing student.
     * @param id The student ID from the URL path
     * @param student The updated student data from the request body (JSON)
     * @return ResponseEntity with updated student and HTTP 200 OK if successful,
     *         or HTTP 404 NOT FOUND if student doesn't exist
     * 
     * Example: PUT http://localhost:8080/api/students/1
     * Body: {"name": "John Smith", "email": "john.smith@example.com", "universityId": "U12345"}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
