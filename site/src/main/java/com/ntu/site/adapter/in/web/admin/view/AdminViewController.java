package com.ntu.site.adapter.in.web.admin.view;

import com.ntu.adminservice.application.dashboard.service.DashboardAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ntu.common.constant.UrlConstant.ADMIN_VIEW_URL;

@Controller
@RequestMapping(ADMIN_VIEW_URL)
@RequiredArgsConstructor
public class AdminViewController extends BaseAdminViewController {

    private final DashboardAdminService dashboardAdminService;

    @GetMapping({"", "/dashboard"})
    public String getAdminPage(Model model) {
        model.addAttribute("dashboard", dashboardAdminService.getDashboard());
        return "admin/index";
    }
}
