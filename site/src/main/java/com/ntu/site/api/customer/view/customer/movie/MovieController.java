package com.ntu.site.api.customer.view.customer.movie;

import com.ntu.customerservice.service.movie.MovieService;
import com.ntu.moviecore.domain.movie.dto.MovieResponse;
import com.ntu.moviecore.domain.movie.dto.MovieShowtimeResponse;
import com.ntu.site.api.customer.view.customer.BaseViewController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.ntu.common.constant.UrlConstant.MOVIE_VIEW_URL;

@Controller
@RequestMapping(MOVIE_VIEW_URL)
@AllArgsConstructor
public class MovieController extends BaseViewController {

    private final MovieService movieService;

    @GetMapping("/movies-scheduled")
    public String getMovieScheduled(@CookieValue(name = "selectedTheaterId", required = false) Long theaterId, Model model) {
        List<MovieShowtimeResponse> scheduledMovies = movieService.getScheduledMovies(theaterId);
        model.addAttribute("scheduledMovies", scheduledMovies);
        return "movie/movies-scheduled";
    }

    @GetMapping
    public String getMovies(@CookieValue(name = "selectedTheaterId", required = false) Long theaterId, Model model) {
        List<MovieResponse> movieShowToday = movieService.getMovieShowToday(theaterId);
        model.addAttribute("movieShowToday", movieShowToday);

        List<MovieResponse> movieComingSoon = movieService.getMovieComingSoon(theaterId);
        model.addAttribute("movieComingSoon", movieComingSoon);
        return "movie/films";
    }

}
