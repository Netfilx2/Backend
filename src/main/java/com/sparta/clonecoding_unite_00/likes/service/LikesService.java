package com.sparta.clonecoding_unite_00.likes.service;

import com.sparta.clonecoding_unite_00.jwt.TokenProvider;
import com.sparta.clonecoding_unite_00.likes.domain.Likes;
import com.sparta.clonecoding_unite_00.likes.repository.LikesRepository;
import com.sparta.clonecoding_unite_00.member.doamin.Member;
import com.sparta.clonecoding_unite_00.member.repository.MemberRepository;
import com.sparta.clonecoding_unite_00.movie.domain.Video;
import com.sparta.clonecoding_unite_00.movie.dto.videoDto.VideoResponseDto;
import com.sparta.clonecoding_unite_00.movie.repository.VideoRepository;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public ResponseDto<?> addLike(Long movieId, HttpServletRequest request) {
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

        if(likesRepository.existsByVideo_IdAndMember_Id(video.getId(), member.getId())){
            likesRepository.deleteByVideo_IdAndMember_Id(video.getId(), member.getId());
            Integer likeCnt = likesRepository.countAllByVideo(video);
            video.likeCnt(likeCnt);
            videoRepository.save(video);
            return ResponseDto.success(
                    VideoResponseDto.builder()
                            .id(video.getId())
                            .title(video.getTitle())
                            .poster_path(video.getPosterPath())
                            .overview(video.getOverview())
                            .first_date(video.getRelease_date())
                            .grade(video.getPopularity())
                            .youtubePath(video.getYoutubePath())
                            .backdrop_path(video.getBackdrop_path())
                            .homepage(video.getHomepage())
                            .likeCnt(video.getLikeCnt())
                            .build()
            );
        }

        Likes likes = new Likes(member, video);
        likesRepository.save(likes);

        Integer likeCnt = likesRepository.countAllByVideo(video);
//        Video videoCnt = new Video(likeCnt);
        video.likeCnt(likeCnt);
        videoRepository.save(video);

        return ResponseDto.success(
                VideoResponseDto.builder()
                        .id(video.getId())
                        .title(video.getTitle())
                        .poster_path(video.getPosterPath())
                        .overview(video.getOverview())
                        .first_date(video.getRelease_date())
                        .grade(video.getPopularity())
                        .youtubePath(video.getYoutubePath())
                        .backdrop_path(video.getBackdrop_path())
                        .homepage(video.getHomepage())
                        .likeCnt(video.getLikeCnt())
                        .build()
        );
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
