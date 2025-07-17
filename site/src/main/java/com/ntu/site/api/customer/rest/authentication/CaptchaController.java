package com.ntu.site.api.customer.rest.authentication;

import com.ntu.moviecore.infrastructure.util.CaptchaUtil;
import com.ntu.site.application.dto.response.SingleDataResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ntu.common.constant.UrlConstant.CAPTCHA_URL;


@RestController
@RequestMapping(CAPTCHA_URL)
public class CaptchaController {

    @GetMapping
    public ResponseEntity<SingleDataResponse<String>> getCaptcha(HttpSession session) {
        String captchaCode = CaptchaUtil.generateCaptcha();
        session.setAttribute(CaptchaUtil.CAPTCHA_KEY, captchaCode);
        return ResponseEntity.ok(SingleDataResponse.success(captchaCode));
    }
}
