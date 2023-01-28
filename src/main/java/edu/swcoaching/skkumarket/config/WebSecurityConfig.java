package edu.swcoaching.skkumarket.config;

import edu.swcoaching.skkumarket.jwt.JwtTokenProvider;
import edu.swcoaching.skkumarket.jwt.config.JwtSecurityConfig;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@AllArgsConstructor
public class WebSecurityConfig{
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                //enable h2 console
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                //set to STATELESS as we do not user session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/signup", "/api/authenticate").permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider));
        return httpSecurity.build();
    }
}
