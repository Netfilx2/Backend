package com.sparta.clonecoding_unite_00.kakao;

import com.sparta.clonecoding_unite_00.member.doamin.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoMember extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(unique = true)
    private Long kakaoId;

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

    public KakaoMember(String nickname, String password, String loginId, Long kakaoId) {
        this.nickname = nickname;
        this.password = password;
        this.loginId = loginId;
        this.kakaoId = kakaoId;
    }


}
