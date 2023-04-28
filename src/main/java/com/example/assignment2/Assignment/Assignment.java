package com.example.assignment2.Assignment;

import com.example.assignment2.Lab.Lab;
import com.example.assignment2.Submission.Submission;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String assignmentDescription;

    @Column
    private Date deadline;

    @OneToMany(mappedBy = "assignment")
    private List<Submission> submissions;

    @OneToOne
    @JoinColumn(name = "labAssignment")
    private Lab labAssignment;


}