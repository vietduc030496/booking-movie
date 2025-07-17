package com.ntu.site.api.customer.view;

import com.ntu.customerservice.service.movie.MovieService;
import com.ntu.moviecore.domain.movie.dto.MovieResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomepageController extends BaseViewController{

    private final MovieService movieService;

    @GetMapping(value = {"", "/"})
    public String defaultPage() {
        return redirect("/home");
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<MovieResponse> movieShowToday = movieService.getMovieShowToday();
        model.addAttribute("movieShowToday", movieShowToday);
        return "index";
    }

}
