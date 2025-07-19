package com.ntu.moviecore.domain.movie.repository;

import com.ntu.moviecore.domain.movie.entity.Movie;
import com.ntu.moviecore.domain.movie.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    @Query("SELECT st FROM Showtime st " +
            "WHERE st.startTime BETWEEN :start AND :end")
    List<Showtime> findMoviesShowtimeByRange(@Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end);
}
