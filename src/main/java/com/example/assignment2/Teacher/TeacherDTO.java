package com.example.assignment2.Teacher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TeacherDTO {
    public String password;
    public String username;
}
