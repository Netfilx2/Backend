package com.sparta.clonecoding_unite_00.member.doamin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.clonecoding_unite_00.member.dto.requestdto.MemberProfileRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String email;

  @Column(name = "profileImg")
  private String profileImg;

  @Column(nullable = false)
  @JsonIgnore
  private String password;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Member member = (Member) o;
    return id != null && Objects.equals(id, member.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
    return passwordEncoder.matches(password, this.password);
  }

  public void updateimg(String profileimg) {
    this.profileImg = profileimg;
  }

  public void updateNickName(MemberProfileRequestDto memberProfileRequestDto){

    this.nickname = memberProfileRequestDto.getNickname();

  }
}
