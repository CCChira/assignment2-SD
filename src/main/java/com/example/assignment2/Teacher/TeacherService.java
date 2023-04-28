package com.example.assignment2.Teacher;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Base64;

@RequiredArgsConstructor
@Service
public class TeacherService {
    private final TeacherRepo teacherRepository;

    public Pair<String, HttpStatus> login(TeacherDTO teacher) {
        Teacher teacherFound = findByUserName(teacher.getUsername());
        if (teacherFound == null) return Pair.of("User not found", HttpStatus.NOT_FOUND);
        if (!passwordCheck(teacher.getPassword(), teacherFound.getPassword()))
            return Pair.of("Wrong password", HttpStatus.UNAUTHORIZED);
        
        teacherFound.setLoggedIn(true);
        teacherRepository.save(teacherFound);
        return Pair.of("Successful teacher login", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> logout(TeacherDTO teacher) {
        Teacher teacherFound = findByUserName(teacher.getUsername());
        if (teacherFound == null) return Pair.of("User not found", HttpStatus.NOT_FOUND);
        teacherFound.setLoggedIn(false);
        teacherRepository.save(teacherFound);
        return Pair.of("Successful teacher logout", HttpStatus.OK);
    }

    public Boolean isLoggedIn(Teacher teacher) {
        if (teacher == null) return false;
        return teacher.getLoggedIn();
    }

    public Boolean passwordCheck(String pass, String passEncoded) {
        return Base64.getEncoder().encodeToString(pass.getBytes()).equals(passEncoded);
    }

    public Teacher findByUserName(String username) {
        return teacherRepository.findTeacherByUsername(username);
    }

    public Pair<String, HttpStatus> notLoggedIn() {
        return Pair.of("You are not logged in", HttpStatus.UNAUTHORIZED);
    }
}
