package com.ntu.moviecore.domain.movie.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieShowtimeResponse {

    private long showtimeId;

    private String dateShowing;

    private List<MovieResponse> movies;

}
