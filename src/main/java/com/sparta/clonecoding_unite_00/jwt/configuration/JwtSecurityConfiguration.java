package com.sparta.clonecoding_unite_00.jwt.configuration;


import com.sparta.clonecoding_unite_00.jwt.JwtFilter;
import com.sparta.clonecoding_unite_00.jwt.TokenProvider;
import com.sparta.clonecoding_unite_00.security.userdetail.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


// Token Provider와 JwtFilter를 SecurityConfig에 적용할때 사용하는 클래스
// 스프링 시큐리티 UsernamePasswordAuthenticationFilter를 대신한다.
@RequiredArgsConstructor
public class JwtSecurityConfiguration
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final String SECRET_KEY;
  private final TokenProvider tokenProvider;
  private final UserDetailsServiceImpl userDetailsService;

  @Override
  public void configure(HttpSecurity httpSecurity) {
    JwtFilter customJwtFilter = new JwtFilter(SECRET_KEY, tokenProvider, userDetailsService);
    httpSecurity.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
