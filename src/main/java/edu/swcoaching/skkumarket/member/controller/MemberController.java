package edu.swcoaching.skkumarket.member.controller;

import edu.swcoaching.skkumarket.member.dto.MemberDto;
import edu.swcoaching.skkumarket.member.dto.SingleResponseDto;
import edu.swcoaching.skkumarket.member.entity.Member;
import edu.swcoaching.skkumarket.member.mapper.MemberMapper;
import edu.swcoaching.skkumarket.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                new SingleResponseDto<>(true, responseBody),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{member-id}")
    public ResponseEntity putMember(@PathVariable("member-id") long memberId,
                                    @RequestBody @Valid MemberDto.Put requestBody ){
        requestBody.setId(memberId);
        Member member = memberMapper.memberPutToMember(requestBody);
        Member updatedMember = memberService.updateMember(member);
        MemberDto.Response responseBody = memberMapper.memberToMemberResponse(updatedMember);

        return new ResponseEntity<>(
                new SingleResponseDto<>(true, responseBody),
                HttpStatus.OK
        );
    }

    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") long memberId){
        Member findMember = memberService.findVerifiedMember(memberId);
        MemberDto.Response responseBody = memberMapper.memberToMemberResponse(findMember);
        return new ResponseEntity<>(
                new SingleResponseDto<>(true, responseBody),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity getMembers(@RequestParam int page, @RequestParam int size){
        Slice<Member> members = memberService.findAll(PageRequest.of(page-1, size));
        Slice<MemberDto.Response> responseBody = members.map(member -> memberMapper.memberToMemberResponse(member));
        return new ResponseEntity<>(
               new SingleResponseDto<>(true, responseBody),
               HttpStatus.OK
       );
                //TODO 무한 스크롤!!
    }


}
