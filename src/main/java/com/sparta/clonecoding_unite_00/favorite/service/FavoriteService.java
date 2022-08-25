package com.sparta.clonecoding_unite_00.favorite.service;

import com.sparta.clonecoding_unite_00.favorite.refactoring.FavoriteRefactoring;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Service
public class FavoriteService {

    private final FavoriteRefactoring refactoring;

    @Transactional
    public ResponseDto<?> getFavorite(HttpServletRequest request) {

       return refactoring.getFavoriteMethod(request);

    }

    @Transactional
    public ResponseDto<?> addFavorite(Long movieId, HttpServletRequest request) {

        return refactoring.favoriteAddMethod(movieId,request);

    }



}
