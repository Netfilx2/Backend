package com.sparta.clonecoding_unite_00.imageupload.repository;

import com.sparta.clonecoding_unite_00.imageupload.domain.Images;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepository extends JpaRepository<Images, Long> {
}
