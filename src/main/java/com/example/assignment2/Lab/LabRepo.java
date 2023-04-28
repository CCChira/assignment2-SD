package com.example.assignment2.Lab;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LabRepo extends JpaRepository<Lab, Integer> {
    Lab findLabById(int id);

    Lab findLabByNr(int nr);
}