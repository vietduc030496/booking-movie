package com.ntu.customerservice.service.movie;

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

    public List<MovieResponse> getMovieShowToday() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return getMovieByRangeDate(startOfDay, endOfDay);
    }

    public List<MovieResponse> getMovieByRangeDate(LocalDateTime dateFrom, LocalDateTime dateTo) {
        ModelMapper modelMapper = new ModelMapper();
        return movieRepository.findMoviesByShowtimeBetween(dateFrom, dateTo)
                .stream()
                .map(movie -> modelMapper.map(movie, MovieResponse.class))
                .toList();
    }

}
