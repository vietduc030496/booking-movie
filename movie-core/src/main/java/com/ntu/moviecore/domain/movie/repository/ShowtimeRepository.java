package com.ntu.moviecore.domain.movie.repository;

import com.ntu.moviecore.domain.movie.dto.ShowtimeTheaterDbResponse;
import com.ntu.moviecore.domain.movie.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    @Query("SELECT st FROM Showtime st " +
            "WHERE st.room.theater.id = :theaterId " +
            "AND st.startTime BETWEEN :start AND :end")
    List<Showtime> findMoviesShowtimeByRange(@Param("theaterId") long theaterId,
                                             @Param("start") LocalDateTime start,
                                             @Param("end") LocalDateTime end);

    @Query(value = """
            SELECT tm.id AS movieId, tm.title AS movieName, 
                   tt.id AS theaterId, tt.name AS theaterName, 
                   ts.id AS timeshowId, ts.start_time AS startTime, 
                   tr.id AS roomId, tr.name AS roomName 
            FROM tbl_showtime ts 
            JOIN tbl_movie tm ON ts.movie_id = tm.id 
            JOIN tbl_room tr ON ts.room_id = tr.id 
            JOIN tbl_theater tt ON tr.theater_id = tt.id 
            WHERE tm.id = :movieId 
            AND tt.id = :theaterId 
            AND ts.start_time BETWEEN :start AND :end """, nativeQuery = true)
    List<ShowtimeTheaterDbResponse> findShowtimeByMovieIdAndTheaterId(@Param("movieId")long movieId,
                                                                      @Param("theaterId") long theaterId,
                                                                      @Param("start") LocalDateTime start,
                                                                      @Param("end") LocalDateTime end);
}
