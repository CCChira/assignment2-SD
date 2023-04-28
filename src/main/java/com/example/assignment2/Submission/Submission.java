package com.example.assignment2.Submission;

import com.example.assignment2.Assignment.Assignment;
import com.example.assignment2.Student.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "student")
    @NonNull
    private Student student;
    @ManyToOne
    @JoinColumn(name = "assignment")
    @NonNull
    private Assignment assignment;


    @Column
    @NonNull
    private String gitLink;

    @Column
    private String comment;

    @Column
    private float grade;
}