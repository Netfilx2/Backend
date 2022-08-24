package com.sparta.clonecoding_unite_00.favorite.dto;

import com.sparta.clonecoding_unite_00.movie.domain.Video;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FavoriteResponseDto {

    private Video video;
}
