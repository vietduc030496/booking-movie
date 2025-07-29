package com.ntu.adminservice.service.movie;

import com.ntu.moviecore.domain.elasticsearch.entity.MovieElasticSearch;
import com.ntu.moviecore.domain.elasticsearch.repository.MovieElasticSearchRepository;
import com.ntu.moviecore.domain.movie.dto.request.MovieNewRequest;
import com.ntu.moviecore.domain.movie.entity.Movie;
import com.ntu.moviecore.domain.movie.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieAdminService {
    private final MovieRepository movieRepository;

    private final MovieElasticSearchRepository movieSearchRepository;

    private final ModelMapper modelmapper;

    public Movie getMovieByRange(LocalDateTime startOfDay, LocalDateTime endOfDay) {
        return null;
    }

    @Transactional
    public void addNewMovie(MovieNewRequest movieNewRequest) {
        Movie newMovie = modelmapper.map(movieNewRequest, Movie.class);
        movieRepository.save(newMovie);
    }


    public void syncAllMoviesToElasticsearch() {
        List<Movie> movies = movieRepository.findAll();
        List<MovieElasticSearch> docs = movies.stream()
                .map(m -> modelmapper.map(m, MovieElasticSearch.class))
                .toList();

        movieSearchRepository.saveAll(docs);
    }
}
