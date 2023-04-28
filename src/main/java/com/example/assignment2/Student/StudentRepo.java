package com.example.assignment2.Student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

    Student findStudentByFirstNameAndLastName(String firstName, String lastName);

    Student findStudentById(int id);

    Student findStudentByToken(String token);

    Student findStudentByUsername(String username);

}