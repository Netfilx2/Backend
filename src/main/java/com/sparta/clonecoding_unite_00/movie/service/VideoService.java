package com.sparta.clonecoding_unite_00.movie.service;


import com.sparta.clonecoding_unite_00.movie.dto.categoryDto.LargeCategoryDto;
import com.sparta.clonecoding_unite_00.movie.dto.genreDto.MovieDto;
import com.sparta.clonecoding_unite_00.movie.dto.genreDto.TVDto;
import com.sparta.clonecoding_unite_00.movie.refactoring.VideoRefactoring;
import com.sparta.clonecoding_unite_00.movie.service.utils.DramaGenre;
import com.sparta.clonecoding_unite_00.movie.service.utils.MovieGenre;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class VideoService {


    private final VideoRefactoring refactoring;


    @Transactional
    public LargeCategoryDto getMovie(HttpServletRequest request) {

        return refactoring.getMovieRefactoring(request);

    }

    @Transactional
    public ResponseDto<?> detailMovie(Long id, HttpServletRequest request){

        return refactoring.detailMovieRefactoring(id,request);

    }

    private static Logger logger = LoggerFactory.getLogger(VideoService.class);

    @Transactional
    public String movieSearch(String query) {

        return refactoring.movieSearchRefactoring(query);

    }

    @Transactional
    public ResponseDto<?> moiveItem(String result) {

        return refactoring.moiveItemRefactoring(result);

    }


    @Transactional
    public LargeCategoryDto getTvShow(HttpServletRequest request) {

        return refactoring.getTvShowRefactoring(request);


    }

    @Transactional
    public LargeCategoryDto getRandomShow() {

        return refactoring.getRandomShowRefactoring();

    }

    @Transactional
    public LargeCategoryDto findMovieToSmallCategory(String movie, String smallcategory) {

        return refactoring.findMovieToSmallCategoryRefactoring(movie,smallcategory);
    }


    @Transactional
    public MovieDto findmoviegenre() {
        List<String> moivegenre = new ArrayList<>();

        for (MovieGenre genre : MovieGenre.values()){
            moivegenre.add(genre.getGenreName());
        }
        return new MovieDto(moivegenre);
    }

    @Transactional
    public TVDto findTVgenre() {
        List<String> tvgenre = new ArrayList<>();

        for (DramaGenre genre : DramaGenre.values()){
            tvgenre.add(genre.getGenreName());
        }
        return new TVDto(tvgenre);
    }


}
