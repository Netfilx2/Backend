package com.sparta.clonecoding_unite_00.member.dto.resposnedto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberProfileResponseDto {

    private Long id;
    private String email;
    private String nickname;
    private String profileimg;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
