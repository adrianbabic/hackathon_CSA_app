package com.example.backend.security;

import com.example.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.transaction.Transactional;
import java.util.Date;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class TokenDeletionScheduler {

    private final MemberRepository memberRepository;

    @Scheduled(fixedDelay = 3600000) //check every hour
    @Transactional
    public void deleteExpired() {
        Date date = new Date();
        Date expirationDate = new Date(date.getTime() - (15 * 60 * 1000));

        memberRepository.deleteByVerificationTokenExpirationDateBefore(expirationDate);
    }

}