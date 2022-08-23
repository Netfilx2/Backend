package com.sparta.clonecoding_unite_00.movie.service;



import com.sparta.clonecoding_unite_00.movie.dto.genreDto.MovieDto;
import com.sparta.clonecoding_unite_00.movie.dto.genreDto.TVDto;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import com.sparta.clonecoding_unite_00.movie.dto.categoryDto.LargeCategoryDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface VideoService {
    // 협업시 메서드명은 물론이고, 클래스명도 달라질수 있으므로 효율적으로 개발하기 위해서
    // 인터페이스를 상속해서 사용할 수있도록 함.
    public LargeCategoryDto getTvShow(HttpServletRequest request);

    public LargeCategoryDto findMovieToSmallCategory(String movie, String smallcategory);

    public LargeCategoryDto getMovie(HttpServletRequest request);

    LargeCategoryDto getRandomShow();

    public MovieDto findmoviegenre();

    public TVDto findTVgenre();


    public ResponseDto<?> detailMovie(Long id, HttpServletRequest request);

}
