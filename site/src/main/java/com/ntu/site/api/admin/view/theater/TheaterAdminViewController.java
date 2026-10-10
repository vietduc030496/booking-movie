package com.ntu.site.api.admin.view.theater;

import com.ntu.adminservice.service.material.ProvinceAdminService;
import com.ntu.adminservice.service.material.WardAdminService;
import com.ntu.adminservice.service.theater.TheaterAdminService;
import com.ntu.moviecore.domain.theater.dto.ProvinceResponse;
import com.ntu.moviecore.domain.theater.dto.TheaterAdminResponse;
import com.ntu.moviecore.domain.theater.dto.TheaterNewRequest;
import com.ntu.site.api.admin.view.BaseAdminViewController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static com.ntu.common.constant.AdminMessageKeyConstant.SAVE_SUCCESS;
import static com.ntu.common.constant.UrlConstant.ADMIN_THEATER_ADMIN_URL;

@Controller
@RequestMapping(ADMIN_THEATER_ADMIN_URL)
@RequiredArgsConstructor
public class TheaterAdminViewController extends BaseAdminViewController {

    private static final String THEATER = "theater";
    private static final String THEATER_MODAL_FRAGMENT = "theaterModalFragment";

    private final TheaterAdminService theaterAdminService;

    private final ProvinceAdminService provinceAdminService;

    private final WardAdminService wardAdminService;

    @GetMapping
    public String getTheatersPage(@RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "provinceId", required = false) Long provinceId,
                                  @RequestParam(value = "wardId", required = false) Long wardId,
                                  Model model) {
        List<ProvinceResponse> provinces = provinceAdminService.getAllProvince();
        String provinceCode = provinces.stream()
                .filter(p -> provinceId != null && p.getProvinceId() == provinceId)
                .map(ProvinceResponse::getProvinceCode)
                .findFirst()
                .orElse(null);

        model.addAttribute("provinces", provinces);
        model.addAttribute("wards", wardAdminService.getAllWard());
        model.addAttribute("theaters", theaterAdminService.searchTheaters(keyword, provinceCode, wardId));
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedProvinceId", provinceId);
        model.addAttribute("selectedWardId", wardId);
        return "admin/theater/theaters";
    }

    @GetMapping("/{theaterId}")
    public String getTheaterDetailPage(@PathVariable("theaterId") long theaterId, Model model) {
        TheaterAdminResponse theater = theaterAdminService.getTheaterById(theaterId);
        addFormAttributes(model, theater, ADMIN_THEATER_ADMIN_URL + "/" + theaterId);
        model.addAttribute("selectedProvinceCode", theater.getProvinceCode());
        return fragment(ADMIN_FRAGMENT_FILE, THEATER_MODAL_FRAGMENT);
    }

    @PostMapping("/{theaterId}")
    public String updateTheater(@PathVariable("theaterId") long theaterId,
                                @Valid @ModelAttribute(THEATER) TheaterNewRequest request,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        return submit(result, redirectAttributes, ADMIN_THEATER_ADMIN_URL, SAVE_SUCCESS,
                () -> theaterAdminService.updateTheater(theaterId, request));
    }

    @GetMapping(ADD_NEW_PATH)
    public String getAddNewTheaterPage(Model model) {
        addFormAttributes(model, new TheaterNewRequest(), ADMIN_THEATER_ADMIN_URL + ADD_NEW_PATH);
        return fragment(ADMIN_FRAGMENT_FILE, THEATER_MODAL_FRAGMENT);
    }

    @PostMapping(ADD_NEW_PATH)
    public String addNewTheater(@Valid @ModelAttribute(THEATER) TheaterNewRequest request,
                                BindingResult result,
                                RedirectAttributes redirectAttributes) {
        return submit(result, redirectAttributes, ADMIN_THEATER_ADMIN_URL, SAVE_SUCCESS,
                () -> theaterAdminService.addNewTheater(request));
    }

    private void addFormAttributes(Model model, Object theater, String actionUrl) {
        model.addAttribute("provinces", provinceAdminService.getAllProvince());
        model.addAttribute("wards", wardAdminService.getAllWard());
        model.addAttribute(THEATER, theater);
        model.addAttribute(ACTION_URL, actionUrl);
    }
}
