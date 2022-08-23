package com.sparta.clonecoding_unite_00.movie.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class VideoSmallCategory {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "videoId")
    private Video video;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "smallCategoryId")
    private SmallCategory smallCategory;

    public VideoSmallCategory(Video video, SmallCategory smallCategory) {
        this.video = video;
        this.smallCategory = smallCategory;
    }
}
