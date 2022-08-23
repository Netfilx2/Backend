package com.sparta.clonecoding_unite_00.movie.dto.videoDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VideoListDto {

    private VideoListResult[] results;
}
