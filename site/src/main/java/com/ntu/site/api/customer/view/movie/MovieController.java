package com.ntu.site.api.customer.view.movie;

import com.ntu.customerservice.service.movie.MovieService;
import com.ntu.moviecore.domain.movie.dto.MovieResponse;
import com.ntu.moviecore.domain.movie.dto.MovieShowtimeResponse;
import com.ntu.site.api.customer.view.BaseViewController;
import com.ntu.site.application.dto.response.CollectionDataResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String getMovieScheduled(Model model) {
        List<MovieShowtimeResponse> scheduledMovies = movieService.getScheduledMovies();
        model.addAttribute("scheduledMovies", scheduledMovies);
        return "movie/movies-scheduled";
    }

    @GetMapping
    public String getMovies(Model model) {
        List<MovieResponse> movieShowToday = movieService.getMovieShowToday();
        model.addAttribute("movieShowToday", movieShowToday);

        List<MovieResponse> movieComingSoon = movieService.getMovieComingSoon();
        model.addAttribute("movieComingSoon", movieComingSoon);
        return "movie/films";
    }

}
