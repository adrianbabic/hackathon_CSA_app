package com.example.backend.repository;

import com.example.backend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String username);

    void deleteByVerificationTokenExpirationDateBefore(Date expirationDate);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    List<Member> findMembersByMinorAlertIsTrue();
}
