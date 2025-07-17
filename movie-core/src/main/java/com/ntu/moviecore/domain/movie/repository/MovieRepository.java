package com.ntu.moviecore.domain.movie.repository;

import com.ntu.moviecore.domain.movie.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT DISTINCT st.movie FROM Showtime st " +
            "WHERE st.startTime BETWEEN :start AND :end")
    List<Movie> findMoviesByShowtimeBetween(@Param("start") LocalDateTime start,
                                            @Param("end") LocalDateTime end);
}
