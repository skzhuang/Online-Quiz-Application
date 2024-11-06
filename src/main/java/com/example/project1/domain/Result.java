package com.example.project1.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {
    private Question question;
    private Choice realChoices;
    private Choice userChoice;
}
