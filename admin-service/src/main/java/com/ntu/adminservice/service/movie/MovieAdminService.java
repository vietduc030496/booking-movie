package com.ntu.adminservice.service.movie;

import com.ntu.common.exception.ResourceNotFoundException;
import com.ntu.common.util.CaffeineCacheUtil;
import com.ntu.common.util.DateUtil;
import com.ntu.moviecore.domain.elasticsearch.entity.MovieElasticSearch;
import com.ntu.moviecore.domain.elasticsearch.repository.MovieElasticSearchRepository;
import com.ntu.moviecore.domain.movie.dto.request.MovieNewRequest;
import com.ntu.moviecore.domain.movie.dto.request.MovieUpdateRequest;
import com.ntu.moviecore.domain.movie.dto.response.MovieResponse;
import com.ntu.moviecore.domain.movie.entity.Movie;
import com.ntu.moviecore.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;

import static com.ntu.common.constant.CacheAttributeKeyConstant.GENRES;

@Service
@RequiredArgsConstructor
public class MovieAdminService {

    private static final String GENRE_SEPARATOR = ",";

    private static final List<String> DEFAULT_GENRES = List.of("Hài hước", "Kinh dị", "Hành động");

    private final MovieRepository movieRepository;

    private final MovieElasticSearchRepository movieSearchRepository;

    private final ModelMapper modelMapper;

    /**
     * @param start   first release date, {@code null} for the first day of the current month
     * @param end     last release date, {@code null} for the last day of the current month
     * @param keyword part of the title (case-insensitive), blank for every movie
     */
    @Transactional(readOnly = true)
    public List<MovieResponse> getMovieByRange(LocalDate start, LocalDate end, String keyword) {
        LocalDate from = start == null ? LocalDate.now().withDayOfMonth(1) : start;
        LocalDate to = end == null ? LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()) : end;

        String normalizedKeyword = StringUtils.isBlank(keyword) ? null : keyword.trim().toLowerCase(Locale.ROOT);

        return movieRepository.findMoviesByReleaseDateBetween(from, to)
                .stream()
                .filter(movie -> normalizedKeyword == null || titleContains(movie, normalizedKeyword))
                .map(this::toResponse)
                .toList();
    }

    private static boolean titleContains(Movie movie, String lowerCaseKeyword) {
        return movie.getTitle() != null && movie.getTitle().toLowerCase(Locale.ROOT).contains(lowerCaseKeyword);
    }

    /**
     * All movies, newest release first. Used to fill the movie selection of the admin forms.
     */
    @Transactional(readOnly = true)
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll(Sort.by(Sort.Direction.DESC, "releaseDate"))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * @throws ResourceNotFoundException when the movie does not exist
     */
    @Transactional(readOnly = true)
    public MovieResponse getMovieById(long movieId) {
        return movieRepository.findById(movieId)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", movieId));
    }

    /**
     * @throws ResourceNotFoundException when the movie does not exist
     */
    @Transactional
    public void updateMovie(long movieId, MovieUpdateRequest request) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie", movieId));
        saveMovie(movie, request);
    }

    @Transactional
    public void addNewMovie(MovieNewRequest request) {
        saveMovie(new Movie(), request);
    }

    public void syncAllMoviesToElasticsearch() {
        List<MovieElasticSearch> docs = movieRepository.findAll()
                .stream()
                .map(m -> modelMapper.map(m, MovieElasticSearch.class))
                .toList();

        movieSearchRepository.saveAll(docs);
    }

    public List<String> getAllGenres() {
        List<?> cached = CaffeineCacheUtil.get(GENRES, List.class);
        if (cached != null) {
            return cached.stream().map(String::valueOf).toList();
        }
        CaffeineCacheUtil.put(GENRES, DEFAULT_GENRES);
        return DEFAULT_GENRES;
    }

    private void saveMovie(Movie movie, MovieNewRequest request) {
        movie.setTitle(request.getTitle().trim());
        movie.setPosterUrl(request.getPosterUrl());
        movie.setTrailerUrl(request.getTrailerUrl());
        movie.setRunningTime(request.getRunningTime());
        movie.setGenre(String.join(GENRE_SEPARATOR, request.getGenre()));
        movie.setReleaseDate(request.getReleaseDate());
        movie.setAgeRating(request.getAgeRating());
        movie.setDescription(request.getDescription());
        Movie saved = movieRepository.save(movie);

        movieSearchRepository.save(modelMapper.map(saved, MovieElasticSearch.class));
    }

    private MovieResponse toResponse(Movie movie) {
        MovieResponse response = modelMapper.map(movie, MovieResponse.class);
        response.setReleaseDate(DateUtil.localDateToString(movie.getReleaseDate()));
        return response;
    }
}
