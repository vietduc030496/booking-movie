package com.ntu.site.api.admin.view;

import com.ntu.adminservice.service.dashboard.DashboardAdminService;
import com.ntu.adminservice.service.material.ProvinceAdminService;
import com.ntu.adminservice.service.material.WardAdminService;
import com.ntu.adminservice.service.movie.MovieAdminService;
import com.ntu.adminservice.service.movie.ShowtimeAdminService;
import com.ntu.adminservice.service.theater.RoomAdminService;
import com.ntu.adminservice.service.theater.TheaterAdminService;
import com.ntu.common.exception.ResourceNotFoundException;
import com.ntu.moviecore.domain.booking.dto.BookingAdminResponse;
import com.ntu.moviecore.domain.booking.dto.DashboardAdminResponse;
import com.ntu.moviecore.domain.booking.entity.BookingStatus;
import com.ntu.moviecore.domain.movie.dto.request.ShowtimeNewRequest;
import com.ntu.moviecore.domain.movie.dto.response.MovieResponse;
import com.ntu.moviecore.domain.movie.dto.response.ShowtimeAdminResponse;
import com.ntu.moviecore.domain.theater.dto.RoomAdminResponse;
import com.ntu.moviecore.domain.theater.dto.TheaterAdminResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.ntu.common.constant.AdminMessageKeyConstant.INVALID_INPUT;
import static com.ntu.common.constant.AdminMessageKeyConstant.SAVE_SUCCESS;
import static com.ntu.common.constant.AdminMessageKeyConstant.SHOWTIME_OVERLAP;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = AdminViewTestConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminViewControllerTest {

    private static final String SHOWTIMES_URL = "/views/admin/showtimes";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DashboardAdminService dashboardAdminService;

    @MockitoBean
    private MovieAdminService movieAdminService;

    @MockitoBean
    private ShowtimeAdminService showtimeAdminService;

    @MockitoBean
    private TheaterAdminService theaterAdminService;

    @MockitoBean
    private RoomAdminService roomAdminService;

    @MockitoBean
    private ProvinceAdminService provinceAdminService;

    @MockitoBean
    private WardAdminService wardAdminService;

    @Test
    void dashboard_page_shows_statistics_and_latest_bookings() throws Exception {
        BookingAdminResponse booking = new BookingAdminResponse(1L, "user@ntu.vn", "Movie A",
                LocalDateTime.of(2025, 9, 7, 10, 30), BookingStatus.CONFIRMED, 90000);
        when(dashboardAdminService.getDashboard()).thenReturn(new DashboardAdminResponse(1205, 3458, 1250000, List.of(booking)));

        mockMvc.perform(get("/views/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("1.205")))
                .andExpect(content().string(containsString("1.250.000 ₫")))
                .andExpect(content().string(containsString("user@ntu.vn")))
                .andExpect(content().string(containsString("07/09/2025 10:30")));
    }

    @Test
    void showtimes_page_lists_showtimes_of_the_day() throws Exception {
        LocalDate date = LocalDate.of(2025, 9, 7);
        when(showtimeAdminService.getShowtimes(1L, date)).thenReturn(List.of(showtime()));
        when(theaterAdminService.getTheaters()).thenReturn(List.of(theater()));

        mockMvc.perform(get(SHOWTIMES_URL).param("theaterId", "1").param("date", "2025-09-07"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Movie A")))
                .andExpect(content().string(containsString("07/09/2025 18:00")))
                .andExpect(content().string(containsString("/views/admin/showtimes/3/delete")));
    }

    @Test
    void add_new_showtime_modal_lists_movies_and_rooms() throws Exception {
        MovieResponse movie = new MovieResponse();
        movie.setId(1L);
        movie.setTitle("Movie A");
        movie.setRunningTime(120);
        when(movieAdminService.getAllMovies()).thenReturn(List.of(movie));
        when(roomAdminService.getRooms(isNull())).thenReturn(List.of(room()));

        mockMvc.perform(get(SHOWTIMES_URL + "/add-new"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Movie A (120 phút)")))
                .andExpect(content().string(containsString("NTU Cinema - Room 1")));
    }

    @Test
    void add_new_showtime_success_redirects_with_success_message() throws Exception {
        when(showtimeAdminService.addNewShowtime(any(ShowtimeNewRequest.class))).thenReturn(true);

        mockMvc.perform(post(SHOWTIMES_URL + "/add-new")
                        .param("movieId", "1")
                        .param("roomId", "2")
                        .param("startTime", "2025-09-07T18:00"))
                .andExpect(redirectedUrl(SHOWTIMES_URL))
                .andExpect(flash().attribute(BaseAdminViewController.SUCCESS_MESSAGE, SAVE_SUCCESS));
    }

    @Test
    void add_new_overlapping_showtime_redirects_with_error_message() throws Exception {
        when(showtimeAdminService.addNewShowtime(any(ShowtimeNewRequest.class))).thenReturn(false);

        mockMvc.perform(post(SHOWTIMES_URL + "/add-new")
                        .param("movieId", "1")
                        .param("roomId", "2")
                        .param("startTime", "2025-09-07T18:00"))
                .andExpect(redirectedUrl(SHOWTIMES_URL))
                .andExpect(flash().attribute(BaseAdminViewController.ERROR_MESSAGE, SHOWTIME_OVERLAP));
    }

    @Test
    void add_new_showtime_without_room_is_rejected() throws Exception {
        mockMvc.perform(post(SHOWTIMES_URL + "/add-new")
                        .param("movieId", "1")
                        .param("startTime", "2025-09-07T18:00"))
                .andExpect(redirectedUrl(SHOWTIMES_URL))
                .andExpect(flash().attribute(BaseAdminViewController.ERROR_MESSAGE, INVALID_INPUT));

        verify(showtimeAdminService, never()).addNewShowtime(any());
    }

    @Test
    void flash_message_is_rendered_from_i18n_key() throws Exception {
        mockMvc.perform(get(SHOWTIMES_URL).flashAttr(BaseAdminViewController.ERROR_MESSAGE, SHOWTIME_OVERLAP))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("alert-danger")))
                .andExpect(content().string(containsString("The room already has a showtime in this time slot")));
    }

    @Test
    void rooms_page_and_room_modal_render() throws Exception {
        when(roomAdminService.getRooms(1L)).thenReturn(List.of(room()));
        when(roomAdminService.getRoomById(2L)).thenReturn(room());
        when(theaterAdminService.getTheaters()).thenReturn(List.of(theater()));

        mockMvc.perform(get("/views/admin/rooms").param("theaterId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Room 1")));

        mockMvc.perform(get("/views/admin/rooms/2"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("value=\"Room 1\"")))
                .andExpect(content().string(containsString("/views/admin/rooms/2")));
    }

    @Test
    void theater_modals_render_for_add_and_edit() throws Exception {
        when(theaterAdminService.getTheaterById(1L)).thenReturn(theater());

        mockMvc.perform(get("/views/admin/theaters/add-new"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("theaterName")));

        mockMvc.perform(get("/views/admin/theaters/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("value=\"NTU Cinema\"")));
    }

    @Test
    void theaters_page_renders_search_result() throws Exception {
        when(theaterAdminService.searchTheaters("ntu", null, null)).thenReturn(List.of(theater()));

        mockMvc.perform(get("/views/admin/theaters").param("keyword", "ntu"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("NTU Cinema")));
    }

    @Test
    void movies_page_and_add_movie_modal_render() throws Exception {
        mockMvc.perform(get("/views/admin/movies").param("keyword", "abc"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("value=\"abc\"")));

        mockMvc.perform(get("/views/admin/movies/add-new"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("addMovieModal")));
    }

    @Test
    void unknown_room_returns_not_found_page() throws Exception {
        when(roomAdminService.getRoomById(99L)).thenThrow(new ResourceNotFoundException("Room", 99L));

        mockMvc.perform(get("/views/admin/rooms/99"))
                .andExpect(status().isNotFound());
    }

    private static ShowtimeAdminResponse showtime() {
        ShowtimeAdminResponse showtime = new ShowtimeAdminResponse();
        showtime.setShowtimeId(3L);
        showtime.setMovieTitle("Movie A");
        showtime.setTheaterName("NTU Cinema");
        showtime.setRoomName("Room 1");
        showtime.setStartTime("07/09/2025 18:00");
        showtime.setEndTime("07/09/2025 20:00");
        return showtime;
    }

    private static TheaterAdminResponse theater() {
        TheaterAdminResponse theater = new TheaterAdminResponse();
        theater.setTheaterId(1L);
        theater.setName("NTU Cinema");
        theater.setProvinceCode("01");
        return theater;
    }

    private static RoomAdminResponse room() {
        RoomAdminResponse room = new RoomAdminResponse();
        room.setRoomId(2L);
        room.setName("Room 1");
        room.setTheaterId(1L);
        room.setTheaterName("NTU Cinema");
        room.setCapacity(40);
        return room;
    }
}
