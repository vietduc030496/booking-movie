package com.ntu.api.rest.user;

import com.ntu.domain.authentication.entity.User;
import com.ntu.domain.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String getUsers() {
        User user = new User();
        user.setEmail("nguyenvietduc030496@gmail.com");
        user.setPassword("123456");
        userRepository.save(user);
        return "Hello world";
    }
}
