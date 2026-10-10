package com.ntu.moviecore.application.movie.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShowtimeNewRequest {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";

    @NotNull
    private Long movieId;

    @NotNull
    private Long roomId;

    @NotNull
    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime startTime;

    /**
     * Optional. When empty, it is calculated from the movie running time.
     */
    @DateTimeFormat(pattern = DATE_TIME_PATTERN)
    private LocalDateTime endTime;
}
