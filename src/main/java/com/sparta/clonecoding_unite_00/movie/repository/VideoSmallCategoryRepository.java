package com.sparta.clonecoding_unite_00.movie.repository;



import com.sparta.clonecoding_unite_00.movie.domain.VideoSmallCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
// @Repository 이거 안써도 된다.

public interface VideoSmallCategoryRepository extends JpaRepository<VideoSmallCategory, Long> {
    List<VideoSmallCategory> findAllByVideoId(Long videoid);
}
