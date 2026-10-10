package com.ntu.moviecore.domain.movie.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowtimeAdminResponse {

    private long showtimeId;

    private long movieId;

    private String movieTitle;

    private long theaterId;

    private String theaterName;

    private long roomId;

    private String roomName;

    private String startTime;

    private String endTime;
}
