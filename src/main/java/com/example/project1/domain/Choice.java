package com.example.project1.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Choice {
    private int id;
    private int questionId;
    private String description;
    private boolean isCorrect;
}
