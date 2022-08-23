package com.sparta.clonecoding_unite_00.movie.dto.categoryDto;

import com.sparta.clonecoding_unite_00.movie.dto.videoDto.VideoResponseDto;
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
public class SmallCategoryDto {
    private int size;
    private String smallCategory;

    private List<VideoResponseDto> dataList = new ArrayList<>();
}
