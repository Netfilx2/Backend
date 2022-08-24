package com.sparta.clonecoding_unite_00.movie.repository;


import com.sparta.clonecoding_unite_00.jwt.domain.RefreshToken;
import com.sparta.clonecoding_unite_00.member.doamin.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
  Optional<RefreshToken> findByMember(Member member);
}
