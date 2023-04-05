package com.example.backend.service;

import com.example.backend.domain.Member;
import com.example.backend.domain.dto.MemberDto;
import com.example.backend.mapper.Mapper;
import com.example.backend.repository.MemberRepository;
import com.example.backend.request.UpdateMemberRequest;
import com.example.backend.result.ActionResult;
import com.example.backend.result.DataResult;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final Mapper mapper;
    private static final Logger logger = LogManager.getLogger(RecordService.class);



    public ActionResult deleteMember(Long id) {
        memberRepository.deleteById(id);
        logger.info("Member deleted successfully");
        return new ActionResult(true, "Member deleted successfully");
    }

    public DataResult<List<MemberDto>> getMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberDto> memberDtos = new ArrayList<>();
        MemberDto memberDto;
        for(Member m : members){
            memberDto = mapper.memberToMemberDto(m);
            memberDtos.add(memberDto);
        }
        logger.info("Members found successfully");
        return new DataResult<>(true, "Members found successfully", memberDtos);
    }

    public ActionResult updateMember(UpdateMemberRequest request) {

        Member member = memberRepository.findById(request.getId()).get();

        if(request.getFirstName() != null){
            member.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null){
            member.setLastName(request.getLastName());
        }
        if(request.getOldPassword() != null && request.getNewPassword() != null){
            member.setPassword(request.getNewPassword());
        }
        if(request.getRole() != null){
            member.setRole("ROLE_" + request.getRole().toUpperCase());
        }
        if(request.getPhoneNumber() != null){
            member.setPhoneNumber(request.getPhoneNumber());
        }
        if(request.isMinorAlert() != member.isMinorAlert()){
            member.setMinorAlert(request.isMinorAlert());
        }
        memberRepository.save(member);
        logger.info("Member updated successfully");
        return new ActionResult(true, "Member updated successfully");
    }

    public DataResult<MemberDto> getMember() {
        logger.info("Member found successfully");
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.error("User not found!");
                    return new EntityNotFoundException("User not found!");
                });
        return new DataResult<>(true, "Member found successfully", mapper.memberToMemberDto(member));
    }
}
