package com.ntu.api.view.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ntu.infrastructure.constant.UrlConstant.ERROR_URL;

@Controller
@RequestMapping(ERROR_URL)
public class ErrorViewController {

    @GetMapping("/404")
    public String get404Page() {
        return "error/404";
    }
}
