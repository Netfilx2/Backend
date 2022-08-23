package com.sparta.clonecoding_unite_00.movie.dto.genreDto.movie;

import com.sparta.clonecoding_unite_00.movie.dto.videoDto.VideoListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheMovieApiByIdResponseDto {
    private String homepage;

    private VideoListDto videos;
}

