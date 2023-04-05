package com.example.backend.security;


import com.example.backend.domain.Member;
import com.example.backend.repository.MemberRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MyUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> user = memberRepository.findByEmail(username);
        if (user.isEmpty())
            throw new BadCredentialsException("No user registered with this details!");
        return user.map(MyUserDetails::new).get();
    }
}
