package com.example.assignment2.Submission;

import com.example.assignment2.Assignment.Assignment;
import com.example.assignment2.Student.Student;

import com.example.assignment2.Assignment.AssignmentRepo;
import com.example.assignment2.Student.StudentRepo;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubmissionService {
    private final AssignmentRepo assignmentRepo;
    private final StudentRepo studentRepo;
    private final SubmissionRepo submissionRepo;

    public Submission subToEntity(SubmissionDTO submissionDTO) {
        Student submissionStudent = studentRepo.findStudentById(submissionDTO.getStudent());
        Assignment submissionAssignment = assignmentRepo.findAssignmentById(submissionDTO.getAssignment());

        return Submission.builder()
                .assignment(submissionAssignment)
                .student(submissionStudent)
                .grade(submissionDTO.getGrade())
                .gitLink(submissionDTO.getGitLink())
                .comment(submissionDTO.getComment())
                .build();
    }

    public SubmissionDTO subFromEntity(Submission submission) {
        SubmissionDTO submissionDTO = SubmissionDTO.builder().build();
        submissionDTO.setAssignment(submission.getAssignment().getId());
        submissionDTO.setStudent(submission.getStudent().getId());
        submissionDTO.setGrade(submission.getGrade());
        submissionDTO.setGitLink(submission.getGitLink());
        submissionDTO.setComment(submission.getComment());
        return submissionDTO;
    }

    private boolean isSubmission(int assId, int stuId) {
        List<Submission> submissions = submissionRepo.findAll();

        for (Submission submission : submissions) {
            if (submission.getAssignment().getId() == assId && submission.getStudent().getId() == stuId) {
                return true;
            }
        }
        return false;
    }

    public Pair<String, HttpStatus> createSubmission(SubmissionDTO submissionDTO) {
        Submission submission = subToEntity(submissionDTO);
        Student student = studentRepo.findStudentById(submissionDTO.getStudent());
        Assignment assignment = assignmentRepo.findAssignmentById(submissionDTO.getAssignment());
        if (student == null || assignment == null) {
            return Pair.of("Student or assignment not found", HttpStatus.NOT_FOUND);
        }
        if (isSubmission(submissionDTO.getAssignment(), submissionDTO.getStudent())) {
            return Pair.of("Submission already exists", HttpStatus.BAD_REQUEST);
        }

        submissionRepo.save(submission);
        return Pair.of("Submission created", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> gradeSubmission(SubmissionDTO submissionDTO) {
        Submission submission = submissionRepo.findSubmissionById(submissionDTO.getId());
        if (submission == null) {
            return Pair.of("Submission does not exist", HttpStatus.BAD_REQUEST);
        }
        float grade = submissionDTO.getGrade();
        submission.setGrade(grade);
        submissionRepo.save(submission);
        return Pair.of("Submission graded successfully", HttpStatus.OK);
    }
}
