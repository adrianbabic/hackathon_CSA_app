package com.example.backend.controller;

import com.example.backend.domain.Member;
import com.example.backend.domain.dto.MemberDto;
import com.example.backend.request.UpdateMemberRequest;
import com.example.backend.result.ActionResult;
import com.example.backend.result.DataResult;
import com.example.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @DeleteMapping("/member")
    public ResponseEntity<ActionResult> deleteMember(@RequestParam Long id) {
        return memberService.deleteMember(id).intoResponseEntity();
    }

    @GetMapping("/members")
    public ResponseEntity<DataResult<List<MemberDto>>> getMembers() {
        return memberService.getMembers().intoResponseEntity();
    }

    @PutMapping("/member")
    public ResponseEntity<ActionResult> updateMember(@RequestBody UpdateMemberRequest request) {
        return memberService.updateMember(request).intoResponseEntity();
    }

    @GetMapping("/member")
    public ResponseEntity<DataResult<MemberDto>> getMember() {
        return memberService.getMember().intoResponseEntity();
    }



}
