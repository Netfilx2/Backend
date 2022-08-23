package com.sparta.clonecoding_unite_00.movie.dto.categoryDto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LargeCategoryDto{
    private String largecategory;
    private List<SmallCategoryDto> datainfo;
}
