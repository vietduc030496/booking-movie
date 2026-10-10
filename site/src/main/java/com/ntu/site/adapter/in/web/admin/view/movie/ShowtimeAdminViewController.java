package com.ntu.site.adapter.in.web.admin.view.movie;

import com.ntu.adminservice.application.movie.service.MovieAdminService;
import com.ntu.adminservice.application.movie.service.ShowtimeAdminService;
import com.ntu.adminservice.application.theater.service.RoomAdminService;
import com.ntu.adminservice.application.theater.service.TheaterAdminService;
import com.ntu.common.exception.BusinessException;
import com.ntu.moviecore.application.movie.dto.request.ShowtimeNewRequest;
import com.ntu.site.adapter.in.web.admin.view.BaseAdminViewController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Clock;
import java.time.LocalDate;

import static com.ntu.common.constant.AdminMessageKeyConstant.DELETE_SUCCESS;
import static com.ntu.common.constant.AdminMessageKeyConstant.SAVE_SUCCESS;
import static com.ntu.common.constant.AdminMessageKeyConstant.SHOWTIME_OVERLAP;
import static com.ntu.common.constant.UrlConstant.ADMIN_SHOWTIME_VIEW_URL;

@Controller
@RequestMapping(ADMIN_SHOWTIME_VIEW_URL)
@RequiredArgsConstructor
public class ShowtimeAdminViewController extends BaseAdminViewController {

    private final ShowtimeAdminService showtimeAdminService;

    private final MovieAdminService movieAdminService;

    private final RoomAdminService roomAdminService;

    private final TheaterAdminService theaterAdminService;

    @GetMapping
    public String getShowtimesPage(@RequestParam(value = "theaterId", required = false) Long theaterId,
                                   @RequestParam(value = "date", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                   Model model) {
        LocalDate day = date == null ? LocalDate.now(Clock.systemDefaultZone()) : date;
        model.addAttribute("showtimes", showtimeAdminService.getShowtimes(theaterId, day));
        model.addAttribute("theaters", theaterAdminService.getTheaters());
        model.addAttribute("selectedTheaterId", theaterId);
        model.addAttribute("selectedDate", day);
        return "admin/showtime/showtimes";
    }

    @GetMapping(ADD_NEW_PATH)
    public String getAddNewShowtimePage(Model model) {
        model.addAttribute("showtime", new ShowtimeNewRequest());
        model.addAttribute("movies", movieAdminService.getAllMovies());
        model.addAttribute("rooms", roomAdminService.getRooms(null));
        model.addAttribute(ACTION_URL, ADMIN_SHOWTIME_VIEW_URL + ADD_NEW_PATH);
        return fragment(ADMIN_FRAGMENT_FILE, "showtimeModalFragment");
    }

    @PostMapping(ADD_NEW_PATH)
    public String addNewShowtime(@Valid @ModelAttribute("showtime") ShowtimeNewRequest request,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        return submit(result, redirectAttributes, ADMIN_SHOWTIME_VIEW_URL, SAVE_SUCCESS, () -> {
            if (!showtimeAdminService.addNewShowtime(request)) {
                throw new BusinessException(SHOWTIME_OVERLAP);
            }
        });
    }

    @PostMapping("/{showtimeId}/delete")
    public String deleteShowtime(@PathVariable("showtimeId") long showtimeId,
                                 RedirectAttributes redirectAttributes) {
        return execute(redirectAttributes, ADMIN_SHOWTIME_VIEW_URL, DELETE_SUCCESS,
                () -> showtimeAdminService.deleteShowtime(showtimeId));
    }
}
