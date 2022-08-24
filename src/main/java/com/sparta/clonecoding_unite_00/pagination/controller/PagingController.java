package com.sparta.clonecoding_unite_00.pagination.controller;

import com.sparta.clonecoding_unite_00.pagination.service.PagingService;
import com.sparta.clonecoding_unite_00.utils.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PagingController {

    @Autowired
    private PagingService pagingService;

    @GetMapping("/video/pagination")
    public ResponseDto<?> getPaging(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc

    ) {
        page = page - 1;
        return pagingService.getPagenation(page, size,sortBy,isAsc);
    }


}
