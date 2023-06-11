package com.example.TestBackend;

import com.example.TestBackend.model.Role;
import com.example.TestBackend.model.Student;
import com.example.TestBackend.repo.RoleRepo;
import com.example.TestBackend.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MotoInitializer implements CommandLineRunner {
    private final StudentRepo studentRepo;
    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MotoInitializer(StudentRepo studentRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.studentRepo = studentRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin = new Role(1L, "ROLE_ADMIN");

        if (roleRepo.findAll().size() != 0){
            return;
        }


        if (roleRepo.findAll().size() == 0){
            roleRepo.save(roleAdmin);

            Role role = new Role((long) 2, "ROLE_USER");
            roleRepo.save(role);
        }

        if ((long) studentRepo.findAll().size() == 0){
            List<Role> roleList = new ArrayList<>();
            roleList.add(roleAdmin);

            Student student = new Student(1L, "Admin", "Admin", "AA@mail.com", "Qwer123!", roleList);
            student.setPassword(passwordEncoder.encode(student.getPassword()));
            studentRepo.save(student);
        }

    }

}
