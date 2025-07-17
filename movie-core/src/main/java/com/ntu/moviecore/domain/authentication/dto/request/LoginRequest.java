package com.ntu.moviecore.domain.authentication.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String username;

    private String password;

    private String captcha;
}
