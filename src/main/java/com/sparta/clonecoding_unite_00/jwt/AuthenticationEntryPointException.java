package com.sparta.clonecoding_unite_00.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;


import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 유효한 자격증명을 제공하지 않고 접근하려할때 401 unauthorized에러를 리턴할
// jwtAuthenticationEntryPont 클래스를 만든다.
@Component
public class AuthenticationEntryPointException implements
    AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().println(
        new ObjectMapper().writeValueAsString(
            ResponseDto.fail("로그인이 필요합니다.")
        )
    );
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
  }
}
