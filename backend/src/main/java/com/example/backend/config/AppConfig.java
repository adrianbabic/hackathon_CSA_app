package com.example.backend.config;

import com.example.backend.domain.Member;
import com.example.backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    private MemberRepository memberRepository;

    @Bean
    CommandLineRunner initDatabase() {

        return args -> {
            Member member = new Member(null,"admin", "admin", "madzarmaksim@gmail.com", "$2a$12$sJweC3OCLmlJtEoXHLENZOkbDuLI3PscwT4bsGo4HEVXjb2Wkk66a", "ROLE_ADMIN", "000000000", true, true, null);
            memberRepository.save(member);
            member = new Member(null,"marko", "markic", "ian.balen6gmail.com", "$2a$12$sJweC3OCLmlJtEoXHLENZOkbDuLI3PscwT4bsGo4HEVXjb2Wkk66a", "ROLE_MEMBER", "000000000", true, true, null);
            memberRepository.save(member);
        };
    }
}
