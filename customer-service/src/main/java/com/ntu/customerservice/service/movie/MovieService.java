package com.ntu.customerservice.service.movie;

import com.ntu.common.util.CaffeineCacheUtil;
import com.ntu.common.util.DateUtil;
import com.ntu.moviecore.domain.movie.dto.MovieResponse;
import com.ntu.moviecore.domain.movie.dto.MovieShowtimeResponse;
import com.ntu.moviecore.domain.movie.entity.Showtime;
import com.ntu.moviecore.domain.movie.repository.MovieRepository;
import com.ntu.moviecore.domain.movie.repository.ShowtimeRepository;
import com.ntu.moviecore.domain.theater.entity.Theater;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    private final ShowtimeRepository showtimeRepository;

//    private final MovieElasticSearchRepository movieEsRepository;

    private final ModelMapper modelMapper;

    public List<MovieShowtimeResponse> getScheduledMovies(Long theaterId) {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfDay = now.atStartOfDay();
        LocalDateTime endOfDay = now.plusDays(7).atTime(LocalTime.MAX);

        List<Showtime> moviesShowtime = showtimeRepository.findMoviesShowtimeByRange(theaterId, startOfDay, endOfDay);

        Map<String, MovieShowtimeResponse> dateShowtimeToMovies = new HashMap<>();
        for (Showtime showtime : moviesShowtime) {
            String dateTime = DateUtil.formatVNFull(showtime.getStartTime());
            MovieResponse movie = modelMapper.map(showtime.getMovie(), MovieResponse.class);
            MovieShowtimeResponse exit = dateShowtimeToMovies.get(dateTime);
            if (exit != null) {
                exit.getMovies().add(movie);
            } else {
                List<MovieResponse> movies = new ArrayList<>();
                movies.add(movie);
                MovieShowtimeResponse showtimeResponse = new MovieShowtimeResponse();
                showtimeResponse.setDateShowing(dateTime);
                showtimeResponse.setShowtimeId(showtime.getId());
                showtimeResponse.setMovies(movies);
                dateShowtimeToMovies.put(dateTime, showtimeResponse);
            }
        }

        return dateShowtimeToMovies.values().stream().toList();
    }

    /**
     * Retrieves the list of movies that are showing today.
     * The time range is from the start of the day (00:00) to the end of the day (23:59:59.999).
     *
     * @return a list of movies showing today
     */
    public List<MovieResponse> getMovieShowToday(Long theaterId) {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfDay = now.atStartOfDay(); // 00:00 today
        LocalDateTime endOfDay = now.atTime(LocalTime.MAX); // 23:59:59.999 today
        return getMovieByRangeDate(theaterId, startOfDay, endOfDay);
    }

    /**
     * Retrieves the list of upcoming movies.
     * The time range is from tomorrow (00:00) to 7 days after tomorrow (23:59:59.999).
     *
     * @return a list of upcoming movies in the next 7 days starting from tomorrow
     */
    public List<MovieResponse> getMovieComingSoon(Long theaterId) {
        LocalDate tomorrow = LocalDate.now().plusDays(1); // Tomorrow's date
        LocalDateTime startOfDay = tomorrow.atStartOfDay(); // 00:00 tomorrow
        LocalDateTime endOfDay = tomorrow.plusDays(7).atTime(LocalTime.MAX); // 23:59:59.999 seven days later
        return getMovieByRangeDate(theaterId, startOfDay, endOfDay);
    }

    public List<MovieResponse> getMovieScheduled(Long theaterId) {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfDay = now.atStartOfDay();
        LocalDateTime endOfDay = now.plusDays(7).atTime(LocalTime.MAX);
        return getMovieByRangeDate(theaterId, startOfDay, endOfDay);
    }

    /**
     * Retrieves a list of movies that have showtimes within the specified date and time range.
     *
     * @param dateFrom the start datetime of the range
     * @param dateTo   the end datetime of the range
     * @return a list of movies that are scheduled between the given datetime range
     */
    public List<MovieResponse> getMovieByRangeDate(Long theaterId, LocalDateTime dateFrom, LocalDateTime dateTo) {
        return movieRepository.findMoviesByShowtimeBetween(theaterId, dateFrom, dateTo)
                .stream()
                .map(movie -> {
                    MovieResponse resp = modelMapper.map(movie, MovieResponse.class);
                    resp.setReleaseDate(DateUtil.localDateToString(movie.getReleaseDate()));
                    return resp;
                })
                .toList();
    }

}
