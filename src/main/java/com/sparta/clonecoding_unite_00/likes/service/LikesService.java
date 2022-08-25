package com.sparta.clonecoding_unite_00.likes.service;

import com.sparta.clonecoding_unite_00.likes.refactoring.LikesRefactoring;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRefactoring refactoring;

    @Transactional
    public ResponseDto<?> addLike(Long movieId, HttpServletRequest request) {
       return refactoring.addLikeRefactoring(movieId,request);
    }


}
