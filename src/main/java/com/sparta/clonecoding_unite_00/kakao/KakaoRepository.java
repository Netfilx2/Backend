package com.sparta.clonecoding_unite_00.kakao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoRepository extends JpaRepository<KakaoMember, Long> {

    Optional<KakaoMember> findByKakaoId(Long kakaoId);

    Optional<KakaoMember> findByLoginId(String nickname);
}
