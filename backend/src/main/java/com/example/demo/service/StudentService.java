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
     * Validates that the university ID is unique before creating.
     * @param student The student object to create
     * @return The saved student with generated ID
     * @throws RuntimeException if a student with the same university ID already exists
     */
    public Student createStudent(Student student) {
        // Check if a student with the same university ID already exists
        Optional<Student> existingStudent = studentRepository.findByUniversityId(student.getUniversityId());
        if (existingStudent.isPresent()) {
            throw new RuntimeException("Student with university ID '" + student.getUniversityId() + "' already exists");
        }
        
        // Validate required fields
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new RuntimeException("Student name is required");
        }
        if (student.getEmail() == null || student.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Student email is required");
        }
        if (student.getUniversityId() == null || student.getUniversityId().trim().isEmpty()) {
            throw new RuntimeException("University ID is required");
        }
        
        return studentRepository.save(student);
    }
    
    /**
     * Updates an existing student's information.
     * Validates that the new university ID doesn't conflict with existing students.
     * @param id The ID of the student to update
     * @param studentDetails The new student details
     * @return The updated student object
     * @throws RuntimeException if student with given ID is not found or university ID conflicts
     */
    public Student updateStudent(Long id, Student studentDetails) {
        // Find the existing student or throw exception
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        
        // Validate required fields
        if (studentDetails.getName() == null || studentDetails.getName().trim().isEmpty()) {
            throw new RuntimeException("Student name is required");
        }
        if (studentDetails.getEmail() == null || studentDetails.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Student email is required");
        }
        if (studentDetails.getUniversityId() == null || studentDetails.getUniversityId().trim().isEmpty()) {
            throw new RuntimeException("University ID is required");
        }
        
        // Check if the new university ID conflicts with another student
        if (!student.getUniversityId().equals(studentDetails.getUniversityId())) {
            Optional<Student> existingStudent = studentRepository.findByUniversityId(studentDetails.getUniversityId());
            if (existingStudent.isPresent()) {
                throw new RuntimeException("University ID '" + studentDetails.getUniversityId() + "' is already in use by another student");
            }
        }
        
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
