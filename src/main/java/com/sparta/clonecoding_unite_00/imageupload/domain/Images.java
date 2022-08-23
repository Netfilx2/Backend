package com.sparta.clonecoding_unite_00.imageupload.domain;


import com.sparta.clonecoding_unite_00.member.doamin.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Images extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(name = "imgUrl")
    String imgUrl;

    @Column(name = "memberId")
    Long memberId;

    //프로필 이미지 관리.
    public Images(String imgUrl, Long memberId){

        this.imgUrl = imgUrl;
        this.memberId = memberId;

    }




}
