package com.example.assignment2.Assignment;

import com.example.assignment2.Lab.Lab;
import com.example.assignment2.Lab.LabRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepo assignmentRepo;
    private final LabRepo labRepo;

    public Assignment toEntity(AssignmentDTO assignmentDTO) {
        Lab laboratory = labRepo.findLabById(assignmentDTO.getLabId());

        Assignment assignment = Assignment.builder().build();
        assignment.setName(assignmentDTO.getName());
        assignment.setAssignmentDescription(assignmentDTO.getAssignmentDescription());
        assignment.setDeadline(assignmentDTO.getDeadline());
        assignment.setLabAssignment(laboratory);
        return assignment;

    }

    public AssignmentDTO dtoFromEntity(Assignment assignment) {
        AssignmentDTO assignmentDTO = AssignmentDTO.builder().build();
        assignmentDTO.setId(assignment.getLabAssignment().getId());
        assignmentDTO.setLabId(assignment.getLabAssignment().getId());
        assignmentDTO.setName(assignment.getName());
        assignmentDTO.setDeadline(assignment.getDeadline());
        assignmentDTO.setAssignmentDescription(assignment.getAssignmentDescription());
        return assignmentDTO;
    }

    private boolean isAssignment(int id) {
        List<Assignment> assignments = assignmentRepo.findAll();

        for (Assignment as : assignments)
            if (id == as.getLabAssignment().getId()) return true;

        return false;
    }

    public Pair<String, HttpStatus> createAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = toEntity(assignmentDTO);
        Lab laboratory = labRepo.findLabByNr(assignmentDTO.getLabId());

        if (isAssignment(assignmentDTO.getId())) {
            return Pair.of("Assignment already exists", HttpStatus.BAD_REQUEST);
        }

        if (laboratory == null) {
            return Pair.of("Lab does not exist", HttpStatus.BAD_REQUEST);
        }

        assignmentRepo.save(assignment);
        return Pair.of("Assignment created successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> deleteAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepo.findAssignmentById(assignmentDTO.getId());
        if (assignment == null) {
            return Pair.of("Assignment does not exist", HttpStatus.BAD_REQUEST);
        }

        assignmentRepo.delete(assignment);
        return Pair.of("Assignment deleted successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> updateAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepo.findAssignmentById(assignmentDTO.getId());
        Lab laboratory = labRepo.findLabById(assignmentDTO.getLabId());

        if (assignment == null) {
            return Pair.of("Assignment does not exist", HttpStatus.BAD_REQUEST);
        }

        if (laboratory == null) {
            return Pair.of("Lab does not exist", HttpStatus.BAD_REQUEST);
        }

        assignment.setName(assignmentDTO.getName());
        assignment.setDeadline(assignmentDTO.getDeadline());
        assignment.setAssignmentDescription(assignmentDTO.getAssignmentDescription());
        assignment.setLabAssignment(laboratory);

        assignmentRepo.save(assignment);
        return Pair.of("Assignment updated successfully", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAssignment(AssignmentDTO assignmentDTO) {
        Assignment assignment = assignmentRepo.findAssignmentById(assignmentDTO.getId());
        if (assignment == null) {
            return Pair.of("Assignment does not exist", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(dtoFromEntity(assignment), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAllAssignments() {
        List<Assignment> assignmentList = assignmentRepo.findAll();
        List<AssignmentDTO> assignmentDTOS = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            assignmentDTOS.add(dtoFromEntity(assignment));
        }
        return Pair.of(assignmentDTOS, HttpStatus.OK);
    }

}
