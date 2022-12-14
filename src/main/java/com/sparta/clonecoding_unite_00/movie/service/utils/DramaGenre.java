package com.sparta.clonecoding_unite_00.movie.service.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DramaGenre { // 12개 enum값.
    Action_Adventure(10759, "Action"),
    Animation(16, "Animation"),
    Comedy(35, "Comedy"),
    Crime(80, "Crime"),
    Documentary(99, "Documentary"),
    Drama(18, "Drama"),
    Family(10751, "Family"),
    Kids(10762, "Kids"),
    Mystery(9648, "Mystery"),
    ScienceFiction(10765, "ScienceFiction"),
    War(10768, "War"),
    Western(37, "Western");

    private final int value;
    private final String GenreName;

}
