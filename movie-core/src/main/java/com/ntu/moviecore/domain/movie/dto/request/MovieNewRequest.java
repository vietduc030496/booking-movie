package com.ntu.moviecore.domain.movie.dto.request;

import com.ntu.moviecore.domain.movie.entity.AgeRating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MovieNewRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String posterUrl;

    @NotNull
    @Positive
    private Integer runningTime;

    @NotEmpty
    private List<String> genre;

    @NotBlank
    private String trailerUrl;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    @NotNull
    private AgeRating ageRating;

    @NotBlank
    private String description;
}
