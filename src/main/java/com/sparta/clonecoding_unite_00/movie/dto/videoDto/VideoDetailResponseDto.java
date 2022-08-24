package com.sparta.clonecoding_unite_00.movie.dto.videoDto;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoDetailResponseDto { // 상세페이지 출력 dto

    private List<String> genre;
    private Long id;
    private String title;
    private String poster_path;
    private String overview;
    private String first_date;
    private Float grade;
    private String youtubePath;
    private String backdrop_path;
    private String homepage;
    private Integer likeCnt;
    private Boolean jjimchek; // default : false;


}
