package com.ntu.adminservice.service.movie;

import com.ntu.common.exception.BusinessException;
import com.ntu.common.exception.ResourceNotFoundException;
import com.ntu.moviecore.domain.booking.repository.BookingRepository;
import com.ntu.moviecore.domain.movie.dto.request.ShowtimeNewRequest;
import com.ntu.moviecore.domain.movie.entity.Movie;
import com.ntu.moviecore.domain.movie.entity.Showtime;
import com.ntu.moviecore.domain.movie.repository.MovieRepository;
import com.ntu.moviecore.domain.movie.repository.ShowtimeRepository;
import com.ntu.moviecore.domain.theater.entity.Room;
import com.ntu.moviecore.domain.theater.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.ntu.common.constant.AdminMessageKeyConstant.SHOWTIME_HAS_BOOKINGS;
import static com.ntu.common.constant.AdminMessageKeyConstant.SHOWTIME_INVALID_TIME_RANGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShowtimeAdminServiceTest {

    private static final long MOVIE_ID = 1L;
    private static final long ROOM_ID = 2L;
    private static final long SHOWTIME_ID = 3L;
    private static final int RUNNING_TIME = 120;
    private static final LocalDateTime START = LocalDateTime.of(2025, 9, 7, 18, 0);

    @Mock
    private ShowtimeRepository showtimeRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private ShowtimeAdminService showtimeAdminService;

    private Movie movie;

    private Room room;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(MOVIE_ID);
        movie.setRunningTime(RUNNING_TIME);

        room = new Room();
        room.setId(ROOM_ID);
    }

    @Test
    void add_new_showtime_is_not_overlap_and_add_success() {
        ShowtimeNewRequest request = newRequest(START, START.plusHours(3));
        mockMovieAndRoom();
        when(showtimeRepository.existsOverlappingShowtime(ROOM_ID, START, START.plusHours(3))).thenReturn(false);

        boolean created = showtimeAdminService.addNewShowtime(request);

        assertTrue(created);
        ArgumentCaptor<Showtime> captor = ArgumentCaptor.forClass(Showtime.class);
        verify(showtimeRepository).save(captor.capture());
        Showtime saved = captor.getValue();
        assertSame(movie, saved.getMovie());
        assertSame(room, saved.getRoom());
        assertEquals(START, saved.getStartTime());
        assertEquals(START.plusHours(3), saved.getEndTime());
    }

    @Test
    void add_new_showtime_is_overlap_and_add_failure() {
        ShowtimeNewRequest request = newRequest(START, START.plusHours(2));
        mockMovieAndRoom();
        when(showtimeRepository.existsOverlappingShowtime(ROOM_ID, START, START.plusHours(2))).thenReturn(true);

        boolean created = showtimeAdminService.addNewShowtime(request);

        assertFalse(created);
        verify(showtimeRepository, never()).save(any());
    }

    @Test
    void add_new_showtime_without_end_time_uses_movie_running_time() {
        ShowtimeNewRequest request = newRequest(START, null);
        LocalDateTime expectedEnd = START.plusMinutes(RUNNING_TIME);
        mockMovieAndRoom();
        when(showtimeRepository.existsOverlappingShowtime(ROOM_ID, START, expectedEnd)).thenReturn(false);

        assertTrue(showtimeAdminService.addNewShowtime(request));

        ArgumentCaptor<Showtime> captor = ArgumentCaptor.forClass(Showtime.class);
        verify(showtimeRepository).save(captor.capture());
        assertEquals(expectedEnd, captor.getValue().getEndTime());
    }

    @Test
    void add_new_showtime_with_end_time_before_start_time_throws_business_exception() {
        ShowtimeNewRequest request = newRequest(START, START.minusMinutes(1));
        mockMovieAndRoom();

        BusinessException exception = assertThrows(BusinessException.class,
                () -> showtimeAdminService.addNewShowtime(request));

        assertEquals(SHOWTIME_INVALID_TIME_RANGE, exception.getMessageKey());
        verify(showtimeRepository, never()).save(any());
    }

    @Test
    void add_new_showtime_with_unknown_movie_throws_not_found() {
        ShowtimeNewRequest request = newRequest(START, START.plusHours(2));
        when(movieRepository.findById(MOVIE_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> showtimeAdminService.addNewShowtime(request));
        verify(showtimeRepository, never()).save(any());
    }

    @Test
    void add_new_showtime_with_unknown_room_throws_not_found() {
        ShowtimeNewRequest request = newRequest(START, START.plusHours(2));
        when(movieRepository.findById(MOVIE_ID)).thenReturn(Optional.of(movie));
        when(roomRepository.findByIdForUpdate(ROOM_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> showtimeAdminService.addNewShowtime(request));
        verify(showtimeRepository, never()).save(any());
    }

    @Test
    void delete_showtime_without_bookings_success() {
        when(showtimeRepository.existsById(SHOWTIME_ID)).thenReturn(true);
        when(bookingRepository.existsByShowtimeId(SHOWTIME_ID)).thenReturn(false);

        showtimeAdminService.deleteShowtime(SHOWTIME_ID);

        verify(showtimeRepository).deleteById(SHOWTIME_ID);
    }

    @Test
    void delete_showtime_with_bookings_throws_business_exception() {
        when(showtimeRepository.existsById(SHOWTIME_ID)).thenReturn(true);
        when(bookingRepository.existsByShowtimeId(SHOWTIME_ID)).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class,
                () -> showtimeAdminService.deleteShowtime(SHOWTIME_ID));

        assertEquals(SHOWTIME_HAS_BOOKINGS, exception.getMessageKey());
        verify(showtimeRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_unknown_showtime_throws_not_found() {
        when(showtimeRepository.existsById(SHOWTIME_ID)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> showtimeAdminService.deleteShowtime(SHOWTIME_ID));
        verify(showtimeRepository, never()).deleteById(anyLong());
    }

    private void mockMovieAndRoom() {
        when(movieRepository.findById(MOVIE_ID)).thenReturn(Optional.of(movie));
        when(roomRepository.findByIdForUpdate(ROOM_ID)).thenReturn(Optional.of(room));
    }

    private static ShowtimeNewRequest newRequest(LocalDateTime start, LocalDateTime end) {
        ShowtimeNewRequest request = new ShowtimeNewRequest();
        request.setMovieId(MOVIE_ID);
        request.setRoomId(ROOM_ID);
        request.setStartTime(start);
        request.setEndTime(end);
        return request;
    }
}
