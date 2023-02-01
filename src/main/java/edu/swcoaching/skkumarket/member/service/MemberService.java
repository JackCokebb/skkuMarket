package edu.swcoaching.skkumarket.member.service;

import edu.swcoaching.skkumarket.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MemberService {
    Member createMember(Member member);
    Member updateMember(Member member);
    Member findMemberById(long memberId);
    void deleteMember(long memberId);
    Member findVerifiedMember(long memberId);
    Member findMemberByEmail(String email);
    void verifyExistEmail(String email);
    Slice findAll(Pageable pageable);


}
