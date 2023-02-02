package edu.swcoaching.skkumarket.jwt.filter;

import edu.swcoaching.skkumarket.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Slf4j
public class JwtFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private JwtTokenProvider jwtTokenProvider;
    public JwtFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // Get Auth from token, Set Auth to Security context
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){ // 한 세트로 처리 -> validateToken 안에서 예외 뱉게끔 하고 예외 처리는 spring security 예외
            // 로그인 한사람이면 access denied 403
            // 로그인 안한 사람이면 unAuthorized 401
            Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Token saved in the security context, info: {}", authentication.getName());
        }
        else {
            log.error("Not a Valid Token");
            //TODO: 예외 구분해서 뱉도록. wrapping해서 처리 -> wrapping해야 추적이 쉬움.
        }
        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.replace("Bearer ", "");
        }

        return null;
    }
}
