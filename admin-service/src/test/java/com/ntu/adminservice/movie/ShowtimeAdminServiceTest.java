package com.ntu.site.api.admin.view.movie;

import com.ntu.adminservice.service.movie.ShowtimeAdminService;
import com.ntu.moviecore.domain.movie.dto.request.ShowtimeNewRequest;
import com.ntu.moviecore.domain.movie.repository.ShowtimeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Sql(scripts = {"/sql"})
class ShowtimeAdminViewControllerTest {

    @Mock
    private ShowtimeRepository showtimeRepository;

    @InjectMocks
    private ShowtimeAdminService showtimeAdminService;

    @Test
    void add_new_showtime_is_not_overlap_and_add_success() {
        // Arrange
        ShowtimeNewRequest showtimeNewRequest = new ShowtimeNewRequest();

        // Act
        boolean b = showtimeAdminService.addNewShowtime(showtimeNewRequest);

        // Assert
        Assertions.assertTrue(b);
    }

    @Test
    @Sql()
    void add_new_showtime_is_overlap_and_add_failure() {
        // Arrange
        ShowtimeNewRequest showtimeNewRequest = new ShowtimeNewRequest();

        // Act
        boolean b = showtimeAdminService.addNewShowtime(showtimeNewRequest);

        // Assert
        Assertions.assertTrue(b);
    }
}
