package edu.swcoaching.skkumarket.jwt;

import edu.swcoaching.skkumarket.jwt.dto.TokenInfo;
import edu.swcoaching.skkumarket.member.entity.Member;
import edu.swcoaching.skkumarket.member.service.MemberDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.DayOfWeek;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private static final String AUTHORITIES_KEY = "auth";
    private final long tokenValidityInMilliseconds;
    private final MemberDetailService memberDetailService;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, //secret key는 배포할때 속성으로 넣어주거나 배포하는 서버의 환경변수로,, 외부에 노출되지 않도록,,
                            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds,
                            MemberDetailService memberDetailService){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
        this.memberDetailService = memberDetailService;
    }

    public String createToken(Member member){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + TimeUnit.HOURS.toMillis(2));
        String authorities = member.getAuthority().toString();

        long nowInMillis = now.getTime();
        Date expiryDate = new Date(nowInMillis + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(member.getEmail())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("userId", member.getId())
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiryDate)
                .setIssuedAt(now)
                .compact();
    }

    //return athentication instance from jwt token
    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //User principal = new User(claims.getSubject(), "", authorities);
//        Map<String, Object> principal = new HashMap<>();
//        principal.put("email", claims.getSubject());
//        principal.put("userId", claims.get("userId"));
        UserDetails principal = memberDetailService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Claims getClaims(String jws){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws)
                .getBody();
        return claims;
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SignatureException | MalformedJwtException e){
            log.info("Wrong Jwt Signiture");
        }catch(ExpiredJwtException e){
            log.info("Token Expired");
        }catch (UnsupportedJwtException e){
            log.info("Unsupported Jwt");
        }catch (IllegalArgumentException e){
            log.info("Wrong Jwt token");
        }
        return false;
    }

}
