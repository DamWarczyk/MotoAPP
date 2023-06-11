package com.example.TestBackend.repo;

import com.example.TestBackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Long> {

    void deleteStudentById(Long id);

    Optional<Student> findStudentById(Long id);

    Student findStudentByEmail(String email);

    Optional<Student> findByEmail(String email);
}
