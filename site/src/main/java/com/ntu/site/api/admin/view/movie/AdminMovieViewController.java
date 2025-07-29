package com.ntu.site.api.admin.view.movie;

import com.ntu.moviecore.domain.movie.dto.request.MovieNewRequest;
import com.ntu.site.api.customer.view.customer.BaseViewController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ntu.common.constant.UrlConstant.ADMIN_MOVIE_VIEW_URL;

@Controller
@RequestMapping(ADMIN_MOVIE_VIEW_URL)
@AllArgsConstructor
public class AdminMovieViewController extends BaseViewController {

    @GetMapping("/add-new")
    public String getAddNewMoviePage(Model model) {
        model.addAttribute("newMovie", new MovieNewRequest());
        return "admin/movie/add-new";
    }

    @PostMapping("/add-new")
    public String addNewMovie(@ModelAttribute("newMovie") MovieNewRequest newMovie,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("newMovie", new MovieNewRequest());
            return "admin/movie/add-new";
        }

        return redirect(ADMIN_MOVIE_VIEW_URL + "/movies");
    }
}
