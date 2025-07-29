package com.ntu.moviecore.domain.movie.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovieNewRequest {
    private String title;
    private String posterUrl;
    private String runningTime;
    private String genre;
    private String trailerUrl;
    private LocalDate releaseDate;
    private String description;
}
