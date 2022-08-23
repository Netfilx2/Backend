package com.sparta.clonecoding_unite_00.member.dto.requestdto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class MemberRequestDto {

  @Email
  @NotBlank (message = "이메일을 입력해주세요")
  private String email;

  @NotBlank (message = "아이디를 입력해주세요")
  private String nickname;

  @NotBlank (message = "비밀번호를 입력해주세요")
  private String password;

  @NotBlank
  private String passwordCheck;
}
