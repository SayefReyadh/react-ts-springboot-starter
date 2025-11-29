package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Student entity database operations.
 * This interface extends JpaRepository which provides built-in CRUD operations.
 * 
 * @Repository marks this as a Data Access Object (DAO)
 * JpaRepository<Student, Long> provides methods like:
 * - findAll(): Get all students
 * - findById(Long id): Get student by ID
 * - save(Student): Create or update a student
 * - delete(Student): Delete a student
 * - And many more...
 * 
 * Spring Data JPA automatically implements this interface at runtime.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    /**
     * Custom query method to find a student by university ID.
     * Spring Data JPA automatically generates the implementation based on the method name.
     * 
     * @param universityId The university ID to search for
     * @return Optional containing the student if found, empty otherwise
     */
    Optional<Student> findByUniversityId(String universityId);
}
