package com.sparta.clonecoding_unite_00.member.controller;




import com.sparta.clonecoding_unite_00.member.dto.requestdto.EmailRequestDto;
import com.sparta.clonecoding_unite_00.member.dto.requestdto.LoginRequestDto;
import com.sparta.clonecoding_unite_00.member.dto.requestdto.MemberRequestDto;
import com.sparta.clonecoding_unite_00.member.dto.requestdto.NicknameRequestDto;
import com.sparta.clonecoding_unite_00.member.service.MemberService;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;


  @RequestMapping(value = "/member/emailcheck", method = RequestMethod.POST)
  public ResponseDto<?> emailCheck(@RequestBody EmailRequestDto emailRequestDto) {
    return memberService.checkEmail(emailRequestDto);
  }

  @RequestMapping(value = "/member/nicknamecheck", method = RequestMethod.POST)
  public ResponseDto<?> nickNameCheck(@RequestBody NicknameRequestDto nicknameRequestDto) {
    return memberService.checkNickname(nicknameRequestDto);
  }

  // @Valid 있었는데 삭제함.
  @RequestMapping(value = "/member/signup", method = RequestMethod.POST)
  public ResponseDto<?> signup(@RequestBody MemberRequestDto requestDto) {
    return memberService.createMember(requestDto);
  }

  @RequestMapping(value = "/member/login", method = RequestMethod.POST)
  public ResponseDto<?> login(@RequestBody LoginRequestDto requestDto,
      HttpServletResponse response
  ) {
    return memberService.login(requestDto, response);
  }

//  @RequestMapping(value = "/api/auth/member/reissue", method = RequestMethod.POST)
//  public ResponseDto<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//    return memberService.reissue(request, response);
//  }

  @RequestMapping(value = "/auth/member/logout", method = RequestMethod.POST)
  public ResponseDto<?> logout(HttpServletRequest request) {
    return memberService.logout(request);
  }
}
