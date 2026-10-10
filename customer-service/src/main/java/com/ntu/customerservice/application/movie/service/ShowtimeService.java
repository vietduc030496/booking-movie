package com.ntu.customerservice.application.movie.service;

import com.ntu.common.util.DateUtil;
import com.ntu.moviecore.application.movie.dto.response.ShowtimeResponse;
import com.ntu.moviecore.domain.movie.readmodel.ShowtimeSlot;
import com.ntu.moviecore.application.movie.dto.response.ShowtimeTheaterResponse;
import com.ntu.persistence.movie.repository.ShowtimeJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ShowtimeService {

    private final ShowtimeRepository showtimeRepository;

    public List<ShowtimeTheaterResponse> getShowtimeMovieByTheater(Long movieId, Long theaterId) {
        LocalDate now = LocalDate.now(Clock.systemDefaultZone());
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

            mapDayToShowtime.computeIfAbsent(dayTime, key -> new ArrayList<>()).add(showtime);
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
