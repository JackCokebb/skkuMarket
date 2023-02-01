package edu.swcoaching.skkumarket.member.utils;

import edu.swcoaching.skkumarket.member.entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemberAuthorityUtils {
    public List<GrantedAuthority> createAuthorities(Member.Authority authority){
        return AuthorityUtils.createAuthorityList(authority.toString());
    }
}
