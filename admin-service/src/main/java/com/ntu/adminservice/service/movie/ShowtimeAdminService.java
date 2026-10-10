package com.ntu.adminservice.service.movie;

import com.ntu.common.exception.BusinessException;
import com.ntu.common.exception.ResourceNotFoundException;
import com.ntu.common.util.DateUtil;
import com.ntu.moviecore.domain.booking.repository.BookingRepository;
import com.ntu.moviecore.domain.movie.dto.request.ShowtimeNewRequest;
import com.ntu.moviecore.domain.movie.dto.response.ShowtimeAdminResponse;
import com.ntu.moviecore.domain.movie.entity.Movie;
import com.ntu.moviecore.domain.movie.entity.Showtime;
import com.ntu.moviecore.domain.movie.repository.MovieRepository;
import com.ntu.moviecore.domain.movie.repository.ShowtimeRepository;
import com.ntu.moviecore.domain.theater.entity.Room;
import com.ntu.moviecore.domain.theater.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.ntu.common.constant.AdminMessageKeyConstant.SHOWTIME_HAS_BOOKINGS;
import static com.ntu.common.constant.AdminMessageKeyConstant.SHOWTIME_INVALID_TIME_RANGE;

@Service
@RequiredArgsConstructor
public class ShowtimeAdminService {

    private static final String DISPLAY_DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm";

    private final ShowtimeRepository showtimeRepository;

    private final MovieRepository movieRepository;

    private final RoomRepository roomRepository;

    private final BookingRepository bookingRepository;

    /**
     * @param theaterId theater to filter on, {@code null} for all theaters
     * @param date      day to list, {@code null} for today
     */
    @Transactional(readOnly = true)
    public List<ShowtimeAdminResponse> getShowtimes(Long theaterId, LocalDate date) {
        LocalDate day = date == null ? LocalDate.now() : date;
        return showtimeRepository.findAdminShowtimes(theaterId, day.atStartOfDay(), day.plusDays(1).atStartOfDay())
                .stream()
                .map(ShowtimeAdminService::toResponse)
                .toList();
    }

    /**
     * Creates a showtime unless the room already has a showtime in the same time slot.
     * When no end time is given, it is calculated from the movie running time.
     *
     * @return {@code true} when the showtime was created, {@code false} when it overlaps another showtime of the room
     * @throws ResourceNotFoundException when the movie or the room does not exist
     * @throws BusinessException         when the end time is not after the start time
     */
    @Transactional
    public boolean addNewShowtime(ShowtimeNewRequest request) {
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie", request.getMovieId()));
        Room room = roomRepository.findByIdForUpdate(request.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", request.getRoomId()));

        LocalDateTime start = request.getStartTime();
        LocalDateTime end = request.getEndTime() != null
                ? request.getEndTime()
                : start.plusMinutes(movie.getRunningTime());
        if (!end.isAfter(start)) {
            throw new BusinessException(SHOWTIME_INVALID_TIME_RANGE);
        }

        if (showtimeRepository.existsOverlappingShowtime(room.getId(), start, end)) {
            return false;
        }

        Showtime showtime = new Showtime();
        showtime.setMovie(movie);
        showtime.setRoom(room);
        showtime.setStartTime(start);
        showtime.setEndTime(end);
        showtimeRepository.save(showtime);
        return true;
    }

    /**
     * @throws ResourceNotFoundException when the showtime does not exist
     * @throws BusinessException         when tickets were already booked for the showtime
     */
    @Transactional
    public void deleteShowtime(long showtimeId) {
        if (!showtimeRepository.existsById(showtimeId)) {
            throw new ResourceNotFoundException("Showtime", showtimeId);
        }
        if (bookingRepository.existsByShowtimeId(showtimeId)) {
            throw new BusinessException(SHOWTIME_HAS_BOOKINGS);
        }
        showtimeRepository.deleteById(showtimeId);
    }

    private static ShowtimeAdminResponse toResponse(Showtime showtime) {
        Room room = showtime.getRoom();
        ShowtimeAdminResponse response = new ShowtimeAdminResponse();
        response.setShowtimeId(showtime.getId());
        response.setMovieId(showtime.getMovie().getId());
        response.setMovieTitle(showtime.getMovie().getTitle());
        response.setRoomId(room.getId());
        response.setRoomName(room.getName());
        response.setTheaterId(room.getTheater().getId());
        response.setTheaterName(room.getTheater().getName());
        response.setStartTime(DateUtil.localDateTimeToString(showtime.getStartTime(), DISPLAY_DATE_TIME_PATTERN));
        response.setEndTime(DateUtil.localDateTimeToString(showtime.getEndTime(), DISPLAY_DATE_TIME_PATTERN));
        return response;
    }
}
