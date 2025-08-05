package com.ntu.site.api.admin.view.movie;

import com.ntu.adminservice.service.movie.MovieAdminService;
import com.ntu.common.util.CaffeineCacheUtil;
import com.ntu.moviecore.domain.movie.dto.request.MovieNewRequest;
import com.ntu.moviecore.domain.movie.dto.request.MovieUpdateRequest;
import com.ntu.moviecore.domain.movie.dto.response.MovieResponse;
import com.ntu.moviecore.domain.movie.entity.AgeRating;
import com.ntu.site.api.customer.view.customer.BaseViewController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.ntu.common.constant.UrlConstant.ADMIN_MOVIE_VIEW_URL;

@Controller
@RequestMapping(ADMIN_MOVIE_VIEW_URL)
@AllArgsConstructor
public class MovieAdminViewController extends BaseViewController {

    private final MovieAdminService movieAdminService;

    @GetMapping("/{movieId}")
    public String getMovieDetailPage(@PathVariable("movieId") Long movieId,
                                     Model model) {
        MovieResponse movie = movieAdminService.getMovieById(movieId);
        model.addAttribute("movie", movie);
        model.addAttribute("ageRatingList", AgeRating.values());
        model.addAttribute("genres", CaffeineCacheUtil.get("genres"));
        model.addAttribute("actionUrl", ADMIN_MOVIE_VIEW_URL + "/" +movieId);
        return fragment("general-admin", "movieModalFragment");
    }

    @PostMapping("/{movieId}")
    public String updateMovie(@PathVariable("movieId") Long movieId,
                              @ModelAttribute("movie") MovieUpdateRequest movieUpdateRequest,
                              Model model) {
        movieAdminService.updateMovie(movieId, movieUpdateRequest);
        return redirect(ADMIN_MOVIE_VIEW_URL);
    }

    @GetMapping("/add-new")
    public String getAddNewMoviePage(Model model) {
        model.addAttribute("movie", new MovieNewRequest());
        model.addAttribute("ageRatingList", AgeRating.values());
        model.addAttribute("genres", CaffeineCacheUtil.get("genres"));
        model.addAttribute("actionUrl", ADMIN_MOVIE_VIEW_URL + "/add-new");
        return fragment("general-admin", "movieModalFragment");
    }

    @PostMapping("/add-new")
    public String addNewMovie(@ModelAttribute("newMovie") MovieNewRequest newMovie,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newMovie", new MovieNewRequest());
            return "admin/movie/add-new";
        }

        movieAdminService.addNewMovie(newMovie);
        return redirect(ADMIN_MOVIE_VIEW_URL);
    }

    @GetMapping("/elasticsearch")
    @ResponseBody
    public ResponseEntity<String> syncAllMoviesToElasticsearch() {
        movieAdminService.syncAllMoviesToElasticsearch();
        return ResponseEntity.ok("Success");
    }
}
