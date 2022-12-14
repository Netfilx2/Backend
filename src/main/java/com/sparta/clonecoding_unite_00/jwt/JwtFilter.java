package com.sparta.clonecoding_unite_00.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.clonecoding_unite_00.security.userdetail.UserDetailsServiceImpl;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  public static String AUTHORIZATION_HEADER = "Authorization";
  public static String BEARER_PREFIX = "Bearer ";
  public static String AUTHORITIES_KEY = "auth";
  private final String SECRET_KEY;
  private final TokenProvider tokenProvider;
  private final UserDetailsServiceImpl userDetailsService;

  // 실제 필터 로직.
  //GenericFilterBean의 메서드
  // doFilter의 역할은 jwt토큰의 인증정보를 Security Context에 저장하는 역할.
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {

    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    Key key = Keys.hmacShaKeyFor(keyBytes);

    String jwt = resolveToken(request);

    if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
      Claims claims;
      try {
        claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
      } catch (ExpiredJwtException e) {
        claims = e.getClaims();
      }

      if (claims.getExpiration().toInstant().toEpochMilli() < Instant.now().toEpochMilli()) {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(
            new ObjectMapper().writeValueAsString(
                ResponseDto.fail("Token이 유효햐지 않습니다.")
            )
        );
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      }
//=====================================위는 JWT 파싱하는 부분=======================================================
//=====================================아래는 JWT 복호화해서 가져온다 ================================================
      String subject = claims.getSubject(); // 여기서 서브젝트가 나온다
      Collection<? extends GrantedAuthority> authorities =
          Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());

      UserDetails principal = userDetailsService.loadUserByUsername(subject);

      Authentication authentication = new UsernamePasswordAuthenticationToken(principal, jwt, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }
  // 필터링을 하기위해서 토큰 정보가 필요하니까 resolveToken을 추가
  // request header에서 토큰정보를 꺼내오기 위한 resolve Token메서드를 추가
  private String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

}
