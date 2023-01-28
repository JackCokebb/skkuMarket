package edu.swcoaching.skkumarket.jwt.filter;

import edu.swcoaching.skkumarket.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

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

        if(StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
            Authentication authentication = jwtTokenProvider.getA
        }
    }

    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.replace("Bearer ", "");
        }

        return null;
    }
}
