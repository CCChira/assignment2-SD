package com.example.assignment2.Attendance;

import com.example.assignment2.Assignment.Assignment;
import com.example.assignment2.Lab.Lab;
import com.example.assignment2.Lab.LabRepo;
import com.example.assignment2.Student.Student;
import com.example.assignment2.Student.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepo attendanceRepo;
    private final StudentRepo studentRepo;
    private final LabRepo labRepo;

    private boolean isAttendance(int id, int labId) {
        List<Attendance> attendances = attendanceRepo.findAll();

        for (Attendance attendance : attendances)
            if (attendance.getStudentId().getId() == id && attendance.getLabId().getId() == labId) return true;

        return false;
    }

    public AttendanceDTO dtoDromEntity(Attendance attendance) {
        AttendanceDTO attendanceDTO = AttendanceDTO.builder().build();
        attendanceDTO.setId(attendance.getId());
        attendanceDTO.setStudentId(attendance.getStudentId().getId());
        attendanceDTO.setLabId(attendance.getLabId().getId());
        attendanceDTO.setPresent(attendance.getPresent());
        return attendanceDTO;
    }

    public Attendance dtoToEntity(AttendanceDTO attendanceDTO) {

        Attendance attendance = Attendance.builder().build();
        attendance.setStudentId(studentRepo.findStudentById(attendanceDTO.getStudentId()));
        attendance.setLabId(labRepo.findLabById(attendanceDTO.getLabId()));
        attendance.setPresent(attendanceDTO.isPresent());
        return attendance;
    }


    public Pair<String, HttpStatus> createAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = dtoToEntity(attendanceDTO);

        Student student = studentRepo.findStudentById(attendanceDTO.getStudentId());
        Lab lab = labRepo.findLabById(attendanceDTO.getLabId());

        if (student == null || lab == null) {
            return Pair.of("Student or Lab does not exist", HttpStatus.BAD_REQUEST);
        }

        if (isAttendance(student.getId(), lab.getId())) {
            return Pair.of("Attendance already exists", HttpStatus.BAD_REQUEST);
        }

        attendanceRepo.save(attendance);
        return Pair.of("Attendance created successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> deleteAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepo.findAttendanceById(attendanceDTO.getId());
        if (attendance == null) {
            return Pair.of("Attendance does not exist", HttpStatus.BAD_REQUEST);
        }

        attendanceRepo.delete(attendance);
        return Pair.of("Attendance deleted successfully", HttpStatus.OK);
    }

    public Pair<String, HttpStatus> updateAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepo.findAttendanceById(attendanceDTO.getId());
        if (attendance == null) {
            return Pair.of("Attendance does not exist", HttpStatus.BAD_REQUEST);
        }

        Student student = studentRepo.findStudentById(attendanceDTO.getStudentId());
        Lab lab = labRepo.findLabById(attendanceDTO.getLabId());

        if (student == null || lab == null) {
            return Pair.of("Student or lab does not exist", HttpStatus.BAD_REQUEST);
        }

        attendance.setStudentId(student);
        attendance.setLabId(lab);
        attendance.setPresent(attendanceDTO.isPresent());
        attendanceRepo.save(attendance);
        return Pair.of("Attendance updated successfully", HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAttendance(AttendanceDTO attendanceDTO) {
        Attendance attendance = attendanceRepo.findAttendanceById(attendanceDTO.getId());
        if (attendance == null) {
            return Pair.of("Attendance does not exist", HttpStatus.BAD_REQUEST);
        }

        return Pair.of(dtoDromEntity(attendance), HttpStatus.OK);
    }

    public Pair<?, HttpStatus> findAllAttendances() {
        List<Attendance> attendanceList = attendanceRepo.findAll();
        List<AttendanceDTO> attendanceDTOS = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            attendanceDTOS.add(dtoDromEntity(attendance));
        }
        return Pair.of(attendanceDTOS, HttpStatus.OK);
    }

}