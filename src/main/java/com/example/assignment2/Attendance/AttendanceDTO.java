package com.example.assignment2.Attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO {

    private int id;
    private int studentId;
    private int labId;
    private String requester;
    private boolean present;

}