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

    @PutMapping("/me") //TODO: /me로 수정
    public ResponseEntity<SingleResponseDto<MemberDto.Response>> putMember(  //ResponseEntity 오류 가능성 있음-> 타입이 없어서 그런것으로 예상 -> 넣어주는게 낫다
                                                                           @RequestBody @Valid MemberDto.Put requestBody ){ //TODO: @Valid에 대한 오류 처리 없음
        //requestBody.setId(memberId); //이중적인 작업임 -> query string이나 requesbody에 id 직접 추가, 객체를 새로 만드는게 나음
        Member member = memberMapper.memberPutToMember(requestBody);
        Member updatedMember = memberService.updateMember(member);
        var responseBody = memberMapper.memberToMemberResponse(updatedMember); //타입이 명확하면 var로 명시 가능, 변수명을 예측 가능하게 설정 -> 타입이 길면 짧게 표기 가능 -> 가독성

        return new ResponseEntity<>( // 필요 없을수도?
                new SingleResponseDto<>(true, responseBody),
                HttpStatus.OK
        );
    }

    @GetMapping("/{memberId}") // camelCase가 나음 -> 그러면 @PathVariable에서도 명시해줄 필요 없음
    public ResponseEntity getMember(@PathVariable long memberId){
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
