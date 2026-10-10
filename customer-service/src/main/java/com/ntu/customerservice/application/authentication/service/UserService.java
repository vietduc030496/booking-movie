package com.ntu.customerservice.application.authentication.service;

import com.ntu.moviecore.application.authentication.dto.request.SignupRequest;
import com.ntu.persistence.authentication.entity.UserJpaEntity;
import com.ntu.persistence.authentication.repository.UserJpaRepository;
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
    public boolean addNewUser(SignupRequest signupRequest) {
        User newUser = modelMapper.map(signupRequest, User.class);
        newUser.setEmail(signupRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(newUser);
        return true;
    }
}
