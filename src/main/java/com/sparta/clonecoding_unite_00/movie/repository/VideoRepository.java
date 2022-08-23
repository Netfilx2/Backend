package com.sparta.clonecoding_unite_00.movie.repository;
import com.sparta.clonecoding_unite_00.movie.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
