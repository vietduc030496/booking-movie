package com.ntu.moviecore.domain.movie.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShowtimeResponse {

    private long showtimeId;

    private String startHour;

    private long roomId;

    private String roomName;
}
