package com.sparta.clonecoding_unite_00.member.repository;





import com.sparta.clonecoding_unite_00.member.doamin.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findById(Long id);
  Optional<Member> findByNickname(String nickname);

  Optional<Member> findByEmail(String email);

  //Optional<Member> findOneWithAuthoritiesByUsername(String username);

  boolean existsByNickname(String name);
}
