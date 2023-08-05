package com.deopraglabs.chebet.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWrapper {
    
    private int id;

    private String firstName;
    
    private String lastName;

    private String email;

    private LocalDate birthDate;

    private String cpf;

    private Gender gender;

    private String phoneNumber;

    private Role role;

    private boolean active;
}
