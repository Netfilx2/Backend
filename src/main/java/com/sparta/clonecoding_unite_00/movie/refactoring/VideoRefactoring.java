package com.sparta.clonecoding_unite_00.movie.refactoring;


import com.sparta.clonecoding_unite_00.favorite.repository.FavoriteRepository;
import com.sparta.clonecoding_unite_00.jwt.TokenProvider;
import com.sparta.clonecoding_unite_00.member.doamin.Member;
import com.sparta.clonecoding_unite_00.movie.domain.SmallCategory;
import com.sparta.clonecoding_unite_00.movie.domain.Video;
import com.sparta.clonecoding_unite_00.movie.domain.VideoSmallCategory;
import com.sparta.clonecoding_unite_00.movie.dto.categoryDto.LargeCategoryDto;
import com.sparta.clonecoding_unite_00.movie.dto.categoryDto.SmallCategoryDto;
import com.sparta.clonecoding_unite_00.movie.dto.serchDto.MovieSearchResponseDto;
import com.sparta.clonecoding_unite_00.movie.dto.videoDto.VideoDetailResponseDto;
import com.sparta.clonecoding_unite_00.movie.dto.videoDto.VideoResponseDto;
import com.sparta.clonecoding_unite_00.movie.repository.SmallCategoryRepository;
import com.sparta.clonecoding_unite_00.movie.repository.VideoRepository;

import com.sparta.clonecoding_unite_00.movie.service.utils.AllGenre;
import com.sparta.clonecoding_unite_00.movie.service.utils.DramaGenre;

import com.sparta.clonecoding_unite_00.movie.service.utils.MovieGenre;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
public class VideoRefactoring {

    private final VideoRepository videoRepository;
    private final SmallCategoryRepository smallCategoryRepository;

    private final FavoriteRepository favoriteRepository;

    private final TokenProvider tokenProvider;

    private final String tvShow = "tvShow";
    private final String movie = "movie";
    private final String random = "random";

    public LargeCategoryDto getMovieRefactoring(HttpServletRequest request){

        LargeCategoryDto largeCategoryDto = null;

        List<SmallCategoryDto> smallCategoryDtoList = new ArrayList<>();
        // 장르별로 찾아서 저장한다.
        for (MovieGenre genre : MovieGenre.values()) {
            List<String> duplicateCheck = new ArrayList<>();

            List<VideoResponseDto> videoResponseDtoList = new ArrayList<>();
            findVideoListByCategory(movie, genre.getGenreName(), videoResponseDtoList, duplicateCheck);

            SmallCategoryDto smallCategoryDto = new SmallCategoryDto(videoResponseDtoList.size(), genre.getGenreName(), videoResponseDtoList);
            smallCategoryDtoList.add(smallCategoryDto);
        }

        largeCategoryDto = new LargeCategoryDto(movie, smallCategoryDtoList);

        return largeCategoryDto;

    }


    public ResponseDto<?> detailMovieRefactoring(Long id, HttpServletRequest request){

        //Optional<Video> detailvideo = videoRepository.findById(id);

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        Video video = verifyVideo(id);
        if (video == null){

            ResponseDto.fail("해당 영화가 없습니다.");

        }
        // 상세페이지 조회시 찜체크 찜했으면 -버튼 찜안했으면 +버튼 || true면 -버튼 false면 +버튼
        if(favoriteRepository.existsByVideo_IdAndMember_Id(video.getId(),member.getId())){

            return ResponseDto.success(

                    VideoDetailResponseDto.builder()

                            .id(video.getId())
                            .title(video.getTitle())
                            .poster_path(video.getPosterPath())
                            .overview(video.getOverview())
                            .first_date(video.getRelease_date())
                            .youtubePath(video.getYoutubePath())
                            .backdrop_path(video.getBackdrop_path())
                            .homepage(video.getHomepage())
                            .grade(video.getPopularity())
                            .likeCnt(video.getLikeCnt())
                            .jjimchek(true)
                            .build()

            );



        }else {

            return ResponseDto.success(

                    VideoDetailResponseDto.builder()

                            .id(video.getId())
                            .title(video.getTitle())
                            .poster_path(video.getPosterPath())
                            .overview(video.getOverview())
                            .first_date(video.getRelease_date())
                            .youtubePath(video.getYoutubePath())
                            .backdrop_path(video.getBackdrop_path())
                            .homepage(video.getHomepage())
                            .grade(video.getPopularity())
                            .likeCnt(video.getLikeCnt())
                            .jjimchek(false)
                            .build()

            );

        }

    }


    public String movieSearchRefactoring(String query) {

        log.info(query);

        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "R19VlCx5JhDdzA7fADa8");
        headers.add("X-Naver-Client-Secret", "Q0WeoiHdj0");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity =
                rest.exchange("https://openapi.naver.com/v1/search/movie.json?query=" + query , HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;



    }

    public ResponseDto<?> moiveItemRefactoring(String result){

        log.info(result);

        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");
        List<MovieSearchResponseDto> itemDtoList = new ArrayList<>();

        for(int i = 0; i<items.length();i++){
            JSONObject itemlist = items.getJSONObject(i);
            MovieSearchResponseDto itemDto = new MovieSearchResponseDto(itemlist);
            itemDtoList.add(itemDto);

        }

        System.out.println(itemDtoList);

        return ResponseDto.success(itemDtoList);



    }


    public LargeCategoryDto getTvShowRefactoring(HttpServletRequest request) {

        LargeCategoryDto largeCategoryDto = null;

        List<SmallCategoryDto> smallCategoryDtoList = new ArrayList<>();
        for (DramaGenre genre : DramaGenre.values()) {
            List<String> duplicateCheck = new ArrayList<>();

            List<VideoResponseDto> videoResponseDtoList = new ArrayList<>();
            findVideoListByCategory(tvShow, genre.getGenreName(), videoResponseDtoList, duplicateCheck);

            SmallCategoryDto smallCategoryDto = new SmallCategoryDto(videoResponseDtoList.size(), genre.getGenreName(), videoResponseDtoList);
            smallCategoryDtoList.add(smallCategoryDto);
        }

        largeCategoryDto = new LargeCategoryDto(tvShow, smallCategoryDtoList);

        return largeCategoryDto;



    }


    public LargeCategoryDto getRandomShowRefactoring(){

        LargeCategoryDto largeCategoryDto = null;

        List<SmallCategoryDto> smallCategoryDtoList = new ArrayList<>();
        for (AllGenre genre : AllGenre.values()) {
            List<String> duplicateCheck = new ArrayList<>();

            List<VideoResponseDto> videoResponseDtoList = new ArrayList<>();
            findRandomVideoListByCategory(random, genre.getGenreName(), videoResponseDtoList, duplicateCheck);

            SmallCategoryDto smallCategoryDto = new SmallCategoryDto(videoResponseDtoList.size(), genre.getGenreName(), videoResponseDtoList);
            smallCategoryDtoList.add(smallCategoryDto);
        }

        largeCategoryDto = new LargeCategoryDto(random, smallCategoryDtoList);

        return largeCategoryDto;

    }


    @Transactional(readOnly = true)
    public Video verifyVideo(Long id) {
        Optional<Video> verifyVideo = videoRepository.findById(id);
        return verifyVideo.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
//        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//            return null;
//        }
        return tokenProvider.getMemberFromAuthentication();
    }


    public LargeCategoryDto findMovieToSmallCategoryRefactoring(String movie, String smallcategory){

        List<SmallCategoryDto> smallCategoryDtoList = new ArrayList<>();
        List<VideoResponseDto> videoResponseDtoList = new ArrayList<>();
        List<String> duplicateCheck = new ArrayList<>();

        findVideoListByCategory(movie, smallcategory, videoResponseDtoList, duplicateCheck);

        SmallCategoryDto smallCategoryDto = new SmallCategoryDto(videoResponseDtoList.size(), smallcategory, videoResponseDtoList);
        smallCategoryDtoList.add(smallCategoryDto);

        LargeCategoryDto largeCategoryDto = new LargeCategoryDto(movie, smallCategoryDtoList);

        return largeCategoryDto;


    }



    private void findRandomVideoListByCategory(String LargeCategory, String key, List<VideoResponseDto> videoResponseDtoList, List<String> duplicateCheck) {
        SmallCategory findSmallCategory = smallCategoryRepository.findBySmallCategoryName(key);
        System.out.println("Genre = " + key);

        List<VideoSmallCategory> videoSmallCategoryList = findSmallCategory.getVideoSmallCategoryList();

        int videoCount = 0;
        for (int i = 0; i < 20; i++) {
            int index;
            if( i % 2 == 0){
                index = videoSmallCategoryList.size() - i-1;
            }
            else
                index = i;

            VideoSmallCategory videoSmallCategory = videoSmallCategoryList.get(index);

            Video video = videoSmallCategory.getVideo();
            boolean contains = checkEffectiveness(duplicateCheck, video, LargeCategory);

            if(contains == false)
                break;
            else{
                duplicateCheck.add(video.getTitle());
                VideoResponseDto newVideoResponseDto = videoToVideoResponseDto(video);
                videoResponseDtoList.add(newVideoResponseDto);
                videoCount +=1;
            }

        }
    }

    private void findVideoListByCategory(String LargeCategory, String key,
                                         List<VideoResponseDto> videoResponseDtoList,
                                         List<String> duplicateCheck) {
        SmallCategory findSmallCategory = smallCategoryRepository.findBySmallCategoryName(key);
        System.out.println("Genre = " + key);

        List<VideoSmallCategory> videoSmallCategoryList = findSmallCategory.getVideoSmallCategoryList();

        int videoCount = 0;
        for (VideoSmallCategory videoSmallCategory : videoSmallCategoryList) {
            if(videoCount > 20) {
                return;
            }

            Video video = videoSmallCategory.getVideo();
            boolean contains = checkEffectiveness(duplicateCheck, video, LargeCategory);

            if(contains == false)
                continue;
            else{
                duplicateCheck.add(video.getTitle());
                VideoResponseDto newVideoResponseDto = videoToVideoResponseDto(video);
                videoResponseDtoList.add(newVideoResponseDto);
                videoCount +=1;
            }
        }
    }

    private boolean checkEffectiveness(List<String> duplicateCheck, Video video, String largeCategory) {
        boolean contains = duplicateCheck.contains(video.getTitle());
        String largeCategoryName = video.getLargeCategory().getLargeCategoryName();
        boolean nameEquals = largeCategory.equals(video.getLargeCategory().getLargeCategoryName());


        if(contains == true){
            return false;
        }
        else{
            if(nameEquals == true || largeCategory == "random")
                return true;
            else
                return false;
        }

    }

    private VideoResponseDto videoToVideoResponseDto(Video video) {
        List<VideoSmallCategory> videoSmallCategoryList1 = video.getVideoSmallCategoryList();
        List<String> genre = new ArrayList<>();
        for (VideoSmallCategory smallCategory : videoSmallCategoryList1) {
            genre.add(smallCategory.getSmallCategory().getSmallCategoryName());
        }

        VideoResponseDto newVideoResponseDto = new VideoResponseDto(genre, video.getId(), video.getTitle(), video.getPosterPath(), video.getOverview(),
                video.getRelease_date(), video.getVote_average(), video.getYoutubePath(), video.getBackdrop_path(), video.getHomepage(),video.getLikeCnt());
        return newVideoResponseDto;
    }






}
