package com.ntu.site.api.customer.view.customer.account;

import com.ntu.common.util.CaptchaUtil;
import com.ntu.common.util.I18n;
import com.ntu.moviecore.domain.authentication.dto.request.LoginRequest;
import com.ntu.site.api.customer.view.customer.BaseViewController;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.ntu.common.constant.UrlConstant.ACCOUNT_VIEW_URL;


@Controller
@RequestMapping(ACCOUNT_VIEW_URL)
public class AuthenticationViewController extends BaseViewController {

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

}
