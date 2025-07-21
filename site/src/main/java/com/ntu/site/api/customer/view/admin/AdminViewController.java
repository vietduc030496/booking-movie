package com.ntu.site.api.customer.view.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ntu.common.constant.UrlConstant.ADMIN_VIEW_URL;

@Controller
@RequestMapping(ADMIN_VIEW_URL)
public class AdminViewController {

    @GetMapping
    public String getAdminPage() {
        return "admin/index.html";
    }
}
