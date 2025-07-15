package com.ntu.api.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Chàosss mừng đến với Spring Boot + Thymeleaf!");
        return "index"; // Trả về templates/index.html
    }

}
