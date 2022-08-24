package com.sparta.clonecoding_unite_00.movie.dto.serchDto;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;


@Getter
@Setter
public class MovieSearchResponseDto {

    String title;
    String link;
    String image;
    String subtitle;
    String pubDate;
    String director;
    String actor;
    String userRating;


    public MovieSearchResponseDto(JSONObject jsonObject) {

        this.title = jsonObject.getString("title");
        this.link = jsonObject.getString("link");
        this.image = jsonObject.getString("image");
        this.subtitle = jsonObject.getString("subtitle");
        this.pubDate = jsonObject.getString("pubDate");
        this.director = jsonObject.getString("director");
        this.actor = jsonObject.getString("actor");
        this.userRating = jsonObject.getString("userRating");


    }


}
