package com.ntu.site.api.admin.view.movie;

import com.ntu.adminservice.service.movie.ShowtimeAdminService;
import com.ntu.moviecore.domain.movie.dto.request.ShowtimeNewRequest;
import com.ntu.moviecore.domain.movie.repository.ShowtimeRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql(scripts = "/sql/sample-data.sql")
class ShowtimeAdminServiceTest {

    @Autowired
    private ShowtimeAdminService showtimeAdminService;

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Test
    void add_new_showtime_is_not_overlap_and_add_success() {
        ShowtimeNewRequest showtimeNewRequest = new ShowtimeNewRequest();
        showtimeNewRequest.setMovieId(1L);
        showtimeNewRequest.setRoomId(1L);
        showtimeNewRequest.setStartTime(LocalDateTime.now());
        showtimeNewRequest.setEndTime(LocalDateTime.now().plusHours(3));

        boolean b = showtimeAdminService.addNewShowtime(showtimeNewRequest);

        Assertions.assertTrue(b);
    }

    @Test
    @Sql(statements = {
            """
            INSERT INTO tbl_showtime
            (id, created_at, created_by, updated_at, updated_by, "version", end_time, start_time, movie_id, room_id)
            VALUES (1, CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP, NULL, 0, DATEADD('HOUR', 3, CURRENT_TIMESTAMP), CURRENT_TIMESTAMP, 1, 1);
            """
    })
    void add_new_showtime_is_overlap_and_add_failure() {
        ShowtimeNewRequest showtimeNewRequest = new ShowtimeNewRequest();
        showtimeNewRequest.setMovieId(1L);
        showtimeNewRequest.setRoomId(1L);
        showtimeNewRequest.setStartTime(LocalDateTime.now());
        showtimeNewRequest.setEndTime(LocalDateTime.now().plusHours(2));

        boolean b = showtimeAdminService.addNewShowtime(showtimeNewRequest);

        Assertions.assertFalse(b);
    }
}
