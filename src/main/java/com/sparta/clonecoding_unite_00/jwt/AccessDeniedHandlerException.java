package com.sparta.clonecoding_unite_00.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
// 필요한 권한이 존재하지 않을 경우에 403 Forbiddend 에러를 반환함
@Component
public class AccessDeniedHandlerException implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().println(
        new ObjectMapper().writeValueAsString(
            ResponseDto.fail("로그인이 필요합니다.")
        )
    );
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
  }
}
