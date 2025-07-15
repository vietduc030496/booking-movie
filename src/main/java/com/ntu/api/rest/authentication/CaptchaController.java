package com.ntu.api.rest.authentication;

import com.ntu.application.dto.response.SingleDataResponse;
import com.ntu.infrastructure.util.CaptchaUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ntu.infrastructure.constant.UrlConstant.CAPTCHA_URL;

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
