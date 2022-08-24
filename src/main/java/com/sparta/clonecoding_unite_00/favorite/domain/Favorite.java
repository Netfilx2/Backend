package com.sparta.clonecoding_unite_00.favorite.domain;

import com.sparta.clonecoding_unite_00.member.doamin.Member;
import com.sparta.clonecoding_unite_00.movie.domain.Video;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn(name = "memberId",nullable = false)
    @ManyToOne (fetch = FetchType.LAZY)
    private Member member;

//    @Column(nullable = true)
//    private Kakao kakaoID;

    @JoinColumn (nullable = false)
    @ManyToOne (fetch = FetchType.LAZY)
    private Video video;


    public Favorite(Member member, Video video){
        this.member = member;
        this.video = video;


    }

}
