package com.sparta.clonecoding_unite_00.member.refactoring;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.clonecoding_unite_00.imageupload.awsS3exceptionhandler.FileTypeErrorException;
import com.sparta.clonecoding_unite_00.imageupload.domain.Images;
import com.sparta.clonecoding_unite_00.imageupload.repository.ImagesRepository;
import com.sparta.clonecoding_unite_00.jwt.TokenProvider;
import com.sparta.clonecoding_unite_00.jwt.dto.TokenDto;
import com.sparta.clonecoding_unite_00.member.doamin.Member;
import com.sparta.clonecoding_unite_00.member.dto.requestdto.*;
import com.sparta.clonecoding_unite_00.member.dto.resposnedto.MemberProfileResponseDto;
import com.sparta.clonecoding_unite_00.member.dto.resposnedto.MemberResponseDto;
import com.sparta.clonecoding_unite_00.member.repository.MemberRepository;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class MemberRefactoring {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    private final AmazonS3Client amazonS3Client;

    private final ImagesRepository imagesRepository;


    // s3관련 전역변수
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름


    public ResponseDto<?> createMemberRefactoring(MemberRequestDto requestDto){


        if (null != ConfirmMember(requestDto.getNickname()) || null != ConfirmMemberEmail(requestDto.getEmail())) {

            return ResponseDto.fail("이미 가입하셨습니다.");
        }


        if (!requestDto.getPassword().equals(requestDto.getPasswordCheck())) {
            return ResponseDto.fail("비밀번호가 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();

        memberRepository.save(member);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );



    }

    public ResponseDto<?> loginRefactoring(LoginRequestDto requestDto,
                                           HttpServletResponse response){

        Member member = ConfirmMemberEmail(requestDto.getEmail());
        if (null == member) {

            return ResponseDto.fail("사용자를 찾을 수 없습니다.");

        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
            return ResponseDto.fail("사용자를 찾을 수 없습니다.");

        }

        // 토큰 발급하기
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                MemberResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );


    }


    public ResponseDto<?> logoutRefactoring(HttpServletRequest request){

        if (!tokenProvider.validateToken(request.getHeader("Authorization"))) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail("사용자를 찾을 수 없습니다.");
        }

        return ResponseDto.successToMessage(200, "OK", "로그아웃 성공");

    }

    public ResponseDto<?> getProfileRefactoring(HttpServletRequest request){

        // 토큰 검증
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        return ResponseDto.success(
                MemberProfileResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .profileimg(member.getProfileImg())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );

    }

    public ResponseDto<?> updateProfileRefactoring(HttpServletRequest request,
                                                   HttpServletResponse response,
                                                   MemberProfileRequestDto memberProfileRequestDto){

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        System.out.println(memberProfileRequestDto.getNickname());

        member.updateNickName(memberProfileRequestDto);

        memberRepository.save(member);

        // 토큰 발급하기
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);


        return ResponseDto.success(
                MemberProfileResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .profileimg(member.getProfileImg())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );


    }


    public ResponseDto<?> updateProfileimgRefactoring(HttpServletRequest request,
                                                      MultipartFile multipartFile,
                                                      String aStatic) throws IOException {


        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        String imageUrl = upload(multipartFile, aStatic);

        Images images = new Images(imageUrl, member.getId());

        imagesRepository.save(images);

        member.updateimg(imageUrl);

        memberRepository.save(member);

        return ResponseDto.success(
                MemberProfileResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .profileimg(member.getProfileImg())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );


    }


    @Transactional
    public Member validateMember(HttpServletRequest request) {
//        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//            return null;
//        }
        return tokenProvider.getMemberFromAuthentication();
    }


    @Transactional(readOnly = true)
    public Member ConfirmMemberEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    //닉네임 중복 확인
    @Transactional(readOnly = true)
    public Member ConfirmMember(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }

    public ResponseDto<?> checkEmailMethod(EmailRequestDto email) {

        String emailCheck = email.getEmail();

        if (emailCheck.equals("")) {

            return ResponseDto.success("이메일을 입력해주세요.");

        }
        if (!emailCheck.contains("@")) {
            return ResponseDto.success("이메일 형식이 아닙니다.");
        }

        if (null != ConfirmMemberEmail(emailCheck)) { // 이메일 중복이면
            return ResponseDto.fail("이미 사용한 이메일이 있습니다.");
        } else { // 이메일 중복 아니면
            return ResponseDto.success("사용가능한 이메일입니다.");
        }

    }

    public ResponseDto<?> checkNickNameMethod(NicknameRequestDto nickname) {

        String nickNameCheck = nickname.getNickname();

        if (nickNameCheck.equals("")) {
            log.info("빈값이다.");
            return ResponseDto.success("닉네임을 입력해주세요");

        } else {
            log.info("빈값이 아니다.");
            if (null != ConfirmMember(nickNameCheck)) { // 넥네임 중복이면
                return ResponseDto.fail("이미 사용한 닉네임이 있습니다.");
            } else { // 닉네임 중복 아니면
                return ResponseDto.success("사용가능한 닉네임입니다.");
            }

        }

    }


    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }


    // ========================= 파일업로드 관련 메서드 ==========================

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {

        String type = file.getContentType();
        long size = file.getSize();
        System.out.println("====================================" + type);
        System.out.println("====================================" + size);

        // 파일 타입 예외처리
        if (!type.startsWith("image")) {

            throw new FileTypeErrorException();

        }

        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());


        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }


    // 로컬에 저장된 파일을 S3로 파일업로드 세팅 및 S3로 업로드
    public String upload(File uploadFile, String dirName) {
        // S3에 저장될 파일 이름
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        // s3로 업로드 및  업로드 파일의 url을 String으로 받음.
        String uploadImageUrl = putS3(uploadFile, fileName);
        log.info(uploadImageUrl);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }

    // 이미지 변환 예외처리
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: 파일 변환에 실패했습니다"));

        return upload(uploadFile, dirName);
    }

    // S3로 업로드 하는 메서드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // s3에 파일 업로드 성공시 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

}
