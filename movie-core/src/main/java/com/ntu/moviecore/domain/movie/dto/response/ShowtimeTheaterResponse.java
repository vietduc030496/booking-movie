package com.ntu.moviecore.domain.movie.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShowtimeTheaterResponse {
    private String dayTime;

    private List<ShowtimeResponse> showtimes;
}
