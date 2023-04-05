package com.example.backend.security;


import com.example.backend.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = Member.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "security_user_id")
    private Member member;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    public VerificationToken(String token, Member member) {
        this.token = token;
        this.member = member;
        this.expirationDate = calculateExpiryDate(15);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public void removeUser(){
        this.member = null;
    }

}
