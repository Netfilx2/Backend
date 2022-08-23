package com.sparta.clonecoding_unite_00.likes.repository;

import com.sparta.clonecoding_unite_00.likes.domain.Likes;
import com.sparta.clonecoding_unite_00.movie.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Integer countAllByVideo(Video video);
    Boolean existsByVideo_IdAndMember_Id(Long unlikeVideoId,Long unlikeMemberId);

    void deleteByVideo_IdAndMember_Id(Long unlikeVideoId,Long unlikeMemberId);
}
