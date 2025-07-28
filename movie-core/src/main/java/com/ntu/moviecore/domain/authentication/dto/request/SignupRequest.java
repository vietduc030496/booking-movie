package com.ntu.moviecore.domain.authentication.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    private String fullname;

    @NotBlank
    @Email
    private String username;

    private String password;

    private String passwordConfirm;

    private String birthday;

    private String phoneNumber;

    private String genre;

    private String captcha;
}
