package com.example.project1.domain;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizResult {
    private int quizId;
    private Timestamp takenTime;
    private String categoryName;
    private String userFullName;
    private int questionsNumber;
}
