package com.ntu.site.api.admin.view.theater;

import com.ntu.adminservice.service.material.ProvinceAdminService;
import com.ntu.adminservice.service.material.WardAdminService;
import com.ntu.adminservice.service.theater.TheaterAdminService;
import com.ntu.customerservice.service.theater.TheaterService;
import com.ntu.moviecore.domain.movie.dto.request.MovieNewRequest;
import com.ntu.moviecore.domain.movie.entity.AgeRating;
import com.ntu.moviecore.domain.theater.dto.TheaterNewRequest;
import com.ntu.site.api.customer.view.customer.BaseViewController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.ntu.common.constant.UrlConstant.ADMIN_MOVIE_VIEW_URL;
import static com.ntu.common.constant.UrlConstant.ADMIN_THEATER_ADMIN_URL;

@Controller
@RequestMapping(ADMIN_THEATER_ADMIN_URL)
@AllArgsConstructor
public class TheaterAdminViewController extends BaseViewController {

    private final TheaterAdminService theaterAdminService;

    private final ProvinceAdminService provinceAdminService;

    private final WardAdminService wardAdminService;

    @GetMapping("/{theaterId}")
    public String getTheaterDetailPage(@PathVariable("theaterId") Long theaterId,
                                       Model model) {
        model.addAttribute("theater", theaterAdminService.getTheaterById(theaterId));
        model.addAttribute("provinces", provinceAdminService.getAllProvince());
        model.addAttribute("wards", wardAdminService.getAllWard());
        model.addAttribute("actionUrl", ADMIN_THEATER_ADMIN_URL + "/" + theaterId);
        return fragment("general-admin", "theaterModalFragment");
    }

    @PostMapping("/{theaterId}")
    public String updateTheater(@PathVariable("theaterId") Long theaterId,
                                @ModelAttribute("theater") TheaterNewRequest theaterNewRequest,
                                BindingResult result,
                                Model model) {
        theaterAdminService.updateTheater(theaterId, theaterNewRequest);
        return redirect(ADMIN_THEATER_ADMIN_URL);
    }

    @GetMapping("/add-new")
    public String getAddNewTheaterPage(Model model) {
        model.addAttribute("theater", new TheaterNewRequest());
        model.addAttribute("provinces", provinceAdminService.getAllProvince());
        model.addAttribute("wards", wardAdminService.getAllWard());
        model.addAttribute("actionUrl", ADMIN_THEATER_ADMIN_URL + "/add-new");
        return fragment("general-admin", "theaterModalFragment");
    }

    @PostMapping("/add-new")
    public String addNewTheater(@ModelAttribute("theater") TheaterNewRequest newTheater,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("theater", new MovieNewRequest());
            return fragment("general-admin", "theaterModalFragment");
        }

        theaterAdminService.addNewTheater(newTheater);
        return redirect(ADMIN_THEATER_ADMIN_URL);
    }

}
