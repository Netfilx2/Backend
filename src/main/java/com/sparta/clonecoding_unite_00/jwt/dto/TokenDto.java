package com.sparta.clonecoding_unite_00.jwt.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {
  private String grantType;
  private String accessToken;
  //private String refreshToken;
  private Long accessTokenExpiresIn;
}
