package edu.swcoaching.skkumarket.auth.controller;

import edu.swcoaching.skkumarket.auth.dto.AuthDto;
import edu.swcoaching.skkumarket.auth.dto.SignUpDto;
import edu.swcoaching.skkumarket.auth.service.AuthService;
import edu.swcoaching.skkumarket.member.dto.LoginDto;
import edu.swcoaching.skkumarket.member.dto.SingleResponseDto;
import edu.swcoaching.skkumarket.member.entity.Member;
import edu.swcoaching.skkumarket.member.mapper.MemberMapper;
import edu.swcoaching.skkumarket.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MemberService memberService;
    private final MemberMapper memberMapper;

    public ResponseEntity signUp(@RequestBody SignUpDto requestBody){
        Member member = authService.signUp(requestBody);
        return new ResponseEntity<>(
                new SingleResponseDto<>(true, memberMapper.memberToMemberResponse(member)),
                HttpStatus.CREATED);
    }

    public ResponseEntity login(@RequestBody @Valid LoginDto requestBody){
        AuthDto responseBody =  authService.login(requestBody);
        return new ResponseEntity<>(
                new SingleResponseDto<>(true, responseBody),
                HttpStatus.OK
        );
    }
    //TODO: refresh token
}
