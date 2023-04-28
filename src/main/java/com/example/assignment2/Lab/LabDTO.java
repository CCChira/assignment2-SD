package com.example.assignment2.Lab;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LabDTO {
    private int id;
    private int nr;
    private String requester;
    private String title;
    private String description;
    private Date date;

}
