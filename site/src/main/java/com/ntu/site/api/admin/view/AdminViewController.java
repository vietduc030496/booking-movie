package com.ntu.site.api.admin.view;

import com.ntu.site.api.customer.view.customer.BaseViewController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ntu.common.constant.UrlConstant.ADMIN_VIEW_URL;

@Controller
@RequestMapping(ADMIN_VIEW_URL)
@AllArgsConstructor
public class AdminViewController extends BaseViewController {

    @GetMapping
    public String getAdminPage() {
        return "admin/index";
    }

    @GetMapping("/movies")
    public String getMoviePage(Model model) {
        return "admin/movie/movies";
    }

    @GetMapping("/showtimes")
    public String getShowTimesPage(Model model) {
        return "admin/showwtime/showtimes";
    }
}
