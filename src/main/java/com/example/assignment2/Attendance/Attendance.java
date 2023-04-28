package com.example.assignment2.Attendance;

import com.example.assignment2.Lab.Lab;
import com.example.assignment2.Student.Student;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Boolean present;

    @ManyToOne
    @JoinColumn(name = "lab_id")
    @NonNull
    private Lab labId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @NonNull
    private Student studentId;

}
