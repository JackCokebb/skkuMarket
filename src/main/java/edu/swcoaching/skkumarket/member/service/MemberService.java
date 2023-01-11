package edu.swcoaching.skkumarket.member.service;

import edu.swcoaching.skkumarket.member.entity.Member;

public interface MemberService {
    Member createMember(Member member);
    Member updateMember(Member member);
    Member findMemberById(long memberId);
    void deleteMember(long memberId);
    Member findVerifiedMember(long memberId);
    void verifyExistEmail(String email);

}
