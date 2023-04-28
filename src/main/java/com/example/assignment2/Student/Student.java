package com.example.assignment2.Student;

import com.example.assignment2.Attendance.Attendance;
import com.example.assignment2.StudentGroup.StudentGroup;
import com.example.assignment2.Submission.Submission;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 128, unique = true)
    private String token;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String password;

    @ManyToOne
    @JoinColumn(name = "studentGroup")
    private StudentGroup studentGroup;

    @OneToMany(mappedBy = "studentId")
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "student")
    private List<Submission> submissions;

    @Column(nullable = false)
    @Value("false")
    private Boolean loggedIn;

    @Column
    private String hobby;

    public Student(int id, String token, String firstName, String lastName, String email, String username, String password, StudentGroup studentGroup, List<Attendance> attendances, List<Submission> submissions, Boolean loggedIn, String hobby) {
        this.id = id;
        this.token = token;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
        this.studentGroup = studentGroup;
        this.attendances = attendances;
        this.submissions = submissions;
        this.loggedIn = false;
        this.hobby = hobby;
    }
}
