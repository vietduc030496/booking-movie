package com.ntu.site.adapter.in.web.customer.view;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
public class TestController {

    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("message", "Hello world");
        return "/test";
    }

    @GetMapping("/test/redis")
    @ResponseBody
    public String test() {
        Boolean b = redisTemplate.opsForValue().setIfAbsent("test", "hello", 10, TimeUnit.MINUTES);
        if (Boolean.TRUE.equals(b)) {
            return "set success";
        }
        return "set fail";
    }
}
