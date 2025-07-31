package com.ntu.site.api.customer.view.customer.account;

import com.ntu.common.util.CaptchaUtil;
import com.ntu.common.util.I18n;
import com.ntu.customerservice.service.auth.UserService;
import com.ntu.moviecore.domain.authentication.dto.request.LoginRequest;
import com.ntu.moviecore.domain.authentication.dto.request.SignupRequest;
import com.ntu.site.api.customer.view.customer.BaseViewController;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.ntu.common.constant.UrlConstant.ACCOUNT_VIEW_URL;


@Controller
@RequestMapping(ACCOUNT_VIEW_URL)
@AllArgsConstructor
public class AuthenticationViewController extends BaseViewController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(name = "captchaError", required = false) String captchaError,
                               @RequestParam(name = "error", required = false) String error,
                               @RequestParam(name = "username", required = false) String username,
                               HttpSession session, Model model) {

        String captchaCode = CaptchaUtil.generateCaptcha();
        session.setAttribute(CaptchaUtil.CAPTCHA_KEY, captchaCode);
        model.addAttribute(CaptchaUtil.CAPTCHA_KEY, captchaCode);

        LoginRequest loginRequest = new LoginRequest();

        if (StringUtils.isNoneEmpty(captchaError)) {
            loginRequest.setUsername(username);
            model.addAttribute("messageError", I18n.getMessage("captcha.error"));
        } else if (StringUtils.isNoneEmpty(error)) {
            loginRequest.setUsername(username);
            model.addAttribute("messageError", I18n.getMessage("invalid-user.error"));
        }

        model.addAttribute("loginRequest", loginRequest);
        return "account/login";
    }

    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        model.addAttribute("signupRequest", new SignupRequest());
        return "account/signup";
    }

    @PostMapping("/signup")
    public String signupNewUser(@Valid @ModelAttribute("signupRequest") SignupRequest signupRequest,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("signupRequest", signupRequest);
            return "account/signup";
        }

        userService.addNewUser(signupRequest);
        return redirect(ACCOUNT_VIEW_URL + "/login");
    }

    @GetMapping("/membership")
    public String getMembershipPage(Model model) {
        return "account/membership";
    }
}
