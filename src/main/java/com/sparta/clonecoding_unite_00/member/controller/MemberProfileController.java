package com.sparta.clonecoding_unite_00.member.controller;

import com.sparta.clonecoding_unite_00.member.dto.requestdto.MemberProfileRequestDto;
import com.sparta.clonecoding_unite_00.member.service.MemberService;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class MemberProfileController {

    private final MemberService memberService;

    // 프로필 조회.
    @RequestMapping(value = "/auth/memeber/profilepage", method = RequestMethod.GET)
    public ResponseDto<?> getProfile(HttpServletRequest request) {
        return memberService.getProfile(request);
    }


    @RequestMapping(value = "/auth/memeber/profileimg-change", method = RequestMethod.POST)
    public ResponseDto<?> updateProfileimg(HttpServletRequest request,
                                           @RequestParam("images") MultipartFile multipartFile) throws IOException {
        return memberService.updateProfileimg(request,multipartFile, "static");

    }

    @RequestMapping(value = "/auth/memeber/profileimg-change", method = RequestMethod.PUT)
    public ResponseDto<?> updateProfile(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestBody MemberProfileRequestDto memberProfileRequestDto) throws IOException, IOException, IOException {
        return memberService.updateProfile(request,response,memberProfileRequestDto);

    }

}
