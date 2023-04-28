package com.example.assignment2.Teacher;

import com.example.assignment2.Assignment.AssignmentDTO;
import com.example.assignment2.Assignment.AssignmentService;
import com.example.assignment2.Attendance.AttendanceDTO;
import com.example.assignment2.Attendance.AttendanceService;
import com.example.assignment2.Lab.LabDTO;
import com.example.assignment2.Lab.LabService;
import com.example.assignment2.Student.StudentDTO;
import com.example.assignment2.Student.StudentService;
import com.example.assignment2.Submission.SubmissionDTO;
import com.example.assignment2.Submission.SubmissionService;
import com.example.assignment2.Teacher.TeacherDTO;
import com.example.assignment2.Teacher.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;
    private final LabService labService;
    private final AttendanceService attendanceService;
    private final AssignmentService assignmentService;
    private final SubmissionService submissionService;


    @Autowired
    public TeacherController(TeacherService teacherService, StudentService studentService, LabService labService, AttendanceService attendanceService, AssignmentService assignmentService, SubmissionService submissionService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.labService = labService;
        this.attendanceService = attendanceService;
        this.assignmentService = assignmentService;
        this.submissionService = submissionService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody TeacherDTO teacherDTO) {
        Pair<String, HttpStatus> pair = teacherService.login(teacherDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody TeacherDTO teacherDTO) {
        Pair<String, HttpStatus> pair = teacherService.logout(teacherDTO);
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/student/create")
    public ResponseEntity<String> createStudent(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(studentDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = studentService.createStudent(studentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @DeleteMapping("/student/delete")
    public ResponseEntity<String> deleteStudent(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(studentDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = studentService.deleteStudent(studentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/student/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentDTO studentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(studentDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = studentService.updateStudent(studentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/student/find")
    public ResponseEntity<?> findStudent(@RequestBody StudentDTO studentDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(studentDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = studentService.findStudent(studentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/student/all")
    public ResponseEntity<?> findAllStudents(@RequestBody TeacherDTO teacherDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(teacherDTO.getUsername());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = studentService.findAllStudents();
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/lab/create")
    public ResponseEntity<String> createLab(@RequestBody LabDTO labDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(labDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = labService.createLab(labDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @DeleteMapping("/lab/delete")
    public ResponseEntity<String> deleteLab(@RequestBody LabDTO labDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(labDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = labService.deleteLab(labDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/lab/update")
    public ResponseEntity<String> updateLab(@RequestBody LabDTO labDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(labDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = labService.updateLab(labDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/lab/find")
    public ResponseEntity<?> findLab(@RequestBody LabDTO labDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(labDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = labService.findLab(labDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/lab/all")
    public ResponseEntity<?> findAllLaboratories(@RequestBody TeacherDTO teacherDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(teacherDTO.getUsername());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = labService.findAllLaboratories();
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/attendance/create")
    public ResponseEntity<String> createAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(attendanceDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = attendanceService.createAttendance(attendanceDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @DeleteMapping("/attendance/delete")
    public ResponseEntity<String> deleteAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(attendanceDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = attendanceService.deleteAttendance(attendanceDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/attendance/update")
    public ResponseEntity<String> updateAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(attendanceDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = attendanceService.updateAttendance(attendanceDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/attendance/find")
    public ResponseEntity<?> findAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(attendanceDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = attendanceService.findAttendance(attendanceDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/attendance/all")
    public ResponseEntity<?> findAllAttendances(@RequestBody TeacherDTO teacherDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(teacherDTO.getUsername());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = attendanceService.findAllAttendances();
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PostMapping("/assignment/create")
    public ResponseEntity<String> createAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(assignmentDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = assignmentService.createAssignment(assignmentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @DeleteMapping("/assignment/delete")
    public ResponseEntity<String> deleteAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(assignmentDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = assignmentService.deleteAssignment(assignmentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/assignment/update")
    public ResponseEntity<String> updateAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(assignmentDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = assignmentService.updateAssignment(assignmentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/assignment/find")
    public ResponseEntity<?> findAssignment(@RequestBody AssignmentDTO assignmentDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(assignmentDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = assignmentService.findAssignment(assignmentDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @GetMapping("/assignment/all")
    public ResponseEntity<?> findAllAssignments(@RequestBody TeacherDTO teacherDTO) {
        Pair<?, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(teacherDTO.getUsername());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = assignmentService.findAllAssignments();
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }

    @PutMapping("/submission/grade")
    public ResponseEntity<String> findAllAssignments(@RequestBody SubmissionDTO submissionDTO) {
        Pair<String, HttpStatus> pair;
        Teacher teacher = teacherService.findByUserName(submissionDTO.getRequester());
        if (!teacherService.isLoggedIn(teacher)) {
            pair = teacherService.notLoggedIn();
        } else {
            pair = submissionService.gradeSubmission(submissionDTO);
        }
        return new ResponseEntity<>(pair.getFirst(), pair.getSecond());
    }


}