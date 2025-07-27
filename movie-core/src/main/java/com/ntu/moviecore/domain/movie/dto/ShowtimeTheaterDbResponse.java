package com.ntu.moviecore.domain.movie.dto;

import java.time.LocalDateTime;

public interface ShowtimeTheaterDbResponse {

    long getMovieId();

    String getMovieName();

    long getTheaterId();

    String getTheaterName();

    long getTimeShowId();

    LocalDateTime getStartTime();

    long getRoomId ();

    String getRoomName();

}
