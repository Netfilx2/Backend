package com.sparta.clonecoding_unite_00.member.dto.requestdto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequestDto {

  @NotBlank
  private String email;

  @NotBlank
  private String password;

}
