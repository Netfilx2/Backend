package com.sparta.clonecoding_unite_00.kakaologin.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUserInfoDto {

    //private Long id;
    private String email;
    private String nickname;


}
