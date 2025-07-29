package com.ntu.moviecore.domain.movie.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponse {
    private long id;

    private String title;

    private String description;

    private int runningTime;

    private String genre;

    private String ageRating;

    private String posterUrl;

    private String trailerUrl;

    private String releaseDate;
}
