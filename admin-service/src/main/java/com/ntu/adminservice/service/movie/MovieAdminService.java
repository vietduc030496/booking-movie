package com.ntu.adminservice.service.movie;

import com.ntu.common.util.DateUtil;
import com.ntu.moviecore.domain.elasticsearch.entity.MovieElasticSearch;
import com.ntu.moviecore.domain.elasticsearch.repository.MovieElasticSearchRepository;
import com.ntu.moviecore.domain.movie.dto.request.MovieNewRequest;
import com.ntu.moviecore.domain.movie.dto.response.MovieResponse;
import com.ntu.moviecore.domain.movie.entity.Movie;
import com.ntu.moviecore.domain.movie.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieAdminService {
    private final MovieRepository movieRepository;

    private final MovieElasticSearchRepository movieSearchRepository;

    private final ModelMapper modelMapper;

    public List<MovieResponse> getMovieByRange(LocalDate start, LocalDate end) {
        start = start == null ? LocalDate.now().withDayOfMonth(1) : start;
        end = end == null ? LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()) : end;

        return movieRepository.findMoviesByReleaseDateBetween(start, end)
                .stream()
                .map(movie -> {
                    MovieResponse resp = modelMapper.map(movie, MovieResponse.class);
                    resp.setReleaseDate(DateUtil.localDateToString(movie.getReleaseDate()));
                    return resp;
                })
                .toList();
    }

    public MovieResponse getMovieById(Long movieId) {
        Optional<Movie> optional = movieRepository.findById(movieId);
        if (optional.isEmpty()) {
            return null;
        }
        Movie movie = optional.get();
        MovieResponse resp = modelMapper.map(movie, MovieResponse.class);
        resp.setReleaseDate(DateUtil.localDateToString(movie.getReleaseDate()));
        return resp;
    }

    @Transactional
    public void addNewMovie(MovieNewRequest movieNewRequest) {
        Movie newMovie = modelMapper.map(movieNewRequest, Movie.class);
        movieRepository.save(newMovie);
    }


    public void syncAllMoviesToElasticsearch() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieElasticSearch> docs = movies.stream()
                .map(m -> modelMapper.map(m, MovieElasticSearch.class))
                .toList();

        movieSearchRepository.saveAll(docs);
    }
}
