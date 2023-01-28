package edu.swcoaching.skkumarket.jwt;

import edu.swcoaching.skkumarket.jwt.dto.TokenInfo;
import edu.swcoaching.skkumarket.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;
    private static final String AUTHORITIES_KEY = "auth";
    private final long tokenValidityInMilliseconds;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey,
                            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
    }

    public String createToken(Authentication authentication){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + TimeUnit.HOURS.toMillis(2));
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long nowInMillis = now.getTime();
        Date expiryDate = new Date(nowInMillis + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(expiryDate)
                .compact();
    }

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
        Member principal = new Member()
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
