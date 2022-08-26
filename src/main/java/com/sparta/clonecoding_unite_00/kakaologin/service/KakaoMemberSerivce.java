package com.sparta.clonecoding_unite_00.kakaologin.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.clonecoding_unite_00.jwt.TokenProvider;
import com.sparta.clonecoding_unite_00.jwt.dto.TokenDto;
import com.sparta.clonecoding_unite_00.kakaologin.doamain.KakaoMember;
import com.sparta.clonecoding_unite_00.kakaologin.dto.KakaoUserInfoDto;
import com.sparta.clonecoding_unite_00.kakaologin.repository.KakaoRepository;
import com.sparta.clonecoding_unite_00.kakaologin.utils.KakaoMemberDetailsImpl;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoMemberSerivce {

    private final KakaoRepository kakaomemberRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    @Transactional
    public ResponseDto<?> kakaoLogin(String code,HttpServletResponse response) throws JsonProcessingException {

        String accessToken = getAccessToken(code);

        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);

        KakaoMember kakaoMember = registerKakaoUserIfNeeded(kakaoUserInfoDto);

        // 4. 강제 로그인 처리
        forceLogin(kakaoMember);

        TokenDto tokenDto = tokenProvider.generateKakaoTokenDto(kakaoMember);
        tokenToHeaders(tokenDto, response);

        return ResponseDto.success(
                KakaoUserInfoDto.builder()
                        .email(kakaoMember.getEmail())
                        .nickname(kakaoMember.getNickname())
                        .build()
        );



    }

    @Transactional
    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }


    @Transactional
    public String getAccessToken(String code) throws JsonProcessingException {

        // 1. "인가 코드"로 "액세스 토큰" 요청
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "0e615a5250af79c8016d4690ed0abe7c");
        body.add("redirect_uri", "http://localhost:8080/member/kakao/callback");
        body.add("code", code);
        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String accessToken = jsonNode.get("access_token").asText();

        return accessToken;
    }
    @Transactional
    public KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {

        // 2. 토큰으로 카카오 API 호출
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);

        KakaoUserInfoDto kakaoUserInfoDto = new KakaoUserInfoDto(nickname, email);

        return kakaoUserInfoDto;

    }
    @Transactional
    public KakaoMember registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfoDto) {
//        Long kakoId = kakaoUserInfoDto.getId();
//        KakaoMember kakaoMember = kakaomemberRepository.findByKakaoId(kakoId)
//                .orElse(null);

        String femail = kakaoUserInfoDto.getEmail();
        KakaoMember kakaoMember = kakaomemberRepository.findByEmail(femail)
                .orElse(null);

        // 회원가입
        // username: kakao nickname
        if (kakaoMember == null) {

            String nickname = kakaoUserInfoDto.getNickname();

            String password = UUID.randomUUID().toString();
            String encodedpassword = passwordEncoder.encode(password);

            String email = kakaoUserInfoDto.getEmail();

            //UserRoleEnum role = UserRoleEnum.USER;

            kakaoMember = new KakaoMember(nickname, encodedpassword, email);

            kakaomemberRepository.save(kakaoMember);

        }

        return kakaoMember;
    }
    @Transactional
    public void forceLogin(KakaoMember kakaoMember) {
        UserDetails userDetails = new KakaoMemberDetailsImpl(kakaoMember);
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }



//    @Transactional(readOnly = true)
//    public KakaoMember isPresentLoginId(String nickname) {
//        Optional<KakaoMember> optionalLoginId = kakaomemberRepository.findByLoginId(nickname);
//        return optionalLoginId.orElse(null);
//    }
//
//    //카카오 로그인
//    public TokenDto kakaoLogin(String code) throws JsonProcessingException {
//        // 1. "인가 코드"로 "액세스 토큰" 요청
//        String accessToken = getAccessToken(code);
//        // 2. 토큰으로 카카오 API 호출
//        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);
//
//        // DB 에 중복된 Kakao Id 가 있는지 확인
//        Long kakaoId = kakaoUserInfo.getId();
//        KakaoMember kakaoUser = kakaomemberRepository.findByKakaoId(kakaoId)
//                .orElse(null);
//
//
//        if (kakaoUser == null) {
//            // 회원가입
//            // username: kakao nickname
//            String nickname = kakaoUserInfo.getNickname();
//
//            // password: random UUID
//            String password = UUID.randomUUID().toString();
//            String encodedPassword = passwordEncoder.encode(password);
//
//            // email: kakao email
//            String loginId = kakaoUserInfo.getLoginId();
//            // role: 일반 사용자
//
//            kakaoUser = new KakaoMember(nickname, encodedPassword, loginId, kakaoId);
//            kakaomemberRepository.save(kakaoUser);
//        }
//
//        // 4. 강제 kakao로그인 처리
//        UserDetails userDetails = new KakaoDetails(kakaoUser);
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        KakaoMember kakaoMember = isPresentLoginId(kakaoUser.getLoginId());
//        TokenDto tokenDto = tokenProvider.generateTokenDto(kakaoMember);
//        return tokenDto;
//    }
//
//    @Value("${myKaKaoRestAplKey}") //2b80e3f494ac5a13cd2684095580e134
//    private String myKaKaoRestAplKey;
//
//    private String getAccessToken(String code) throws JsonProcessingException {
//
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP Body 생성
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", myKaKaoRestAplKey);
//        body.add("redirect_uri", "http://localhost://8080/kakao/callback"); //프론트랑 협업해야됨, 프론트랑 같은 값을 넣어줘야된다.... 요청을 했을때, redirect가 되는데, 코드를 받는데,
//        body.add("code", code);
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
//                new HttpEntity<>(body, headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        return jsonNode.get("access_token").asText();
//    }
//
//
//    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
//
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoUserInfoRequest,
//                String.class
//        );
//
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//        Long id = jsonNode.get("id").asLong();
//        String nickname = jsonNode.get("properties")
//                .get("nickname").asText();
//        String loginId = jsonNode.get("kakao_account")
//                .get("email").asText();
//
//        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + loginId);
//        return new KakaoUserInfoDto(id, nickname, loginId);
//    }




}
