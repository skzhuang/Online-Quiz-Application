package com.example.project1.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizQuestion {
    private int quizId;
    private int questionId;
    private Integer userChoiceId;
}
