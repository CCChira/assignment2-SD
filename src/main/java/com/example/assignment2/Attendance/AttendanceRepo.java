package com.example.assignment2.Attendance;

import com.example.assignment2.Lab.Lab;
import com.example.assignment2.Student.Student;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
    Attendance findAttendanceByStudentIdAndLabId(@NonNull Student studentId, @NonNull Lab laboratoryId);

    Attendance findAttendanceById(int id);
}
