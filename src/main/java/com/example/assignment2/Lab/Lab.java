package com.example.assignment2.Lab;

import com.example.assignment2.Assignment.Assignment;
import com.example.assignment2.Attendance.Attendance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int nr;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Date date;

    @OneToOne(mappedBy = "labAssignment")
    private Assignment labAssignment;

    @OneToMany(mappedBy = "labId")
    private List<Attendance> attendances;

}