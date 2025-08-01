package com.ntu.moviecore.domain.movie.dto.request;

import com.ntu.moviecore.domain.movie.entity.AgeRating;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MovieUpdateRequest {
    private String title;
    private String posterUrl;
    private String runningTime;
    private List<String> genre;
    private String trailerUrl;
    private LocalDate releaseDate;
    private AgeRating ageRating;
    private String description;
}
