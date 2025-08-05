package com.ntu.site.api.admin.view;

import com.ntu.adminservice.service.material.ProvinceAdminService;
import com.ntu.adminservice.service.material.WardAdminService;
import com.ntu.adminservice.service.movie.MovieAdminService;
import com.ntu.moviecore.domain.movie.dto.response.MovieResponse;
import com.ntu.site.api.customer.view.customer.BaseViewController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import static com.ntu.common.constant.UrlConstant.ADMIN_VIEW_URL;

@Controller
@RequestMapping(ADMIN_VIEW_URL)
@AllArgsConstructor
public class AdminViewController extends BaseViewController {

    private final MovieAdminService movieAdminService;

    private final WardAdminService wardAdminService;

    private final ProvinceAdminService provinceAdminService;

    @GetMapping
    public String getAdminPage() {
        return "admin/index";
    }

    @GetMapping("/movies")
    public String getMoviePage(@RequestParam(value = "start", required = false) LocalDate start,
                               @RequestParam(value = "end", required = false)LocalDate end,
                               Model model) {
        List<MovieResponse> movieByRange = movieAdminService.getMovieByRange(start, end);
        model.addAttribute("movies", movieByRange);
        return "admin/movie/movies";
    }

    @GetMapping("/showtimes")
    public String getShowTimesPage(Model model) {
        return "admin/showtime/showtimes";
    }

    @GetMapping("/theaters")
    public String getTheatersPage(Model model) {
        model.addAttribute("wards", wardAdminService.getAllWard());
        model.addAttribute("provinces", provinceAdminService.getAllProvince());

        return "admin/theater/theaters";
    }
}
