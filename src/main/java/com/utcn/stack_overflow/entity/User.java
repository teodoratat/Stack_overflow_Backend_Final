package com.utcn.stack_overflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @Column(name = "l_name")
    private String lastName;

    @Column(name = "f_name")
    private String firstName;
    @Column(name = "email")
    private String email;

    @Column(name = "phone", length = 10)
    private String phoneNr;

    @Column(name = "password")
    private String password;

    @Column(name = "banned")
    private Boolean banned = false;

    @Column(name = "score")
    private Double score = 0.0;

    @Column(name = "role")
    private String role;



}
