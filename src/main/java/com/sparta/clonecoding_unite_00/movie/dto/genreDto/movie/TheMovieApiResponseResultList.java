package com.sparta.clonecoding_unite_00.movie.dto.genreDto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheMovieApiResponseResultList { // db에 저장되는 형태
    private Boolean adult;
    private String backdrop_path;
    private Long[] genre_ids;
    private Long id;
    private String original_language;
    private String original_title;
    private String overview;
    private Float popularity;
    private String poster_path;
    private String release_date;
    private String title;
    private Boolean video;
    private Float vote_average;
    private Long vote_count;
}
