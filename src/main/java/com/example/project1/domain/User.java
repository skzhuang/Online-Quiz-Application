package com.example.project1.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private int id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private boolean isActive;
    private boolean isAdmin;
}
