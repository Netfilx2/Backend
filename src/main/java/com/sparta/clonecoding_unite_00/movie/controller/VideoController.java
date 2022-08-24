package com.sparta.clonecoding_unite_00.movie.controller;


import com.sparta.clonecoding_unite_00.jwt.utils.TokenCheck;
import com.sparta.clonecoding_unite_00.movie.dto.categoryDto.LargeCategoryDto;
import com.sparta.clonecoding_unite_00.movie.dto.genreDto.MovieDto;
import com.sparta.clonecoding_unite_00.movie.dto.genreDto.TVDto;
import com.sparta.clonecoding_unite_00.movie.repository.VideoRepository;
import com.sparta.clonecoding_unite_00.movie.service.VideoService;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    private VideoRepository videoRepository;

    private final TokenCheck tokenCheck;


    @GetMapping("/auth/movie")
    public ResponseDto getMovie(HttpServletRequest request) {

        if (null == request.getHeader("Authorization")) {

            return ResponseDto.fail("Authorization이 없습니다.");
        }

        LargeCategoryDto result = videoService.getMovie(request);
        return new ResponseDto(200, "OK", result);

    }

    // 영화 상세보기.
    @RequestMapping(value = "/auth/movie/{id}", method = RequestMethod.GET)
    public ResponseDto<?> detailMovie(@PathVariable Long id,
                                                      HttpServletRequest request) {

        if (null == request.getHeader("Authorization")) {

            return ResponseDto.fail("Authorization이 없습니다.");
        }

        return videoService.detailMovie(id, request);
    }

    @GetMapping("/auth/tvshow")
    public ResponseDto getTvShow(HttpServletRequest request) {

        if (null == request.getHeader("Authorization")) {

            return ResponseDto.fail("Authorization이 없습니다.");
        }


        LargeCategoryDto result = videoService.getTvShow(request);

        return new ResponseDto(200, "성공", result);
    }


    //api 수정함 영화 검색.
    @RequestMapping(value = "movie/search", method = {RequestMethod.GET})
    public ResponseDto<?> movieSearch(@RequestParam String query) {

        log.info(query);

        //query = URLDecoder.decode(query, "utf-8");

        String result = videoService.movieSearch(query);

        log.info(result);

        return videoService.moiveItem(result);

    }



    @GetMapping("/video/random")
    public ResponseDto getRandomShow() {
        LargeCategoryDto result = videoService.getRandomShow();

        return new ResponseDto(200, "성공", result);
    }


    @GetMapping("/videoCategory")
    public ResponseDto videoSmallCategory(@RequestParam String largeCategory, @RequestParam String smallCategory) {
        LargeCategoryDto result = videoService.findMovieToSmallCategory(largeCategory, smallCategory);

        return new ResponseDto(200, "성공", result);
    }

    @GetMapping("/videomovie")
    public ResponseDto findAllMovieGenre() {
        MovieDto result = videoService.findmoviegenre();
        return new ResponseDto(200, "성공", result);
    }

    @GetMapping("/videoTV")
    public ResponseDto findAllTVGenre() {
        TVDto result = videoService.findTVgenre();
        return new ResponseDto(200, "성공", result);
    }


}
