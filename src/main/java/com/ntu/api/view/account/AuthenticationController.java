package com.ntu.api.view.account;

import com.ntu.api.view.BaseViewController;
import com.ntu.domain.authentication.dto.request.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

import static com.ntu.infrastructure.constant.UrlConstant.ACCOUNT_VIEW_URL;

@Controller
@RequestMapping(ACCOUNT_VIEW_URL)
public class AuthenticationController extends BaseViewController {

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new LoginRequest());
        return "account/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginRequest") LoginRequest loginRequest, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginRequest", loginRequest);
            return "account/login";
        }
        model.addAttribute("loginRequest", new LoginRequest());
        return "account/login";
    }
}
