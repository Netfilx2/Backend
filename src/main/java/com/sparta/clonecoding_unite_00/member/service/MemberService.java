package com.sparta.clonecoding_unite_00.member.service;

import com.sparta.clonecoding_unite_00.member.dto.requestdto.*;
import com.sparta.clonecoding_unite_00.member.refactoring.MemberRefactoring;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRefactoring refactoring;


    // s3관련 전역변수
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    // 이메일 중복확인.
    @Transactional
    public ResponseDto<?> checkEmail(EmailRequestDto email) {
        return refactoring.checkEmailMethod(email);
    }

    // 닉네임 중복확인.
    @Transactional
    public ResponseDto<?> checkNickname(NicknameRequestDto nickname) {
        return refactoring.checkNickNameMethod(nickname);
    }

    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto) {

       return refactoring.createMemberRefactoring(requestDto);

    }
    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto,
                                HttpServletResponse response) {

        return refactoring.loginRefactoring(requestDto,response);


    }

    @Transactional
    public ResponseDto<?> logout(HttpServletRequest request) {

        return refactoring.logoutRefactoring(request);

    }

    // 회원 프로필 조회.
    @Transactional
    public ResponseDto<?> getProfile(HttpServletRequest request) {

        return refactoring.getProfileRefactoring(request);


    }

    // 프로필 닉네임 변경.
    @Transactional
    public ResponseDto<?> updateProfile(HttpServletRequest request,
                                        HttpServletResponse response,
                                        MemberProfileRequestDto memberProfileRequestDto) {

        return refactoring.updateProfileRefactoring(request,response,memberProfileRequestDto);

    }

    // 프로필 이미지 변경
    @Transactional
    public ResponseDto<?> updateProfileimg(HttpServletRequest request,
                                           MultipartFile multipartFile,
                                           String aStatic) throws IOException {

        return refactoring.updateProfileimgRefactoring(request,multipartFile,aStatic);

    }

}
