package com.sparta.clonecoding_unite_00.movie.dto.genreDto.movie;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheMovieApiByGenreResponseDto {
    private Float page;
    private Float total_page;
    private List<TheMovieApiResponseResultList> results = new ArrayList<>();
}
