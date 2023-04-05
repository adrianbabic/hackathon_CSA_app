package com.example.backend.domain;

import com.example.backend.security.VerificationToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
    private boolean minorAlert;
    private Boolean active;
    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private VerificationToken verificationToken;

}
