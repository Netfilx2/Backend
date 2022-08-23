package com.sparta.clonecoding_unite_00.movie.service;



import com.sparta.clonecoding_unite_00.movie.utils.AllGenre;

import com.sparta.clonecoding_unite_00.movie.utils.DramaGenre;

import com.sparta.clonecoding_unite_00.movie.utils.MovieGenre;

import com.sparta.clonecoding_unite_00.movie.dto.genreDto.MovieDto;
import com.sparta.clonecoding_unite_00.movie.dto.genreDto.TVDto;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import com.sparta.clonecoding_unite_00.movie.dto.categoryDto.LargeCategoryDto;
import com.sparta.clonecoding_unite_00.movie.dto.categoryDto.SmallCategoryDto;
import com.sparta.clonecoding_unite_00.movie.dto.videoDto.VideoResponseDto;
import com.sparta.clonecoding_unite_00.movie.domain.SmallCategory;
import com.sparta.clonecoding_unite_00.movie.domain.Video;
import com.sparta.clonecoding_unite_00.movie.domain.VideoSmallCategory;
import com.sparta.clonecoding_unite_00.movie.repository.SmallCategoryRepository;
import com.sparta.clonecoding_unite_00.movie.repository.VideoRepository;
import com.sparta.clonecoding_unite_00.jwt.utils.TokenCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VideoServiceCollabor implements VideoService{
    private final VideoRepository videoRepository;
    private final SmallCategoryRepository smallCategoryRepository;

    private final TokenCheck tokenCheck;

    private final String tvShow = "tvShow";
    private final String movie = "movie";
    private final String random = "random";



    @Override
    public LargeCategoryDto getMovie(HttpServletRequest request) {

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

    @Override
    public ResponseDto<?> detailMovie(Long id, HttpServletRequest request){

        //Optional<Video> detailvideo = videoRepository.findById(id);
        Video video = verifyVideo(id);
        if (video == null){

            ResponseDto.fail("해당 영화가 없습니다.");

        }

    return ResponseDto.success(

                VideoResponseDto.builder()

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
                        .build()

        );



    }

    @Transactional(readOnly = true)
    public Video verifyVideo(Long id) {
        Optional<Video> verifyVideo = videoRepository.findById(id);
        return verifyVideo.orElse(null);
    }




    @Override
    public LargeCategoryDto getTvShow(HttpServletRequest request) {


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

    @Override
    public LargeCategoryDto getRandomShow() {
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

    @Override
    public LargeCategoryDto findMovieToSmallCategory(String movie, String smallcategory) {
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
                video.getRelease_date(), video.getVote_average(), video.getYoutubePath(), video.getBackdrop_path(), video.getHomepage(), video.getLikeCnt());
        return newVideoResponseDto;
    }

    @Override
    public MovieDto findmoviegenre() {
        List<String> moivegenre = new ArrayList<>();

        for (MovieGenre genre : MovieGenre.values()){
            moivegenre.add(genre.getGenreName());
        }
        return new MovieDto(moivegenre);
    }

    @Override
    public TVDto findTVgenre() {
        List<String> tvgenre = new ArrayList<>();

        for (DramaGenre genre : DramaGenre.values()){
            tvgenre.add(genre.getGenreName());
        }
        return new TVDto(tvgenre);
    }

}
