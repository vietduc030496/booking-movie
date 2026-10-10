package com.ntu.site.adapter.in.web.customer.view.showtime;

import com.ntu.customerservice.application.movie.service.ShowtimeService;
import com.ntu.moviecore.application.movie.dto.response.ShowtimeTheaterResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.ntu.common.constant.UrlConstant.SHOWTIME_VIEW_URL;

@Controller
@RequestMapping(SHOWTIME_VIEW_URL)
@AllArgsConstructor
public class ShowtimeViewController {

    private final ShowtimeService showtimeService;

    @GetMapping("/movies/{movieId}/theaters/{theaterId}")
    public String getShowtimeMovieByTheater(@PathVariable("movieId") Long movieId,
                                            @PathVariable("theaterId") Long theaterId,
                                            Model model) {
        List<ShowtimeTheaterResponse> showtimeMovieByTheater = showtimeService.getShowtimeMovieByTheater(movieId, theaterId);
        model.addAttribute("showtimeByDay", showtimeMovieByTheater);

        return "fragments/general :: showtime-modal";
    }
}
