package com.example.project1.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {
    private int id;
    private int categoryId;
    private String description;
    private boolean isActive;
}
