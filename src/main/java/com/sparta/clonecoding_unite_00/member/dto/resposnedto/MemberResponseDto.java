package com.sparta.clonecoding_unite_00.member.dto.resposnedto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MemberResponseDto {
  private Long id;
  private String nickname;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
