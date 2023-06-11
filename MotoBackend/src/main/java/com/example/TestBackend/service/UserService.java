package com.example.TestBackend.service;

import com.example.TestBackend.model.Role;
import com.example.TestBackend.model.Student;

import java.util.List;

public interface UserService {

    Student saveStudent(Student student);
    Role saveRole(Role role);
    void addRoleToUser(String email, String roleName);
    Student getStudent(String email);
    List<Student> getStudents();

}
