package edu.swcoaching.skkumarket.member.repository;

import edu.swcoaching.skkumarket.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    Optional<Member> findByEmail(String email);
    Slice<Member> findSliceBy(final Pageable pageable);
}
