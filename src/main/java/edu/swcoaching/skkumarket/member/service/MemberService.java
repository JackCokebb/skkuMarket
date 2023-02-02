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

    /**
     * email로 회원을 조회한다.
     * @param email 이메일
     * @return 회원 객체
     */
    Member findMemberByEmail(String email);
    void verifyExistEmail(String email);
    Slice findAll(Pageable pageable);


}
