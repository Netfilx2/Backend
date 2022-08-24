package com.sparta.clonecoding_unite_00.kakaologin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.clonecoding_unite_00.kakaologin.service.KakaoMemberSerivce;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class KakaoLogin {

    private final KakaoMemberSerivce kakaoMemberSerivce;

//    @GetMapping("/user/kakao/callback")
//    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
//
//        kakaoMemberSerivce.kakaoLogin(code);
//
//        return "redirect:/";
//    }

    @GetMapping("/member/kakao/callback")
    public ResponseDto<?> kakaoLogin(@RequestParam String code,
                                     HttpServletResponse response) throws JsonProcessingException {

       return kakaoMemberSerivce.kakaoLogin(code,response);


    }



}
