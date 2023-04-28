package com.example.assignment2.Student;

import com.example.assignment2.Lab.LabDTO;
import com.example.assignment2.Submission.SubmissionDTO;
import com.example.assignment2.Submission.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("student")
public class StudentController {

    private final StudentService studentService;
    private final SubmissionService submissionService;

    @Autowired
    public StudentController(StudentService studentService, SubmissionService submissionService) {
        this.studentService = studentService;
        this.submissionService = submissionService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair = studentService.login(studentDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair = studentService.logout(studentDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair = studentService.register(studentDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/labs")
    public ResponseEntity<?> findAllLabs(@RequestBody StudentDTO studentDTO) {
        Pair<?, HttpStatus> pair;
        Student student = studentService.findByUserName(studentDTO.getUsername());
        if (!studentService.isStudentLoggedIn(student)) {
            pair = studentService.invalidLogin();
        } else {
            pair = studentService.findAllLabs();
        }

        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/assignments")
    public ResponseEntity<?> findAssignmentByLab(@RequestBody LabDTO labDTO) {
        Pair<?, HttpStatus> pair = studentService.findAssignmentsByLab(labDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/submission/create")
    public ResponseEntity<?> createSubmission(@RequestBody SubmissionDTO submissionDTO) {
        Pair<?, HttpStatus> pair;
        Student student = studentService.findByUserName(submissionDTO.getRequester());
        if (!studentService.isStudentLoggedIn(student)) {
            pair = studentService.invalidLogin();
        } else {
            pair = submissionService.createSubmission(submissionDTO);
        }

        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

}