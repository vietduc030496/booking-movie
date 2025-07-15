package com.ntu.api.view.account;

import com.ntu.api.view.BaseViewController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

import static com.ntu.infrastructure.constant.UrlConstant.ACCOUNT_VIEW_URL;

@Controller
@RequestMapping(ACCOUNT_VIEW_URL)
public class AuthenticationController extends BaseViewController {

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "account/login";
    }
}
