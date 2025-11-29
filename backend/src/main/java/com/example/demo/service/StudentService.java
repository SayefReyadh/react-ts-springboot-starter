package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Student-related business logic.
 * This class acts as an intermediary between the Controller and Repository layers.
 * 
 * @Service marks this class as a service component in Spring
 * Best practice: Keep business logic in the service layer, not in controllers
 */
@Service
public class StudentService {
    
    /**
     * Dependency injection of StudentRepository.
     * @Autowired tells Spring to automatically inject the repository instance
     */
    @Autowired
    private StudentRepository studentRepository;
    
    // ==================== CRUD Operations ====================
    
    /**
     * Retrieves all students from the database.
     * @return List of all students
     */
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    /**
     * Retrieves a single student by their ID.
     * @param id The student's unique identifier
     * @return Optional containing the student if found, empty otherwise
     */
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    /**
     * Creates a new student in the database.
     * @param student The student object to create
     * @return The saved student with generated ID
     */
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }
    
    /**
     * Updates an existing student's information.
     * @param id The ID of the student to update
     * @param studentDetails The new student details
     * @return The updated student object
     * @throws RuntimeException if student with given ID is not found
     */
    public Student updateStudent(Long id, Student studentDetails) {
        // Find the existing student or throw exception
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        // Update the fields
        student.setName(studentDetails.getName());
        student.setEmail(studentDetails.getEmail());
        student.setUniversityId(studentDetails.getUniversityId());
        
        // Save and return the updated student
        return studentRepository.save(student);
    }
    
    /**
     * Deletes a student from the database.
     * @param id The ID of the student to delete
     * @throws RuntimeException if student with given ID is not found
     */
    public void deleteStudent(Long id) {
        // Find the student or throw exception
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        // Delete the student
        studentRepository.delete(student);
    }
}
