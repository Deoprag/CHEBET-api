package com.deopraglabs.chebet.model;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
@Table(name = "TB_User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;
    
    @Enumerated
    @Column(name = "gender", nullable = false)
    private Gender gender;
    
    @Column(name = "phone_number", length = 11, nullable = false, unique = true)
    private String phoneNumber;
    
    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;
    
    @Enumerated
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "balance")
    private Float balance;

    @Column(name = "active", nullable = false)
    private boolean active = false;
}
