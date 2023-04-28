package com.example.assignment2.Student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private int id;
    private String requester;
    private String token;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private int studentGroup;
    private String hobby;

}