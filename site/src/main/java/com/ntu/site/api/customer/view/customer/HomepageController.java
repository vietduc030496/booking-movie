package com.ntu.site.api.customer.view.customer;

import com.ntu.customerservice.service.movie.MovieService;
import com.ntu.customerservice.service.setting.BannerService;
import com.ntu.moviecore.domain.movie.dto.MovieResponse;
import com.ntu.moviecore.domain.setting.dto.BannerResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.ntu.site.application.dto.response.PageInfo.DEFAULT_PAGE_NUM;
import static com.ntu.site.application.dto.response.PageInfo.DEFAULT_PAGE_SIZE;


@Controller
@AllArgsConstructor
public class HomepageController extends BaseViewController{

    private final BannerService bannerService;

    private final MovieService movieService;

    @GetMapping(value = {"", "/"})
    public String defaultPage() {
        return redirect("/home");
    }

    @GetMapping("/home")
    public String home(Model model) {
        List<BannerResponse> banners = bannerService.getBanners(DEFAULT_PAGE_NUM, DEFAULT_PAGE_SIZE);
        model.addAttribute("banners", banners);

        List<MovieResponse> movieShowToday = movieService.getMovieShowToday();
        model.addAttribute("movieShowToday", movieShowToday);

        List<MovieResponse> movieComingSoon = movieService.getMovieComingSoon();
        model.addAttribute("movieComingSoon", movieComingSoon);
        return "index";
    }

}
