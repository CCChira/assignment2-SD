package com.example.assignment2.Student;

import com.example.assignment2.Assignment.Assignment;
import com.example.assignment2.Assignment.AssignmentDTO;
import com.example.assignment2.Assignment.AssignmentRepo;
import com.example.assignment2.Lab.Lab;
import com.example.assignment2.Lab.LabDTO;
import com.example.assignment2.Lab.LabRepo;
import com.example.assignment2.StudentGroup.StudentGroup;
import com.example.assignment2.StudentGroup.StudentGroupRepo;
import com.example.assignment2.Utils.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepo studentRepo;
    private final StudentGroupRepo studentGroupRepo;
    private final LabRepo labRepo;
    private final AssignmentRepo assignmentRepo;

    private boolean isStudent(String firstName, String lastName) {
        return studentRepo.findStudentByFirstNameAndLastName(firstName, lastName) != null;
    }

    private String generateToken(String email) {
        TokenGenerator tokenGenerator = new TokenGenerator(email);
        return tokenGenerator.generateToken();
    }

    public Student dtoToEntity(StudentDTO studentDTO) {
        Optional<StudentGroup> studentGroup = studentGroupRepo.findById(studentDTO.getStudentGroup());
        StudentGroup foundStudentGroup = studentGroup.orElse(null);
        return Student.builder()
                .firstName(studentDTO.getFirstName())
                .lastName(studentDTO.getLastName())
                .email(studentDTO.getEmail())
                .username(studentDTO.getUsername())
                .password(studentDTO.getPassword())
                .studentGroup(foundStudentGroup)
                .hobby(studentDTO.getHobby())
                .build();
    }

    public StudentDTO dtoFromEntity(Student student) {
        /*StudentDTO studentDTO = StudentDTO.builder().build();
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setStudentGroup(student.getStudentGroup().getId());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setUsername(student.getUsername());
        studentDTO.setPassword(student.getPassword());
        studentDTO.setHobby(student.getHobby());*/
        return StudentDTO.builder()
                .studentGroup(student.getStudentGroup().getId())
                .lastName(student.getLastName())
                .firstName(student.getFirstName())
                .email(student.getEmail())
                .username(student.getUsername())
                .password(student.getPassword())
                .hobby(student.getHobby())
                .build();
    }

    public LabDTO labDtoFromEntity(Lab lab) {/*
        LabDTO labDTO = LabDTO.builder().build();
        labDTO.setNr(lab.getNr());
        labDTO.setDate(lab.getDate());
        labDTO.setTitle(lab.getTitle());
        labDTO.setDescription(lab.getDescription());
*/
        return LabDTO.builder()
                .nr(lab.getNr())
                .date(lab.getDate())
                .title(lab.getTitle())
                .description(lab.getDescription())
                .build();

    }

    public AssignmentDTO assignmentDtoFromEntity(Assignment assignment) {
        return AssignmentDTO.builder()
                .id(assignment.getId())
                .name(assignment.getName())
                .assignmentDescription(assignment.getAssignmentDescription())
                .deadline(assignment.getDeadline())
                .labId(assignment.getLabAssignment().getId())
                .build();
    }

    public Pair<String, HttpStatus> deleteStudent(StudentDTO studentDTO) {
        Optional<Student> student = studentRepo.findById(studentDTO.getId());
        Student foundStudent;
        foundStudent = student.orElse(null);
        if (foundStudent == null) {
            return Pair.of("Student does not exist", HttpStatus.BAD_REQUEST);
        }

        studentRepo.deleteById(foundStudent.getId());
        return Pair.of("Student deleted successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> updateStudent(StudentDTO studentDTO) {
        System.out.println(studentDTO);
        System.out.println("Here" + studentRepo.findStudentById(studentDTO.getId()));
        Student foundStudent = studentRepo.findStudentByToken(studentDTO.getToken());

        if (foundStudent == null) {
            return Pair.of("Student does not exist", HttpStatus.BAD_REQUEST);
        }
        if (studentGroupRepo.findById(studentDTO.getStudentGroup()).isEmpty()) {
            return Pair.of("Group does not exist", HttpStatus.BAD_REQUEST);
        }

        foundStudent.setFirstName(studentDTO.getFirstName());
        foundStudent.setLastName(studentDTO.getLastName());
        foundStudent.setEmail(studentDTO.getEmail());
        foundStudent.setUsername(studentDTO.getUsername());
        foundStudent.setStudentGroup(studentGroupRepo.findById(studentDTO.getStudentGroup()).get());
        foundStudent.setHobby(studentDTO.getHobby());
        studentRepo.save(foundStudent);
        return Pair.of("Student updated successfully", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findStudent(StudentDTO studentDTO) {
        Student student = studentRepo.findStudentByFirstNameAndLastName(studentDTO.getFirstName(), studentDTO.getLastName());
        if (student == null) {
            return Pair.of("Student does not exist", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(dtoFromEntity(student), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAllStudents() {
        List<Student> studentList = studentRepo.findAll();
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student student : studentList) {
            studentDTOS.add(dtoFromEntity(student));
        }
        return Pair.of(studentDTOS, HttpStatus.OK);
    }

    public Pair<String, HttpStatus> register(StudentDTO studentDTO) {
        Optional<StudentGroup> candidate = studentGroupRepo.findById(studentDTO.getStudentGroup());
        StudentGroup st = candidate.orElse(null);
        Student student = studentRepo.findStudentByToken(studentDTO.getToken());
        if (student == null) {
            return Pair.of("Token is incorrect", HttpStatus.BAD_REQUEST);
        }

        student.setPassword(Base64.getEncoder().encodeToString(studentDTO.getPassword().getBytes()));
        student.setStudentGroup(st);
        student.setLoggedIn(Boolean.TRUE);
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setUsername(studentDTO.getUsername());
        student.setHobby(studentDTO.getHobby());

        studentRepo.save(student);
        return Pair.of("Student registered successfully", HttpStatus.OK);

    }

    public Student findByUserName(String username) {
        return studentRepo.findStudentByUsername(username);
    }

    public Boolean isStudentLoggedIn(Student student) {
        if (student == null) {
            return false;
        }
        return !student.getLoggedIn().equals(Boolean.FALSE);
    }

    public Pair<String, HttpStatus> invalidLogin() {
        return Pair.of("Student is not logged in", HttpStatus.BAD_REQUEST);
    }

    public Boolean checkPasswordMatch(String password, String passwordHash) {
        return Base64.getEncoder().encodeToString(password.getBytes()).equals(passwordHash);
    }

    public Pair<String, HttpStatus> login(StudentDTO studentDTO) {
        Student foundStudent = findByUserName(studentDTO.getUsername());
        if (foundStudent == null || !checkPasswordMatch(studentDTO.getPassword(), foundStudent.getPassword())) {
            return Pair.of("Username or password incorrect", HttpStatus.BAD_REQUEST);
        }
        if (isStudentLoggedIn(foundStudent)) {
            return Pair.of("User already logged in.", HttpStatus.BAD_REQUEST);
        }

        foundStudent.setLoggedIn(Boolean.TRUE);
        studentRepo.save(foundStudent);
        return Pair.of("You have successfully logged in", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> logout(StudentDTO studentDTO) {
        Student foundStudent = findByUserName(studentDTO.getUsername());
        if (foundStudent == null) {
            return Pair.of("Username is incorrect", HttpStatus.BAD_REQUEST);
        }
        if (!isStudentLoggedIn(foundStudent)) {
            return Pair.of("User already logged out.", HttpStatus.BAD_REQUEST);
        }

        foundStudent.setLoggedIn(Boolean.FALSE);
        studentRepo.save(foundStudent);
        return Pair.of("You have successfully logged out", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAllLabs() {
        List<Lab> labList = labRepo.findAll();
        List<LabDTO> labDTOS = new ArrayList<>();
        for (Lab lab : labList) {
            labDTOS.add(labDtoFromEntity(lab));
        }
        return Pair.of(labDTOS, HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAssignmentsByLab(LabDTO labDTO) {
        Lab lab = labRepo.findLabByNr(labDTO.getNr());
        if (lab == null) {
            return Pair.of("Lab does not exist", HttpStatus.BAD_REQUEST);
        }

        List<Assignment> assignmentList = assignmentRepo.findAllByLabAssignment(lab);
        List<AssignmentDTO> assignmentDTOS = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            assignmentDTOS.add(assignmentDtoFromEntity(assignment));
        }
        return Pair.of(assignmentDTOS, HttpStatus.OK);
    }

    public Pair<String, HttpStatus> createStudent(StudentDTO studentDTO) {
        Student student = dtoToEntity(studentDTO);
        student.setToken(generateToken(studentDTO.getEmail()));

        if (isStudent(student.getFirstName(), student.getLastName())) {
            return Pair.of("Student already exists", HttpStatus.BAD_REQUEST);
        }

        studentRepo.save(student);
        return Pair.of("Student created successfully", HttpStatus.OK);

    }
}
