package com.sparta.clonecoding_unite_00.member.doamin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Timestamped {

  @JsonIgnore
  @CreatedDate
  private LocalDateTime createdAt;

  @JsonIgnore
  @LastModifiedDate
  private LocalDateTime modifiedAt;

}
