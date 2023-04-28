package com.example.assignment2.Submission;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmissionDTO {

    private int id;
    private int assignment;
    private int student;
    private String requester;
    private String gitLink;
    private String comment;
    private float grade;

}