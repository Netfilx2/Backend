package com.sparta.clonecoding_unite_00.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.clonecoding_unite_00.movie.dto.genreDto.drama.TheDramaApiResponseResultList;
import com.sparta.clonecoding_unite_00.movie.dto.genreDto.movie.TheMovieApiResponseResultList;
import com.sparta.clonecoding_unite_00.movie.dto.videoDto.VideoListResult;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Boolean adult;
    @Column(nullable = false)
    private String title;
    @Column
    private String original_language;
    @Column
    private String original_title;
    @Column( length = 100000 )
    private String overview;
    @Column
    private Float popularity;
    @Column
    private String posterPath;
    @Column
    private String release_date;
    @Column
    private String youtubePath;
    @Column
    private Float vote_average;
    @Column
    private Long vote_count;
    @Column
    private String homepage;

    @Column
    private String backdrop_path;

    @Column
    @ColumnDefault("0")
    private int likeCnt;

    public void likeCnt(Integer likeCnt){
        this.likeCnt = likeCnt;
    }

    @OneToMany(mappedBy = "video",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<VideoSmallCategory> videoSmallCategoryList = new ArrayList<>();

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "largeCategoryId")
    private LargeCategory largeCategory;




    public Video(TheMovieApiResponseResultList movie, VideoListResult youtube, LargeCategory largeCategory, String homepage) {
        this.adult = movie.getAdult();
        this.title = movie.getTitle();
        this.original_language = movie.getOriginal_language();
        this.original_title = movie.getOriginal_title();
        this.overview = movie.getOverview();
        this.popularity = movie.getPopularity();
        this.posterPath = "https://image.tmdb.org/t/p/w500"+movie.getPoster_path();
        this.release_date = movie.getRelease_date();

        if(youtube == null)
            this.youtubePath = null;
        else
            this.youtubePath = "https://www.youtube.com/embed/"+youtube.getKey()+"?autoplay=1&mute=1";

        this.vote_average = movie.getVote_average();
        this.vote_count = movie.getVote_count();
        this.largeCategory = largeCategory;

        this.backdrop_path = "https://image.tmdb.org/t/p/w500"+movie.getBackdrop_path();
        this.homepage = homepage;
    }

    public Video(TheDramaApiResponseResultList drama, VideoListResult youtube, LargeCategory largeCategory, String homepage) {
        this.title = drama.getName();
        this.original_language = drama.getOriginal_language();
        this.original_title = drama.getOriginal_name();
        this.overview = drama.getOverview();
        this.popularity = drama.getPopularity();
        this.posterPath = "https://image.tmdb.org/t/p/w500"+drama.getPoster_path();
        this.release_date = drama.getFirst_air_date();

        if(youtube == null)
            this.youtubePath = null;
        else
            this.youtubePath = "https://www.youtube.com/embed/"+youtube.getKey()+"?autoplay=1&mute=1";

        this.vote_average = drama.getVote_average();
        this.vote_count = drama.getVote_count();
        this.largeCategory = largeCategory;

        this.backdrop_path = "https://image.tmdb.org/t/p/w500"+drama.getBackdrop_path();
        this.homepage =homepage;
    }





}
