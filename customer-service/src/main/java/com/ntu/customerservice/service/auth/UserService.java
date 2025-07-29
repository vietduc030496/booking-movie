package com.ntu.customerservice.service.auth;

import com.ntu.moviecore.domain.authentication.dto.request.SignupRequest;
import com.ntu.moviecore.domain.authentication.entity.User;
import com.ntu.moviecore.domain.authentication.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void addNewUser(SignupRequest signupRequest) {
        User newUser = modelMapper.map(signupRequest, User.class);
        newUser.setEmail(signupRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(newUser);
    }
}
