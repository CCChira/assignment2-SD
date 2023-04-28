package com.example.assignment2.Assignment;

import com.example.assignment2.Lab.Lab;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepo extends JpaRepository<Assignment, Integer> {
    List<Assignment> findAllByLabAssignment(Lab lab);

    Assignment findAssignmentById(int id);
}