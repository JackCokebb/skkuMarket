package edu.swcoaching.skkumarket.auth.service;

import edu.swcoaching.skkumarket.auth.dto.AuthDto;
import edu.swcoaching.skkumarket.auth.dto.SignUpDto;
import edu.swcoaching.skkumarket.jwt.JwtTokenProvider;
import edu.swcoaching.skkumarket.member.dto.LoginDto;
import edu.swcoaching.skkumarket.member.entity.Member;
import edu.swcoaching.skkumarket.member.service.MemberService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Member signUp(SignUpDto memberInfo){
        memberService.verifyExistEmail(memberInfo.getEmail());
        Member member = Member.builder()
                .email(memberInfo.getEmail())
                .nickname(memberInfo.getNickname())
                .password(memberInfo.getPassword())
                .build();
        Member createdMember = memberService.createMember(member);
        return createdMember;
    }

    public AuthDto login(LoginDto loginInfo){
        Member targetMember = memberService.findMemberByEmail(loginInfo.getUsername());
        if(!passwordEncoder.encode(loginInfo.getPassword()).equals(loginInfo.getPassword())){
            throw  new RuntimeException("Password Not Valid");
        }
        String accessToken = jwtTokenProvider.createToken(targetMember);
        return AuthDto.builder()
                .accessToken(accessToken)
                .userId(targetMember.getId())
                .build();
    }

    private boolean isPasswordValid(String email, String password){
        Member targetMember = memberService.findMemberByEmail(email);
        if(targetMember.getPassword().equals(passwordEncoder.encode(password))){
            return true;
        }
        else{
            return false;
        }
    }
}
