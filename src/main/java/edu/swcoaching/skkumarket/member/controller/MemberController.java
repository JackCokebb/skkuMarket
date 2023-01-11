package edu.swcoaching.skkumarket.member.controller;

import edu.swcoaching.skkumarket.member.dto.MemberDto;
import edu.swcoaching.skkumarket.member.dto.SingleResponseDto;
import edu.swcoaching.skkumarket.member.entity.Member;
import edu.swcoaching.skkumarket.member.mapper.MemberMapper;
import edu.swcoaching.skkumarket.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping
    public ResponseEntity postMember(@RequestBody @Valid MemberDto.Post requestBody){
        Member member = memberMapper.memberPostToMember(requestBody);
        Member createdMember =  memberService.createMember(member);
        MemberDto.Response responseBody = memberMapper.memberToMemberResponse(createdMember);
        return new ResponseEntity<>(
                new SingleResponseDto<>(responseBody),
                HttpStatus.CREATED
        );
    }
    public ResponseEntity patchMember(){

    }


}
