package com.ntu.customerservice.service.auth;

import com.ntu.moviecore.domain.authentication.dto.request.SignupRequest;
import com.ntu.moviecore.domain.authentication.entity.User;
import com.ntu.moviecore.domain.authentication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void add_new_user_should_success() {
        // Arrange
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("test@example.com");
        signupRequest.setPassword("rawPassword");

        User mappedUser = new User();
        mappedUser.setEmail("test@example.com");
        when(modelMapper.map(signupRequest, User.class)).thenReturn(mappedUser);
        when(passwordEncoder.encode("rawPassword")).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@example.com");
        savedUser.setPassword("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        boolean result = userService.addNewUser(signupRequest);

        // Assert
        assertTrue(result);
        verify(modelMapper).map(signupRequest, User.class);
        verify(passwordEncoder).encode("rawPassword");
        verify(userRepository).save(any(User.class));
    }

}
