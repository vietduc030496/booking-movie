package com.ntu.customerservice.service.movie;

import com.ntu.common.util.DateUtil;
import com.ntu.moviecore.domain.elasticsearch.repository.MovieElasticSearchRepository;
import com.ntu.moviecore.domain.movie.dto.MovieResponse;
import com.ntu.moviecore.domain.movie.repository.MovieRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

//    private final MovieElasticSearchRepository movieEsRepository;

    private final ModelMapper modelMapper;

    /**
     * Retrieves the list of movies that are showing today.
     * The time range is from the start of the day (00:00) to the end of the day (23:59:59.999).
     *
     * @return a list of movies showing today
     */
    public List<MovieResponse> getMovieShowToday() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay(); // 00:00 today
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX); // 23:59:59.999 today
        return getMovieByRangeDate(startOfDay, endOfDay);
    }

    /**
     * Retrieves the list of upcoming movies.
     * The time range is from tomorrow (00:00) to 7 days after tomorrow (23:59:59.999).
     *
     * @return a list of upcoming movies in the next 7 days starting from tomorrow
     */
    public List<MovieResponse> getMovieComingSoon() {
        LocalDate tomorrow = LocalDate.now().plusDays(1); // Tomorrow's date
        LocalDateTime startOfDay = tomorrow.atStartOfDay(); // 00:00 tomorrow
        LocalDateTime endOfDay = tomorrow.plusDays(7).atTime(LocalTime.MAX); // 23:59:59.999 seven days later
        return getMovieByRangeDate(startOfDay, endOfDay);
    }

    /**
     * Retrieves a list of movies that have showtimes within the specified date and time range.
     *
     * @param dateFrom the start datetime of the range
     * @param dateTo   the end datetime of the range
     * @return a list of movies that are scheduled between the given datetime range
     */
    public List<MovieResponse> getMovieByRangeDate(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return movieRepository.findMoviesByShowtimeBetween(dateFrom, dateTo)
                .stream()
                .map(movie -> {
                    MovieResponse resp = modelMapper.map(movie, MovieResponse.class);
                    resp.setReleaseDate(DateUtil.localDateToString(movie.getReleaseDate()));
                    return resp;
                })
                .toList();
    }

}
