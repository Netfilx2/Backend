package com.sparta.clonecoding_unite_00.kakaologin.repository;

import com.sparta.clonecoding_unite_00.kakaologin.doamain.KakaoMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KakaoRepository extends JpaRepository<KakaoMember, Long> {

    //Optional<KakaoMember> findByKakaoId(Long kakaoId);

    //Optional<KakaoMember> findByLoginId(String nickname);

    Optional<KakaoMember> findByEmail(String email);
}
