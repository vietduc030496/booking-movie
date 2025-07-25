package com.ntu.site.api.customer.view.customer.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ntu.common.constant.UrlConstant.ERROR_URL;


@Controller
@RequestMapping(ERROR_URL)
public class ErrorViewController {

    @GetMapping("/404")
    public String get404Page() {
        return "error/404";
    }

    @GetMapping("/500")
    public String get500Page() {
        return "error/500";
    }
}
