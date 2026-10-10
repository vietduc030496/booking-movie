package com.ntu.site.api.admin.view.movie;

import com.ntu.adminservice.service.movie.MovieAdminService;
import com.ntu.moviecore.domain.movie.dto.request.MovieNewRequest;
import com.ntu.moviecore.domain.movie.dto.request.MovieUpdateRequest;
import com.ntu.moviecore.domain.movie.entity.AgeRating;
import com.ntu.site.api.admin.view.BaseAdminViewController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

import static com.ntu.common.constant.AdminMessageKeyConstant.SAVE_SUCCESS;
import static com.ntu.common.constant.UrlConstant.ADMIN_MOVIE_VIEW_URL;

@Controller
@RequestMapping(ADMIN_MOVIE_VIEW_URL)
@RequiredArgsConstructor
public class MovieAdminViewController extends BaseAdminViewController {

    private static final String MOVIE = "movie";
    private static final String MOVIE_MODAL_FRAGMENT = "movieModalFragment";

    private final MovieAdminService movieAdminService;

    @GetMapping
    public String getMoviePage(@RequestParam(value = "start", required = false)
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                               @RequestParam(value = "end", required = false)
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
                               @RequestParam(value = "keyword", required = false) String keyword,
                               Model model) {
        model.addAttribute("movies", movieAdminService.getMovieByRange(start, end, keyword));
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("keyword", keyword);
        return "admin/movie/movies";
    }

    @GetMapping("/{movieId}")
    public String getMovieDetailPage(@PathVariable("movieId") long movieId, Model model) {
        addFormAttributes(model, movieAdminService.getMovieById(movieId), ADMIN_MOVIE_VIEW_URL + "/" + movieId);
        return fragment(ADMIN_FRAGMENT_FILE, MOVIE_MODAL_FRAGMENT);
    }

    @PostMapping("/{movieId}")
    public String updateMovie(@PathVariable("movieId") long movieId,
                              @Valid @ModelAttribute(MOVIE) MovieUpdateRequest request,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        return submit(result, redirectAttributes, ADMIN_MOVIE_VIEW_URL, SAVE_SUCCESS,
                () -> movieAdminService.updateMovie(movieId, request));
    }

    @GetMapping(ADD_NEW_PATH)
    public String getAddNewMoviePage(Model model) {
        addFormAttributes(model, new MovieNewRequest(), ADMIN_MOVIE_VIEW_URL + ADD_NEW_PATH);
        return fragment(ADMIN_FRAGMENT_FILE, MOVIE_MODAL_FRAGMENT);
    }

    @PostMapping(ADD_NEW_PATH)
    public String addNewMovie(@Valid @ModelAttribute(MOVIE) MovieNewRequest request,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        return submit(result, redirectAttributes, ADMIN_MOVIE_VIEW_URL, SAVE_SUCCESS,
                () -> movieAdminService.addNewMovie(request));
    }

    @PostMapping("/elasticsearch")
    @ResponseBody
    public ResponseEntity<String> syncAllMoviesToElasticsearch() {
        movieAdminService.syncAllMoviesToElasticsearch();
        return ResponseEntity.ok("Success");
    }

    private void addFormAttributes(Model model, Object movie, String actionUrl) {
        model.addAttribute(MOVIE, movie);
        model.addAttribute("ageRatingList", AgeRating.values());
        model.addAttribute("genres", movieAdminService.getAllGenres());
        model.addAttribute(ACTION_URL, actionUrl);
    }
}
