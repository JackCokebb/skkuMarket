package edu.swcoaching.skkumarket.member.service;

import edu.swcoaching.skkumarket.member.entity.Member;
import edu.swcoaching.skkumarket.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    public Member createMember(Member member) {
        verifyExistEmail(member.getEmail());
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    @Override
    public Member updateMember(Member member) {
        Member findMember = findVerifiedMember(member.getId());
        Optional.ofNullable(member.getUsername())
                .ifPresent(newUsername -> findMember.setUsername(newUsername));
        Optional.ofNullable(member.getPassword())
                .ifPresent(newPassword -> findMember.setPassword(newPassword));

        return memberRepository.save(findMember);
    }

    @Override
    public Member findMemberById(long memberId) {
        return findVerifiedMember(memberId) ;
    }

    @Override
    public void deleteMember(long memberId) {
        Member findMember = findVerifiedMember(memberId);
        memberRepository.delete(findMember);
    }

    @Override
    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember =
                optionalMember.orElseThrow(()-> new RuntimeException("member not found")); // custom?
        return findMember;
    }

    @Override
    public void verifyExistEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(optionalMember.isPresent())
            throw new RuntimeException("email already exist"); // custom?
    }

    @Override
    public Slice<Member> findAll(Pageable pageable){
        Slice<Member> memberSlice = memberRepository.findSliceBy(pageable);
        return memberSlice;
    }
}
