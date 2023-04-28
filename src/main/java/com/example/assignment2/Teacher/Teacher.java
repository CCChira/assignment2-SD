package com.example.assignment2.Teacher;

import java.util.Base64;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String username;
    @Column
    private String password;
    @Column(nullable = false)
    @Value("false")
    private Boolean loggedIn;

    public Teacher() {

    }

    public Teacher(String username, String password, Boolean loggedIn) {

        this.username = username;
        this.password = Base64.getEncoder().encodeToString(password.getBytes());
        this.loggedIn = loggedIn;
    }
}



