package com.ntu.adminservice.service.movie;

import com.ntu.moviecore.domain.movie.dto.request.MovieNewRequest;
import com.ntu.moviecore.domain.movie.entity.Movie;
import com.ntu.moviecore.domain.movie.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieAdminService {
    private final MovieRepository movieRepository;

    private final ModelMapper modelmapper;

    @Transactional
    public void addNewMovie(MovieNewRequest movieNewRequest) {
        Movie newMovie = modelmapper.map(movieNewRequest, Movie.class);
        movieRepository.save(newMovie);
    }
}
