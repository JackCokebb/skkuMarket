package edu.swcoaching.skkumarket.member.service;

import edu.swcoaching.skkumarket.member.entity.Member;
import edu.swcoaching.skkumarket.member.repository.MemberRepository;
import edu.swcoaching.skkumarket.member.utils.MemberAuthorityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberAuthorityUtils memberAuthorityUtils;

    public MemberDetailService(MemberRepository memberRepository, MemberAuthorityUtils memberAuthorityUtils){
        this.memberAuthorityUtils = memberAuthorityUtils;
        this.memberRepository = memberRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(username);
        Member findMember = optionalMember.orElseThrow(() -> new RuntimeException("# No Such Member"));

        return new MemberDetails(findMember);
    }

    private final class MemberDetails extends Member implements UserDetails{
        MemberDetails(Member member){
            Member.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .password((member.getPassword()))
                    .status(member.getStatus())
                    .authority(member.getAuthority())
                    .build();
        }
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return memberAuthorityUtils.createAuthorities(this.getAuthority());
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }

}
