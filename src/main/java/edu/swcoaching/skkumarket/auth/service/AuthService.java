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
        memberService.verifyExistEmail(memberInfo.email());
        Member member = Member.builder()  // builder 패턴 문제가 있음, builder 패턴은 필수 값이 누락될 수 있음, side pattern이 생길 수 있음 -> of 메소드(static)나 생성자로 바꾸기 -> 필수 파라미터 강제 가능
                .email(memberInfo.email()) // builder 패턴는 필드가 많은 경우 추적이 불가 -> ex) 멤버 객체가 어디서 생성되는지 찾기 힘듬
                .nickname(memberInfo.nickname()) // private(accessLevel을 private)으로 entity 안에서 생성자나 of 메소드 안에서만 사용
                .password(memberInfo.password())
                .authority(Member.Authority.ROLE_USER)
                .status(Member.Status.ACTIVE)
                .build();
        Member createdMember = memberService.createMember(member);
        return createdMember;
    }

    public AuthDto login(LoginDto loginInfo){
        Member targetMember = memberService.findMemberByEmail(loginInfo.getUsername()); // TODO: null 주의
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
