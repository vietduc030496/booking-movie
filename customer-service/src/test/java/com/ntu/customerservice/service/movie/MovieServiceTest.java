package com.ntu.customerservice.service.movie;

import com.ntu.common.util.DateUtil;
import com.ntu.moviecore.domain.authentication.entity.User;
import com.ntu.moviecore.domain.movie.dto.response.MovieResponse;
import com.ntu.moviecore.domain.movie.dto.response.MovieShowtimeResponse;
import com.ntu.moviecore.domain.movie.entity.AgeRating;
import com.ntu.moviecore.domain.movie.entity.Movie;
import com.ntu.moviecore.domain.movie.entity.Showtime;
import com.ntu.moviecore.domain.movie.repository.MovieRepository;
import com.ntu.moviecore.domain.movie.repository.ShowtimeRepository;
import com.ntu.moviecore.domain.theater.entity.Room;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ShowtimeRepository showtimeRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MovieService movieService;

    @Test
    void get_schedule_movies_success_and_exist_movies() {
        // Arrange
        long theaterId = 1L;

        Movie firstMovieDump = new Movie();
        firstMovieDump.setId(1L);
        firstMovieDump.setGenre("Action 1");
        firstMovieDump.setTitle("Movie Title 1");
        firstMovieDump.setReleaseDate(LocalDate.now());
        firstMovieDump.setDescription("Movie Description 1");
        firstMovieDump.setAgeRating(AgeRating.P);

        Movie secondMovieDump = new Movie();
        secondMovieDump.setId(2L);
        secondMovieDump.setGenre("Action 2");
        secondMovieDump.setTitle("Movie Title 2");
        secondMovieDump.setReleaseDate(LocalDate.now());
        secondMovieDump.setDescription("Movie Description 2");
        secondMovieDump.setAgeRating(AgeRating.P);

        Room roomDump = new Room();
        roomDump.setId(1L);
        roomDump.setName("Room Name 1");
        roomDump.setCapacity(100);

        Showtime firstShowtime = new Showtime();
        firstShowtime.setId(1L);
        firstShowtime.setMovie(firstMovieDump);
        firstShowtime.setRoom(roomDump);
        firstShowtime.setStartTime(LocalDateTime.now());
        firstShowtime.setEndTime(LocalDateTime.now().plusDays(7));

        Showtime secondShowtime = new Showtime();
        secondShowtime.setId(2L);
        secondShowtime.setMovie(secondMovieDump);
        secondShowtime.setRoom(roomDump);
        secondShowtime.setStartTime(LocalDateTime.now().plusDays(1));
        secondShowtime.setEndTime(LocalDateTime.now().plusDays(7));

        Showtime thirdShowtime = new Showtime();
        thirdShowtime.setId(3L);
        thirdShowtime.setMovie(firstMovieDump);
        thirdShowtime.setRoom(roomDump);
        thirdShowtime.setStartTime(LocalDateTime.now());
        thirdShowtime.setEndTime(LocalDateTime.now().plusDays(7));

        when(showtimeRepository.findMoviesShowtimeByRange(
                                                            eq(theaterId),
                                                            any(LocalDateTime.class),
                                                            any(LocalDateTime.class)))
                .thenReturn(List.of(firstShowtime, secondShowtime, thirdShowtime));

        // Act
        List<MovieShowtimeResponse> movies = movieService.getScheduledMovies(theaterId);

        // Assert
        assertFalse(movies.isEmpty());
        assertEquals(2, movies.size());
    }
}
