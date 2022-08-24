package com.sparta.clonecoding_unite_00.favorite.controller;

import com.sparta.clonecoding_unite_00.favorite.service.FavoriteService;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class FavoriteController {

    private final FavoriteService favoriteService;


    // /auth/favorite 좋아요한 영상 조회 GET

    @RequestMapping(value = "/auth/favorite", method = RequestMethod.GET)
    public ResponseDto<?> getFavorite(HttpServletRequest request) {
        return favoriteService.getFavorite(request);
    }

    // /auth/{movieId}/favorite 좋아요 추가 삭제 POST

    @RequestMapping(value = "/auth/{movieId}/favorite", method = RequestMethod.POST)
    public ResponseDto<?> addFavorite(@PathVariable Long movieId, HttpServletRequest request) {
        return favoriteService.addFavorite(movieId,request);
    }




}
