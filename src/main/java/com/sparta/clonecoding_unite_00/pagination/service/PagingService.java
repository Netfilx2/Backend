package com.sparta.clonecoding_unite_00.pagination.service;

import com.sparta.clonecoding_unite_00.movie.domain.Video;
import com.sparta.clonecoding_unite_00.movie.dto.videoDto.VideoResponseDto;
import com.sparta.clonecoding_unite_00.movie.repository.VideoRepository;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PagingService {

    @Autowired
    private final VideoRepository videoRepository;

    @Transactional
    public ResponseDto<?> getPagenation(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size,sort);
        Page<Video> videos = videoRepository.findAll(pageable);

        List<VideoResponseDto> videoResponseDtoList = new ArrayList<>();

        for (Video video : videos){

            videoResponseDtoList.add(
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

        return ResponseDto.success(videoResponseDtoList);

    }


}
