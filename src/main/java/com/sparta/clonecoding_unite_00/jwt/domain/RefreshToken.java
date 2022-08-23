package com.sparta.clonecoding_unite_00.jwt.domain;

import com.sparta.clonecoding_unite_00.member.doamin.Member;
import com.sparta.clonecoding_unite_00.member.doamin.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "refresh_Token")
@Entity
public class RefreshToken extends Timestamped {

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(nullable = false)
  private Long id;

  @JoinColumn(name = "member_id", nullable = false)
  @OneToOne(fetch = FetchType.LAZY)
  private Member member;

  @Column(nullable = false, name = "value")
  private String value;

  public void updateValue(String token) {
    this.value = token;
  }
}
