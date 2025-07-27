package com.ntu.customerservice.service.movie;

import com.ntu.common.util.DateUtil;
import com.ntu.moviecore.domain.movie.dto.ShowtimeResponse;
import com.ntu.moviecore.domain.movie.dto.ShowtimeTheaterDbResponse;
import com.ntu.moviecore.domain.movie.dto.ShowtimeTheaterResponse;
import com.ntu.moviecore.domain.movie.repository.ShowtimeRepository;
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
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    public List<ShowtimeTheaterResponse> getShowtimeMovieByTheater(Long movieId, Long theaterId) {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfDay = now.atStartOfDay();
        LocalDateTime endOfDay = now.plusDays(7).atTime(LocalTime.MAX);

        List<ShowtimeTheaterDbResponse> dbResponses = showtimeRepository.findShowtimeByMovieIdAndTheaterId(movieId,
                                                                                                    theaterId,
                                                                                                    startOfDay,
                                                                                                    endOfDay);
        Map<String, List<ShowtimeResponse>> mapDayToShowtime = new HashMap<>();
        for (ShowtimeTheaterDbResponse dbResponse : dbResponses) {
            String dayTime = DateUtil.formatVNFull(dbResponse.getStartTime());

            ShowtimeResponse showtime = new ShowtimeResponse();
            showtime.setShowtimeId(dbResponse.getTimeShowId());
            showtime.setRoomId(dbResponse.getRoomId());
            showtime.setRoomName(dbResponse.getRoomName());
            showtime.setStartHour(DateUtil.localDateTimeToString(dbResponse.getStartTime(), "HH:mm"));

            if (mapDayToShowtime.containsKey(dayTime)) {
                mapDayToShowtime.get(dayTime).add(showtime);
            } else {
                List<ShowtimeResponse> showtimes = new ArrayList<>();
                showtimes.add(showtime);
                mapDayToShowtime.put(dayTime, showtimes);
            }
        }

        List<ShowtimeTheaterResponse> responses = new ArrayList<>();
        for (Map.Entry<String, List<ShowtimeResponse>> entry : mapDayToShowtime.entrySet()) {
            ShowtimeTheaterResponse response = new ShowtimeTheaterResponse();
            response.setDayTime(entry.getKey());
            response.setShowtimes(entry.getValue());
            responses.add(response);
        }
        return responses;
    }


}
