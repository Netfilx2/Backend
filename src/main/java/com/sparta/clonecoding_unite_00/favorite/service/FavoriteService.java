package com.sparta.clonecoding_unite_00.favorite.service;

import com.sparta.clonecoding_unite_00.favorite.domain.Favorite;
import com.sparta.clonecoding_unite_00.favorite.repository.FavoriteRepository;
import com.sparta.clonecoding_unite_00.jwt.TokenProvider;
import com.sparta.clonecoding_unite_00.member.doamin.Member;
import com.sparta.clonecoding_unite_00.movie.domain.Video;
import com.sparta.clonecoding_unite_00.movie.dto.videoDto.VideoResponseDto;
import com.sparta.clonecoding_unite_00.movie.repository.VideoRepository;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    private final TokenProvider tokenProvider;

    private final VideoRepository videoRepository;

    @Transactional
    public ResponseDto<?> getFavorite(HttpServletRequest request) {


        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("Authorization이 없습니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        List<Favorite> favorites = favoriteRepository.findAllByMember_Id(member.getId());

        ArrayList<VideoResponseDto> favoriteArrayList = new ArrayList<>();

        for (Favorite favorite : favorites){
            favoriteArrayList.add(
                    VideoResponseDto.builder()
                            .id(favorite.getVideo().getId())
                            .title(favorite.getVideo().getTitle())
                            .poster_path(favorite.getVideo().getPosterPath())
                            .overview(favorite.getVideo().getOverview())
                            .first_date(favorite.getVideo().getRelease_date())
                            .grade(favorite.getVideo().getPopularity())
                            .youtubePath(favorite.getVideo().getYoutubePath())
                            .backdrop_path(favorite.getVideo().getBackdrop_path())
                            .homepage(favorite.getVideo().getHomepage())
                            .likeCnt(favorite.getVideo().getLikeCnt())
                            .build()
            );
        }

       return ResponseDto.success(favoriteArrayList);


    }


    @Transactional
    public ResponseDto<?> addFavorite(Long movieId, HttpServletRequest request) {

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("Authorization이 없습니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("Token이 유효하지 않습니다.");
        }

        Video video = validateVideo(movieId);
        if(null == video){
            return ResponseDto.fail("영상이 없습니다.");
        }

        if(favoriteRepository.existsByVideo_IdAndMember_Id(video.getId(), member.getId())) {
            favoriteRepository.deleteByVideo_IdAndMember_Id(video.getId(), member.getId());

            return ResponseDto.successToMessage(200,"찜하기를 취소 하셨습니다.",false);

        }


        Favorite favorite = new Favorite(member, video);

        favoriteRepository.save(favorite);

        return ResponseDto.successToMessage(200,"찜하기를 누르셨습니다.",true);

    }

    //복호화
    @Transactional
    public Member validateMember(HttpServletRequest request) {
//        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
//            return null;
//        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Video validateVideo(Long id) {
        Optional<Video> validateVideo = videoRepository.findById(id);
        return validateVideo.orElse(null);
    }



}
