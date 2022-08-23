package com.sparta.clonecoding_unite_00.likes.controller;


import com.sparta.clonecoding_unite_00.likes.service.LikesService;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class LikesController {
    private final LikesService likesService;


    @PostMapping("/auth/movie/{movieId}/like")
    public ResponseDto<?> addLike(@PathVariable Long movieId, HttpServletRequest request){
        return likesService.addLike(movieId,request);

    }
}
