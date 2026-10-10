package com.ntu.moviecore.domain.movie.repository;

import com.ntu.moviecore.domain.movie.dto.response.ShowtimeTheaterDbResponse;
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
            AND ts.start_time BETWEEN :start AND :end 
            ORDER BY ts.start_time """, nativeQuery = true)
    List<ShowtimeTheaterDbResponse> findShowtimeByMovieIdAndTheaterId(@Param("movieId")long movieId,
                                                                      @Param("theaterId") long theaterId,
                                                                      @Param("start") LocalDateTime start,
                                                                      @Param("end") LocalDateTime end);

    @Query("SELECT st FROM Showtime st " +
            "JOIN FETCH st.movie " +
            "JOIN FETCH st.room r " +
            "JOIN FETCH r.theater t " +
            "WHERE (:theaterId IS NULL OR t.id = :theaterId) " +
            "AND st.startTime >= :start AND st.startTime < :end " +
            "ORDER BY st.startTime, r.name")
    List<Showtime> findAdminShowtimes(@Param("theaterId") Long theaterId,
                                      @Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end);

    /**
     * Two showtimes in the same room overlap when each one starts before the other one ends.
     */
    @Query("SELECT COUNT(st) > 0 FROM Showtime st " +
            "WHERE st.room.id = :roomId " +
            "AND st.startTime < :end " +
            "AND st.endTime > :start")
    boolean existsOverlappingShowtime(@Param("roomId") long roomId,
                                      @Param("start") LocalDateTime start,
                                      @Param("end") LocalDateTime end);
}
